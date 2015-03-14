package com.followme.followme.SpeakerSettings;

import java.util.List;
import android.app.Activity;
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

import com.followme.followme.DoorSettings.DoorsSettingsActivity;
import com.followme.followme.Http.WebConnection;
import com.followme.followme.Model.Room;
import com.followme.followme.Model.Speaker;
import com.followme.followme.R;
import com.followme.followme.RoomSettings.ModifyRoomActivity;
import com.followme.followme.RoomSettings.RoomSettingsActivity;
import com.followme.followme.UserSettings.UsersSettingsActivity;
import com.followme.followme.View.ErrorDialog;

import org.parceler.Parcels;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Robinson on 18/01/15.
 * <b>Activité permettant de configurer les enceintes.</b>
 *  Ajouter, supprimer, ou modifier une enceinte.
 *  @author Robinson
 *  @version 1.0
 */
public class SpeakersSettingsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {


    /**
     * Web connection to the database
     */
    private WebConnection webConection;

    /**
     * Speaker's list
     */
    private List<Speaker> listSpeakers;
    /**
     * Speaker's listView
     */
    private ListView listViewSpeakers;

    /**
     * Bouton pour ajouter une enceinte
     */
    private Button bAdd = null;

    /**
     * Bouton pour modifier une enceinte
     */
    private Button bModify = null;

    /**
     * delete button
     */
    private Button bDelete = null;

    /**
     * speaker selected
     */
    private Speaker selectedSpeaker = null;

    /**
     * <b>Methode qui permet de créer l'activité.</b>
     *  Affiche la listes des enceintes
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
        setContentView(R.layout.activity_speakers_settings);

        listViewSpeakers = (ListView) findViewById(R.id.listSeakers);

        webConection = new WebConnection();

        printList();

        bAdd = (Button) findViewById(R.id.newSpeaker);
        bModify = (Button) findViewById(R.id.modificationSpeaker);
        bDelete = (Button) findViewById(R.id.deleteSpeaker);
        bAdd.setOnClickListener(this);
        bModify.setOnClickListener(this);
        bDelete.setOnClickListener(this);

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
        Intent I = new Intent(SpeakersSettingsActivity.this, UsersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des pièces.
     */
    private void ShowRoomsSettings(){
        Intent I = new Intent(SpeakersSettingsActivity.this, RoomSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des capteurs de proximité
     */
    private void showDoorsSettings() {
        Intent I = new Intent(SpeakersSettingsActivity.this, DoorsSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité d'ajout d'enceinte
     */
    private void showNewSpeaker() {
        Intent I = new Intent(SpeakersSettingsActivity.this, NewSpeakerActivity.class);//NewSpeakerActivity.class);
        startActivity(I);
    }

    /**
     * Open the activity which modify speaker
     */
    private void showSpeakerModify() {
        Intent I = new Intent(SpeakersSettingsActivity.this, ModifySpeakerActivity.class);
        Parcelable wrapped = Parcels.wrap(selectedSpeaker);
        I.putExtra("speaker", wrapped);
    startActivity(I);
}

    /**
     * delete selected speaker
     */
    private void deleteSpeaker() {
        final SpeakersSettingsActivity weakCopy = this;
        webConection.getApi().deleteSpeaker(selectedSpeaker.getId(), new Callback<Speaker>() {
            @Override
            public void success(Speaker speaker, Response response) {
                printList();
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorDialog dialog = new ErrorDialog("Delete Error", "OK", weakCopy);
                dialog.openDialog();
                printList();
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

            case R.id.newSpeaker :
                showNewSpeaker();
                break;

            case R.id.modificationSpeaker :
                showSpeakerModify();
                break;

            case R.id.deleteSpeaker :
                deleteSpeaker();
                break;

            default:
                break;

        }
    }

    /**
     * Permet de mettre à jour la posistion de l'enceinte séléctionner dans la liste.
     *
     * @param parent L'AdapterView ou le click a eu lieu.
     * @param view La view au le click a eu lieu
     * @param position La position de la vue dans l'AdapterView.
     * @param id La ligne id de l'item séléctionner.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedSpeaker = (Speaker) parent.getItemAtPosition(position);
    }

    private void printList(){
        final SpeakersSettingsActivity weakCopy = this;
        webConection.getApi().getspeakers(new Callback<List<Speaker>>() {
            @Override
            public void success(List<Speaker> speakers, Response response) {
                listSpeakers = speakers;
                listViewSpeakers.setAdapter(new ArrayAdapter<>(weakCopy, android.R.layout.simple_list_item_single_choice, listSpeakers));
                listViewSpeakers.setItemChecked(0, true);
                listViewSpeakers.setOnItemClickListener(weakCopy);

                if (listSpeakers != null) {
                    if (listSpeakers.get(0) != null)
                        selectedSpeaker = listSpeakers.get(0);
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
