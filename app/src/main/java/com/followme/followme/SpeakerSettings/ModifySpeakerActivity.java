package com.followme.followme.SpeakerSettings;

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
import android.widget.EditText;
import android.widget.Spinner;

import com.followme.followme.DoorSettings.DoorsSettingsActivity;
import com.followme.followme.Http.WebConnection;
import com.followme.followme.Model.Room;
import com.followme.followme.Model.Speaker;
import com.followme.followme.R;
import com.followme.followme.RoomSettings.RoomSettingsActivity;
import com.followme.followme.UserSettings.ModifyUserActivity;
import com.followme.followme.UserSettings.UsersSettingsActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Robinson on 25/01/15.
 *
 * <b>Activité qui permet de modifier le nom et la pièce d'une enceinte</b>
 *  @author Robinson
 *  @version 1.0
 */
public class ModifySpeakerActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /**
     * Bouton permettant de valider les paramètres entrés par l'utilisateur.
     */
    private Button bValidate = null;

    /**
     * Web connection to the database
     */
    private WebConnection webConnection;

    /**
     * speaker's room choose by the user
     */
    private Room speakerRoom;

    /**
     * List of rooms
     */
    private List<Room> listRoom;

    /**
     * Speaker to modify
     */
    private Speaker speaker;

    /**
     * Edit Text for edit name
     */
    private EditText editName;



    /**
     * <b>Methode qui permet de créer l'activité.</b>
     *  Affiche la liste des pièces et un EditText du nom du speaker
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     * @see #onCreate(android.os.Bundle)
     */
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_speaker);

        webConnection = new WebConnection();

        bValidate =(Button) findViewById(R.id.validSettingName);
        bValidate.setOnClickListener(this);

        Intent i = getIntent();
        speaker = Parcels.unwrap(i.getParcelableExtra("speaker"));
        editName = (EditText) findViewById(R.id.speakerName);
        editName.setText(speaker.toString());

        printSpinner();
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
        switch (id) {
            case R.id.action_my_music:
                finish();
                return true;

            case R.id.action_users_settings:
                finish();
                showUsersSettings();
                return true;

            case R.id.action_rooms_settings:
                finish();
                ShowRoomsSettings();
                return true;

            case R.id.action_doors_settings:
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
        Intent I = new Intent(ModifySpeakerActivity.this, UsersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des pièces.
     */
    private void ShowRoomsSettings(){
        Intent I = new Intent(ModifySpeakerActivity.this, RoomSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des capteurs de proximité.
     */
    private void showDoorsSettings() {
        Intent I = new Intent(ModifySpeakerActivity.this, DoorsSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrage wifi de l'enceinte.
     */
    private void showSpeakerWifiConnection(){
        Intent I = new Intent(ModifySpeakerActivity.this, SpeakerWifiConnectionActivity.class);
        startActivity(I);
    }

    private void saveModification(){
        speaker.setRoom(speakerRoom);

        editName = (EditText) findViewById(R.id.speakerName);

        speaker.setName(editName.getText().toString());

        webConnection.getApi().postSpeaker(speaker, new Callback<Speaker>() {
                    @Override
                    public void success(Speaker speaker, Response response) {
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //dialog
                        finish();
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
            case R.id.validSettingName :
                //showSpeakerWifiConnection();
                saveModification();
                break;
            default:
                break;
        }
    }

    private void printSpinner(){
        final ModifySpeakerActivity weakCopy = this;
        webConnection.getApi().getRooms(new Callback<List<Room>>() {
            @Override
            public void success(List<Room> rooms, Response response) {

                listRoom = rooms;

                if(listRoom != null){
                    if(listRoom.get(0) != null)
                        speakerRoom = listRoom.get(0);
                }

                List<String> listStringRoom = new ArrayList<>();

                int roomPosition = 0;
                for(int i = 0 ; i < listRoom.size(); i++){
                    listStringRoom.add(listRoom.get(i).toString());
                    if(listRoom.get(i).getName().equals(speaker.getRoom().getName())){
                        roomPosition = i;
                        speakerRoom = listRoom.get(i);
                        Log.d("spinnner selectection position", "position : " + i + ", room : " + speakerRoom);
                    }
                }

                Spinner spinner = (Spinner) findViewById(R.id.speakerRoom);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(weakCopy,
                        android.R.layout.simple_spinner_item, listStringRoom);

                // Drop down layout style - list view
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(weakCopy);

                spinner.setSelection(roomPosition);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Retrofit", error.getMessage());
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        speakerRoom = listRoom.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // i not use this function
    }
}
