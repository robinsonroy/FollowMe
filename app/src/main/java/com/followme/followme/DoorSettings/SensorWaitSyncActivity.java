package com.followme.followme.DoorSettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.followme.followme.MyMusicActivity;
import com.followme.followme.R;
import com.followme.followme.RoomSettingsActivity;
import com.followme.followme.SpeakerSettings.SpeakersSettingsActivity;
import com.followme.followme.UserSettings.UsersSettingsActivity;

/**
 * Created by Robinson on 04/02/15.
 * <b>Activité qui attend la synchronisation du capteur de proximité avec la box open hab</b>
 *  @author Robinson
 *  @version 1.0
 */
public class SensorWaitSyncActivity extends Activity implements View.OnClickListener{
    /**
     * Le numero du sensor à configurer
     * 1 correspond au 1er sensor de la porte à configurer
     * 2 correspond au 2eme sensor de la porte à configurer
     */
    private int sensorNum;

    /**
     * <b>Methode qui permet de créer l'activité.</b>
     *
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
        setContentView(R.layout.activity_sensor_wait_sync);

        String value = getIntent().getStringExtra("sensorNum");
        sensorNum = Integer.parseInt(value);

        Button bFakeRecognizing ;
        bFakeRecognizing =(Button) findViewById(R.id.fakeRecognizing);
        bFakeRecognizing.setOnClickListener(this);
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
     * Ouvre l'activité de paramétrages des utilisateurs.
     */
    private void showUsersSettings(){
        Intent I = new Intent(SensorWaitSyncActivity.this, UsersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des pièces.
     */
    private void ShowRoomsSettings(){
        Intent I = new Intent(SensorWaitSyncActivity.this, RoomSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramètrages des enceintes.
     */
    private void showSpeakersSettings() {
        Intent I = new Intent(SensorWaitSyncActivity.this, SpeakersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre la page d'ajout du capteur 2 si le capteur n'est pas ajouté
     * Si le capteur 2 est ajouté, l'actvité myMusic est ouverte
     */
    private void syncOk() {
        if(sensorNum == 1){
            Intent I = new Intent(SensorWaitSyncActivity.this, SensorChooseRoomActivity.class);
            I.putExtra("sensorNum", "2");
            startActivity(I);
        }else if(sensorNum == 2){
            Intent I = new Intent(SensorWaitSyncActivity.this, MyMusicActivity.class);
            startActivity(I);
        }else{
            Log.d("SensorNum","Sensor is not 1 or 2");
        }

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
            case R.id.fakeRecognizing :
                syncOk();
                break;
            default:
                break;
        }
    }
}
