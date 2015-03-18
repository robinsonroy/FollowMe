package com.followme.followme.UserSettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.followme.followme.DoorSettings.DoorsSettingsActivity;
import com.followme.followme.Http.WebConnection;
import com.followme.followme.Model.User;
import com.followme.followme.R;
import com.followme.followme.RoomSettings.RoomSettingsActivity;
import com.followme.followme.SpeakerSettings.SpeakersSettingsActivity;
import com.followme.followme.View.ErrorFinishDialog;
import com.followme.followme.View.GsonMessage;

import java.util.Random;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Robinson on 28/01/15.
 * <b>Activité permettant de parametrer le nom d'un nouvel utilisateur</b>
 *  @author Robinson
 *  @version 1.0
 */
public class NewUserSettingName extends Activity implements View.OnClickListener {

    /**
     * Bouton permettant de valider le nom de l'utilisateur
     */
    private Button bValidate = null;

    /**
     * webconnection
     */
    private WebConnection webConnection;

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
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_setting_name);

        bValidate =(Button) findViewById(R.id.validSettingName);
        bValidate.setOnClickListener(this);
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

            case R.id.action_speakers_settings :
                finish();
                showSpeakersSettings();
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
     * Ouvre l'activité de paramètrages des enceintes.
     */
    private void showSpeakersSettings() {
        Intent I = new Intent(NewUserSettingName.this, SpeakersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des pièces.
     */
    private void ShowRoomsSettings(){
        Intent I = new Intent(NewUserSettingName.this, RoomSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des capteurs de proximité
     */
    private void showDoorsSettings() {
        Intent I = new Intent(NewUserSettingName.this, DoorsSettingsActivity.class);
        startActivity(I);
    }

    private void addNewUser(){

        User user = new User();

        EditText editName =(EditText) findViewById(R.id.userName);
        EditText editBraceletID = (EditText) findViewById(R.id.braceletID);

        user.setName(editName.getText().toString());
        user.setBraceletID(Long.parseLong(editBraceletID.getText().toString()));

        final NewUserSettingName weakCopy = this;

        webConnection = new WebConnection();
        webConnection.getApi().putUser(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorFinishDialog dialog = new ErrorFinishDialog(GsonMessage.getMessage(error), "OK", weakCopy);
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
                addNewUser();
                break;
            default:
                break;
        }
    }
}
