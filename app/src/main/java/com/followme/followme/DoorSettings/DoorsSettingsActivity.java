package com.followme.followme.DoorSettings;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.followme.followme.Http.WebConnection;
import com.followme.followme.Model.Door;
import com.followme.followme.Model.Room;
import com.followme.followme.Model.Speaker;
import com.followme.followme.R;
import com.followme.followme.RoomSettings.RoomSettingsActivity;
import com.followme.followme.SpeakerSettings.SpeakersSettingsActivity;
import com.followme.followme.UserSettings.UsersSettingsActivity;
import com.followme.followme.View.ErrorDialog;
import com.followme.followme.View.GsonMessage;
import com.followme.followme.View.SwipeDismissListViewTouchListener;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Robinson on 04/02/15.
 * <b>Cette class représente la première activité pour paramétré les portes et dans les capteurs de proximités</b>
 *  @author Robinson
 *  @version 1.0
 */
public class DoorsSettingsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    /**
     * Liste des portes connues
     */
    private ListView listViewDoors;

    /**
     * List of all door
     */
    private List<Door> listDoors;
    /**
     * Door selected by the user
     */
    private Door selectedDoor;

    /**
     * web connection with the database
     */
    private WebConnection webConnection;

    /**
     * Bouton qui permet d'ajouter une porte.
     */
    private Button bAdd = null;

    /**
     * Adapter for Door ListView
     */
    private ArrayAdapter<Door> mAdapter;

    /**
     * <b>Methode qui permet de créer l'activité.</b>
     *
     *  Cette activité affiche la liste des portes déja enregistrés.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     * @see #onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doors_settings);

        webConnection = new WebConnection();
        listViewDoors = (ListView) findViewById(R.id.listDoors);

        printList();

        bAdd = (Button) findViewById(R.id.newDoor);
        bAdd.setOnClickListener(this);

    }

    /**
     * Créé le menu
     *
     * @param menu
     *          menu permettant d'aller sur certaine activitées depuis toutes les activitées.
     *
     * @return true si le menu s'affiche et false sinon.
     *
     * see #onCreateOptionMenu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Gère les actions lors d'un click sur un item du menu.
     * @param item
     *             Le menu item selectionné.
     *
     * @return false to allow normal menu processing to
     *         proceed, true to consume it here.
     *
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_my_music :
                finish();
                return true;

            case R.id.action_users_settings :
                finish();
                showUsersSettings();
                return true;

            case R.id.action_rooms_settings :
                finish();
                ShowRoomsSettings();
                return true;

            case R.id.action_speakers_settings :
                finish();
                showSpeakersSettings();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Ouvre l'activité de paramétrages utilisateur.
     */
    private void showUsersSettings(){
        Intent I = new Intent(DoorsSettingsActivity.this, UsersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des pièces.
     */
    private void ShowRoomsSettings(){
        Intent I = new Intent(DoorsSettingsActivity.this, RoomSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramètrages des enceintes.
     */
    private void showSpeakersSettings() {
        Intent I = new Intent(DoorsSettingsActivity.this, SpeakersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramètrages des capteurs de proximité
     */
    private void showNewSensor1() {
        Intent I = new Intent(DoorsSettingsActivity.this, SensorAddActivity.class);
        startActivity(I);
    }

    /**
     * Supprime une porte (2 capteurs de proximité).
     */
    private void deleteDoor(Door door) {
        final DoorsSettingsActivity weakCopy = this;
        final Door tempDoor = door;
        webConnection.getApi().deleteDoor(door.getId(), new Callback<Room>() {
            @Override
            public void success(Room room, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                ErrorDialog dialog = new ErrorDialog(GsonMessage.getMessage(error), "OK", weakCopy);
                dialog.openDialog();
                mAdapter.add(tempDoor);
            }
        });
    }

    /**
     *
     * @param v
     *      le bouton cliqué
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.newDoor :
                showNewSensor1();
                break;
            default:
                break;

        }
    }

    private void printList(){
        final DoorsSettingsActivity weakCopy = this;
        webConnection.getApi().getDoors(new Callback<List<Door>>() {
            @Override
            public void success(List<Door> doors, Response response) {
                listDoors = doors;
                mAdapter = new ArrayAdapter<Door>(weakCopy,
                        android.R.layout.simple_list_item_single_choice,
                        listDoors);

                listViewDoors.setAdapter(mAdapter);

                SwipeDismissListViewTouchListener touchListener =
                        new SwipeDismissListViewTouchListener(
                                listViewDoors,
                                new SwipeDismissListViewTouchListener.DismissCallbacks() {
                                    @Override
                                    public boolean canDismiss(int position) {
                                        return true;
                                    }

                                    @Override
                                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                        for (int position : reverseSortedPositions) {
                                            Door tempDoor = mAdapter.getItem(position);
                                            mAdapter.remove(tempDoor);
                                            deleteDoor(tempDoor);
                                        }
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                listViewDoors.setOnTouchListener(touchListener);
                listViewDoors.setOnScrollListener(touchListener.makeScrollListener());
                listViewDoors.setItemChecked(0, true);
                listViewDoors.setOnItemClickListener(weakCopy);
                Log.d("doors", doors.toString());

                if (listDoors != null) {
                    if (listDoors.get(0) != null)
                        selectedDoor = listDoors.get(0);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Retrofit", error.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedDoor = (Door) parent.getItemAtPosition(position);
    }


    @Override
    protected void onResume() {
        super.onResume();
        printList();
    }
}
