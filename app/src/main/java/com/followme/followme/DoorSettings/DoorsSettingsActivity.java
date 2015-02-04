package com.followme.followme.DoorSettings;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.followme.followme.R;
import com.followme.followme.RoomSettingsActivity;
import com.followme.followme.SpeakerSettings.SpeakersSettingsActivity;
import com.followme.followme.UserSettings.UsersSettingsActivity;

/**
 * Created by Robinson on 04/02/15.
 * <b>Cette class représente la première activité pour paramétré les portes et dans les capteurs de proximités</b>
 *  @author Robinson
 *  @version 1.0
 */
public class DoorsSettingsActivity extends Activity implements View.OnClickListener {

    /**
     * Liste des portes connues
     */
    private ListView listViewDoors;

    /**
     * Bouton qui permet d'ajouter une porte.
     */
    private Button bAdd = null;

    /**
     * Bouton qui permet de supprimer une porte.
     */
    private Button bDelete = null;

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

        listViewDoors = (ListView) findViewById(R.id.listDoors);

        List<String> listDoors = new ArrayList<String>();

        listDoors.add("Door Kitchen - Living Room");
        listDoors.add("Door Living Room - Parents Room");
        listDoors.add("Door Kitchen - Corridor");

        listViewDoors.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, listDoors));

        listViewDoors.setItemChecked(0, true);

        bAdd = (Button) findViewById(R.id.newDoor);
        bDelete = (Button) findViewById(R.id.deleteDoor);
        bAdd.setOnClickListener(this);
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
        Intent I = new Intent(DoorsSettingsActivity.this, SensorChooseRoomActivity.class);
        I.putExtra("sensorNum", "1");
        startActivity(I);
    }

    /**
     * Supprime une porte (2 capteurs de proximité).
     */
    private void deleteDoor() {
        //Delete door
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
            case R.id.deleteDoor :
                deleteDoor();
                break;
            default:
                break;

        }
    }
}
