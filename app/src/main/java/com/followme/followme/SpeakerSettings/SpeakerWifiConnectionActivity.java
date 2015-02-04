package com.followme.followme.SpeakerSettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.followme.followme.DoorSettings.DoorsSettingsActivity;
import com.followme.followme.R;
import com.followme.followme.RoomSettingsActivity;
import com.followme.followme.UserSettings.UsersSettingsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robinson on 25/01/15.
 * <b>Activité qui permet de connecter l'enceinte au wifi</b>
 *  @author Robinson
 *  @version 1.0
 */
public class SpeakerWifiConnectionActivity extends Activity implements View.OnClickListener {

    /**
     * <b>Methode qui permet de créer l'activité.</b>
     *  Affiche la liste des réseaux wifi ainsi qu'un TextEdit qui permet d'entrer le mot de passe wifi
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
        setContentView(R.layout.activity_speaker_wifi_connection);

        Button bWifiTest = (Button) findViewById(R.id.wifiTest);
        Button bFakeError = (Button) findViewById(R.id.fakeError);
        bWifiTest.setOnClickListener(this);
        bFakeError.setOnClickListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.wifiName);

        List<String> rooms = new ArrayList<>();

        rooms.add("Orange_13E23");
        rooms.add("FreeWifi");
        rooms.add("NEUF_C2J3");
        rooms.add("CIA Van");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, rooms);

        // Drop down layout style - list view
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
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
        Intent I = new Intent(SpeakerWifiConnectionActivity.this, UsersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des pièces.
     */
    private void ShowRoomsSettings(){
        Intent I = new Intent(SpeakerWifiConnectionActivity.this, RoomSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des capteurs de proximité
     */
    private void showDoorsSettings() {
        Intent I = new Intent(SpeakerWifiConnectionActivity.this, DoorsSettingsActivity.class);
        startActivity(I);
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
            case R.id.wifiTest :
                //getCallingActivity()
                break;
            case R.id.fakeError :
                TextView errorWifiMessage = (TextView) findViewById(R.id.errorWifiMessage);
                errorWifiMessage.setVisibility(View.VISIBLE);
            default:
                break;
        }
    }
}
