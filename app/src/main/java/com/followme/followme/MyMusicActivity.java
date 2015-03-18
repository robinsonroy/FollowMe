package com.followme.followme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.TextView;

import com.followme.followme.DoorSettings.DoorsSettingsActivity;
import com.followme.followme.Http.ApiService;
import com.followme.followme.Http.WebConnection;
import com.followme.followme.Model.Music;
import com.followme.followme.Model.PlayMusic;
import com.followme.followme.Model.Room;
import com.followme.followme.Model.Test;
import com.followme.followme.Model.User;
import com.followme.followme.RoomSettings.RoomSettingsActivity;
import com.followme.followme.SpeakerSettings.SpeakersSettingsActivity;
import com.followme.followme.UserSettings.UsersSettingsActivity;
import com.followme.followme.View.ErrorDialog;
import com.followme.followme.View.GsonMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Robinson on 18/01/15.
 *
 * <b>Activité permettant de lancer de la musique.</b>
 *
 *  @author Robinson
 *  @version 1.0
 */
public class MyMusicActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    /**
     * Tag to save main user in share preference
     */
    public final static String MAIN_USER_NAME = "main user name";
    public final static String MAIN_USER_ID = "main user id";
    public final static String MAIN_USER_BRACELET = "main user bracelet";

    /**
     * connection with the web server
     */
    private WebConnection webConnection;

    /**
     * Music ListView
     */
    private ListView listViewMusic;

    /**
     * Music List
     */
    private List<Music> listMusic;

    /**
     * play/pause Button
     */
    private Button playPauseButton;

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
        setContentView(R.layout.activity_my_music);
        webConnection = new WebConnection();
        playPauseButton = (Button) findViewById(R.id.play_pause);
        playPauseButton.setOnClickListener(this);
        printListMusic();
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
            case R.id.action_rooms_settings :
                ShowRoomsSettings();
                finish();
                break;

            case R.id.action_users_settings :
                showUsersSettings();
                return true;

            case R.id.action_speakers_settings :
                showSpeakersSettings();
                return true;

            case R.id.action_doors_settings :
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
        Intent I = new Intent(MyMusicActivity.this, UsersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des pièces.
     */
    private void ShowRoomsSettings(){
        Intent I = new Intent(MyMusicActivity.this, RoomSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramètrages des enceintes.
     */
    private void showSpeakersSettings() {
        Intent I = new Intent(MyMusicActivity.this, SpeakersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des capteurs de proximité
     */
    private void showDoorsSettings() {
        Intent I = new Intent(MyMusicActivity.this, DoorsSettingsActivity.class);
        startActivity(I);
    }

    private void printListMusic(){
        listViewMusic =(ListView) findViewById(R.id.listMusic);
        Log.d("where", "printTest in MyMusicActivity");
        final MyMusicActivity weakcopy = this;
        webConnection.getApi().getMusics(new Callback<List<Music>>() {
            @Override
            public void success(List<Music> musics, Response response) {
                listMusic = musics;

                listViewMusic.setAdapter(new ArrayAdapter<Music>(weakcopy, android.R.layout.simple_list_item_single_choice, listMusic));
                listViewMusic.setOnItemClickListener(weakcopy);
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorDialog errorDialog = new ErrorDialog("Impossible de get music", "ok", weakcopy);
                errorDialog.openDialog();
            }
        });
    }





    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.play_pause :
                playPause();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = getMainUser();

        if(user.getId() != -1 || user.getName() != "none" || user.getBraceletID() != -1){
            final MyMusicActivity weakCopy = this;
            webConnection.getApi().playMusic(new PlayMusic(listMusic.get(position), user),  new Callback<PlayMusic>() {
                @Override
                public void success(PlayMusic playMusic, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {
                    ErrorDialog errorDialog = new ErrorDialog(GsonMessage.getMessage(error), "OK", weakCopy);
                    errorDialog.openDialog();
                }

            });
        }else{
            ErrorDialog errorDialog = new ErrorDialog("Please initialise the main user in user settings", "OK", this);
            errorDialog.openDialog();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
    private User getMainUser(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        User user = new User();
        user.setId(preferences.getInt(MAIN_USER_ID, -1));
        user.setBraceletID(preferences.getLong(MAIN_USER_BRACELET, -1));
        user.setName(preferences.getString(MAIN_USER_NAME, "none"));

        return user;
    }

    private void playPause() {
        Log.d("play", "play_pause fonction");
        final MyMusicActivity weakCopy = this;
        User user = getMainUser();
        if (user.getId() != -1 || user.getName() != "none" || user.getBraceletID() != -1) {
            webConnection.getApi().playPause(user.getId(), new Callback<Object>() {
                @Override
                public void success(Object o, Response response) {
                }

                @Override
                public void failure(RetrofitError error) {
                    ErrorDialog errorDialog = new ErrorDialog(GsonMessage.getMessage(error), "OK", weakCopy);
                    errorDialog.openDialog();


                }
            });
        }else{
            ErrorDialog errorDialog = new ErrorDialog("Please initialise the main user in user settings", "OK", weakCopy);
            errorDialog.openDialog();
        }
    }

}