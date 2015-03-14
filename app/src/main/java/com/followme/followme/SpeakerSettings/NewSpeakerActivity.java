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
import com.followme.followme.UserSettings.NewUserSettingName;
import com.followme.followme.UserSettings.UsersSettingsActivity;
import com.followme.followme.View.ErrorFinishDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Robinson on 25/01/15.
 * <b>Activité qui permet de donner un nom à une nouvelle enceinte</b>
 *  @author Robinson
 *  @version 1.0
 */
public class NewSpeakerActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /**
     * Validate bouton
     */
    private Button bValidate = null;

    /**
     * Web connectioon to the database
     */
    private WebConnection webConnection;

    /**
     * the list of rooms
     */
    private List<Room> listRoom;

    /**
     * room choose with spinner
     */
    private Room speakerRoom;


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
        setContentView(R.layout.activity_new_speaker_setting_name);

        bValidate =(Button) findViewById(R.id.validSettingName);
        bValidate.setOnClickListener(this);

        webConnection = new WebConnection();

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
        Intent I = new Intent(NewSpeakerActivity.this, UsersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des pièces.
     */
    private void ShowRoomsSettings(){
        Intent I = new Intent(NewSpeakerActivity.this, RoomSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des capteurs de proximité
     */
    private void showDoorsSettings() {
        Intent I = new Intent(NewSpeakerActivity.this, DoorsSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrage wifi de l'enceinte.
     */
    private void showSpeakerWifiConnection(){
        Intent I = new Intent(NewSpeakerActivity.this, SpeakerWifiConnectionActivity.class);
        startActivity(I);
    }

    private void addNewSpeaker(){
        Speaker speaker = new Speaker();

        EditText editName =(EditText) findViewById(R.id.speakerName);

        speaker.setName(editName.getText().toString());

        speaker.setRoom(speakerRoom);

        final NewSpeakerActivity weakCopy = this;
        webConnection.getApi().putSpeaker(speaker, new Callback<Speaker>() {
            @Override
            public void success(Speaker speaker, Response response) {
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorFinishDialog dialog = new ErrorFinishDialog("Impossible to add speaker", "ok", weakCopy);
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
            case R.id.validSettingName :
                //showSpeakerWifiConnection();
                addNewSpeaker();
                break;
            default:
                break;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        speakerRoom = listRoom.get(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void printSpinner(){
        final NewSpeakerActivity weakCopy = this;
        webConnection.getApi().getRooms(new Callback<List<Room>>() {
            @Override
            public void success(List<Room> rooms, Response response) {

                listRoom = rooms;

                List<String> listStringRoom = new ArrayList<>();

                for(int i = 0 ; i < rooms.size(); i++){
                    listStringRoom.add(rooms.get(i).toString());
                }

                Spinner spinner = (Spinner) findViewById(R.id.speakerRoom);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(weakCopy,
                        android.R.layout.simple_spinner_item, listStringRoom);

                // Drop down layout style - list view
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                if(rooms != null){
                    if(rooms.get(0) != null)
                        speakerRoom = rooms.get(0);
                }


                // attaching data adapter to spinner
                spinner.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Retrofit", error.getMessage());
            }
        });
    }
}
