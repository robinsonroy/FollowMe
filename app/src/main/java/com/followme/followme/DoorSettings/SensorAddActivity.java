package com.followme.followme.DoorSettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.followme.followme.Http.WebConnection;
import com.followme.followme.Model.Door;
import com.followme.followme.Model.RFSensor;
import com.followme.followme.Model.Room;
import com.followme.followme.R;
import com.followme.followme.RoomSettings.RoomSettingsActivity;
import com.followme.followme.SpeakerSettings.SpeakersSettingsActivity;
import com.followme.followme.UserSettings.UsersSettingsActivity;
import com.followme.followme.View.ErrorFinishDialog;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Robinson on 04/02/15.
 * <b>Activité qui permet d'indiquer la pièce ou se trouve le capteur</b>
 *  @author Robinson
 *  @version 1.0
 */
public class SensorAddActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /**
     * Bouton permettant de valider les paramètres entrés par l'utilisateur
     */
    private Button bValidate = null;

    /**
     * Web connection
     */
    private WebConnection webConnection;

    /**
     * Room list
     */
    private List<Room> listRooms;

    /**
     * selected room
     */
    private Room selectedRoom;

    /**
     * Serial number
     */
    private long sensorSerial;

    /**
     * EditText serial number
     */
    EditText editSerial;

    /**
     * Door to add in database
     */
    private Door door;

    /**
     * <b>Methode qui permet de créer l'activité.</b>
     *
     *  Cette activité affiche la liste des pièces déja enregistrés.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     * @see #onCreate(android.os.Bundle)
     */
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_door);

        //Button
        bValidate =(Button) findViewById(R.id.validSetting);
        bValidate.setOnClickListener(this);

        //initialise editText
        editSerial = (EditText) findViewById(R.id.sensorSerial);

        //Spinner
        final SensorAddActivity weakCopy = this;
        webConnection = new WebConnection();
        webConnection.getApi().getRooms(new Callback<List<Room>>() {
            @Override
            public void success(List<Room> rooms, Response response) {
                Spinner spinner = (Spinner) findViewById(R.id.sensorRoom);
                listRooms = rooms;
                List<String> stringRoom = new ArrayList<String>();
                for(int i=0; i< rooms.size(); i++){
                    stringRoom.add(rooms.get(i).getName());
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(weakCopy,
                        android.R.layout.simple_spinner_item, stringRoom);

                // Drop down layout style - list view
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(dataAdapter);
                spinner.setOnItemSelectedListener(weakCopy);

                if(rooms != null){
                    if(rooms.get(0) != null)
                        selectedRoom = rooms.get(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorFinishDialog dialog = new ErrorFinishDialog("Impossible to share room", "ok", weakCopy);
                dialog.openDialog();
            }
        });

        TextView textView = (TextView) findViewById(R.id.sensorNumber);
        //if second activity set door else initialise door
        Intent i = getIntent();
        if(i.hasExtra("door")){
            textView.setText("Sensor 2");
            door = Parcels.unwrap(i.getParcelableExtra("door"));
        }else {
            textView.setText("Sensor 1");
            door = new Door();
        }

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
        Intent I = new Intent(SensorAddActivity.this, UsersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des pièces.
     */
    private void ShowRoomsSettings(){
        Intent I = new Intent(SensorAddActivity.this, RoomSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramètrages des enceintes.
     */
    private void showSpeakersSettings() {
        Intent I = new Intent(SensorAddActivity.this, SpeakersSettingsActivity.class);
        startActivity(I);
    }

    /*
     * Ouvre l'activité de synchronisation entre le capteur et la box openHab.

    private void showWaitForSync(){
        Log.d("showActivity", "We are in showWaitForSync method");
        Intent I = new Intent(SensorAddActivity.this, SensorWaitSyncActivity.class);
        String value = Integer.toString(sensorNum);
        I.putExtra("sensorNum", value);
        startActivity(I);
    }*/

    private void saveSensor(int sensorNumber){
        RFSensor sensor = new RFSensor();
        sensor.setId(Long.parseLong(editSerial.getText().toString()));
        sensor.setRoom(selectedRoom);
        if(sensorNumber == 1){
            door.setSensor1(sensor);
        }
        else door.setSensor2(sensor);
    }

    private void secondSensor(){
        Intent I = new Intent(SensorAddActivity.this, SensorAddActivity.class);
        Parcelable wrapped = Parcels.wrap(door);
        I.putExtra("door", wrapped);
        startActivity(I);
    }

    private void saveDoor(){
        final SensorAddActivity weakCopy = this;
        webConnection.getApi().putDoor(door, new Callback<Room>() {
            @Override
            public void success(Room room, Response response) {
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorFinishDialog dialog = new ErrorFinishDialog("Impossible to add this door", "OK", weakCopy);
                dialog.openDialog();
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
            case R.id.validSetting :
                if(door.getSensor1() == null){
                    saveSensor(1);
                    secondSensor();
                    finish();
                }
                else{
                    saveSensor(2);
                    saveDoor();
                }
                //showWaitForSync();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedRoom = listRooms.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
