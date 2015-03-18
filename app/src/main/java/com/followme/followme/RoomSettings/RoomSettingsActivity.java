package com.followme.followme.RoomSettings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.followme.followme.DoorSettings.DoorsSettingsActivity;
import com.followme.followme.Http.WebConnection;
import com.followme.followme.Model.Door;
import com.followme.followme.Model.Room;
import com.followme.followme.Model.Speaker;
import com.followme.followme.R;
import com.followme.followme.SpeakerSettings.SpeakersSettingsActivity;
import com.followme.followme.UserSettings.UsersSettingsActivity;
import com.followme.followme.View.ErrorDialog;
import com.followme.followme.View.GsonMessage;
import com.followme.followme.View.SwipeDismissListViewTouchListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Robinson on 18/01/15.
 *
 * <b>Activité permettant d'ajouter, supprimer ou modifier une pièce.</b>
 *
 *  @author Robinson
 *  @version 1.0
 */
public class RoomSettingsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{

    /**
     * Connexion to the web serveur
     */
    WebConnection webConection;
    /**
     * Liste des vues des pièces connues
     */
    private ListView listViewRooms;

    /**
     * Liste de pièces
     */
    private List<Room> listRooms;

    /**
     * Bouton qui permet d'ajouter une pièce.
     */
    private Button bAdd = null;

    /**
     * Bouton qui permet de modifier une pièce.
     */
    private Button bModify = null;

    /**
     * Posisiton de la room choisi par l'utilisateur dans la ListView
     */
    private Room selectedRoom;

    /**
     * Adapter for Room ListView
     */
    private ArrayAdapter<Room> mAdapter;

    /**
     * <b>Methode qui permet de créer l'activité.</b>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     * @see #onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_settings);

        listViewRooms = (ListView) findViewById(R.id.listRooms);

        webConection = new WebConnection();

        printList();

        bAdd = (Button) findViewById(R.id.newRoom);
        bModify = (Button) findViewById(R.id.modificationRoom);

        bAdd.setOnClickListener(this);
        bModify.setOnClickListener(this);
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

            case R.id.action_speakers_settings :
                finish();
                showSpeakersSettings();
                return true;

            case R.id.action_doors_settings :
                finish();
                showDoorsSettings();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Ouvre l'activité de paramétrages des utilisateurs.
     */
    private void showUsersSettings(){
        Intent I = new Intent(RoomSettingsActivity.this, UsersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramètrages des enceintes.
     */
    private void showSpeakersSettings() {
        Intent I = new Intent(RoomSettingsActivity.this, SpeakersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des capteurs de proximité
     */
    private void showDoorsSettings() {
        Intent I = new Intent(RoomSettingsActivity.this, DoorsSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité d'ajout de pièce
     */
    private void showNewRoom() {
        Intent I = new Intent(RoomSettingsActivity.this, NewRoomActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de modification de pièce
     */
    private void showModificationRoom() {
        Intent I = new Intent(RoomSettingsActivity.this, ModifyRoomActivity.class);
        Parcelable wrapped = Parcels.wrap(selectedRoom);
        I.putExtra("room", wrapped);
        startActivity(I);
    }

    private void deleteRoom(Room room) {
        final RoomSettingsActivity weakCopy = this;
        final Room tempRoom = room;
        webConection.getApi().deleteRoom(room.getId(), new Callback<Room>(){
            @Override
            public void success(Room room, Response response) {

            }
            @Override
            public void failure(RetrofitError error) {
                ErrorDialog dialog = new ErrorDialog(GsonMessage.getMessage(error), "OK", weakCopy);
                dialog.openDialog();
                mAdapter.add(tempRoom);
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

        switch (id){
            case R.id.newRoom :
                showNewRoom();
                break;
            case R.id.modificationRoom :
                showModificationRoom();
                break;
            default:
                break;
        }
    }

    /**
     * Permet de mettre à jour la posistion de la pièce séléctionner dans la liste.
     *
     * @param parent L'AdapterView ou le click a eu lieu.
     * @param view La view au le click a eu lieu
     * @param position La position de la vue dans l'AdapterView.
     * @param id La ligne id de l'item séléctionner.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedRoom = (Room) parent.getItemAtPosition(position);
    }

    private void printList(){
        final RoomSettingsActivity weakCopy = this;
        webConection.getApi().getRooms(new Callback<List<Room>>() {
            @Override
            public void success(List<Room> rooms, Response response) {
                listRooms = rooms;
                mAdapter = new ArrayAdapter<Room>(weakCopy,
                        android.R.layout.simple_list_item_single_choice,
                        listRooms);

                listViewRooms.setAdapter(mAdapter);

                SwipeDismissListViewTouchListener touchListener =
                        new SwipeDismissListViewTouchListener(
                                listViewRooms,
                                new SwipeDismissListViewTouchListener.DismissCallbacks() {
                                    @Override
                                    public boolean canDismiss(int position) {
                                        return true;
                                    }

                                    @Override
                                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                        for (int position : reverseSortedPositions) {
                                            Room tempRoom = mAdapter.getItem(position);
                                            mAdapter.remove(tempRoom);
                                            deleteRoom(tempRoom);
                                        }
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                listViewRooms.setOnTouchListener(touchListener);
                listViewRooms.setOnScrollListener(touchListener.makeScrollListener());
                listViewRooms.setItemChecked(0, true);
                listViewRooms.setOnItemClickListener(weakCopy);

                if(listRooms != null){
                    if(listRooms.get(0) != null)
                        selectedRoom = listRooms.get(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Retrofit", error.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        printList();
    }
}
