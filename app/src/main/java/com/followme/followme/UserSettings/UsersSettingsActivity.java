package com.followme.followme.UserSettings;

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

import com.followme.followme.DoorSettings.DoorsSettingsActivity;
import com.followme.followme.Http.WebConnection;
import com.followme.followme.Model.User;
import com.followme.followme.R;
import com.followme.followme.RoomSettings.RoomSettingsActivity;
import com.followme.followme.SpeakerSettings.SpeakersSettingsActivity;
import com.followme.followme.View.ErrorDialog;
import com.followme.followme.View.ErrorFinishDialog;

import org.parceler.Parcels;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Robinson on 18/01/15.
 * <b>Activité permettant la configuration d'utilisateur (bracelet).</b>
 *  Ajouter, supprimer, ou modifier un utilisateur (bracelet).
 *  @author Robinson
 *  @version 1.0
 */
public class UsersSettingsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{

    /**
     * Web connection to the database
     */
    private WebConnection webConection;

    /**
     * List view of users
     */
    private ListView listViewUsers;

    /**
     * users list
     */
    private List<User> listUsers;


    /**
     * Bouton d'ajout d'un utilisateur
     */
    private Button bAdd = null;

    /**
     * Bouton de modification d'un utilisateur
     */
    private Button bModify = null;

    /**
     * Button to delete an user
     */
    private Button bDelete = null;

    /**
     * position de l'utilsateur selection dans la ListView listViewUsers
     */
    private User selectedUser = null;

    /**
     * <b>Methode qui permet de créer l'activité.</b>
     *  Affiche la listes des utilisateurs
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
        setContentView(R.layout.activity_users_settings);

        listViewUsers = (ListView) findViewById(R.id.listUsers);

        webConection = new WebConnection();

        printList();

        bAdd = (Button) findViewById(R.id.newUser);
        bModify = (Button) findViewById(R.id.modificationUser);
        bDelete = (Button) findViewById(R.id.deleteUser);
        bDelete.setOnClickListener(this);
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
        Intent I = new Intent(UsersSettingsActivity.this, SpeakersSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des pièces.
     */
    private void ShowRoomsSettings(){
        Intent I = new Intent(UsersSettingsActivity.this, RoomSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité de paramétrages des capteurs de proximité.
     */
    private void showDoorsSettings() {
        Intent I = new Intent(UsersSettingsActivity.this, DoorsSettingsActivity.class);
        startActivity(I);
    }

    /**
     * Ouvre l'activité d'ajout d'utilisateur.
     */
    private void showNewUser() {
        Intent I = new Intent(UsersSettingsActivity.this, BraceletSyncActivity.class);
        startActivity(I);
    }

    /**
     * Open the activity which modify speaker
     */
    private void showUserModify() {
        Intent I = new Intent(UsersSettingsActivity.this, ModifyUserActivity.class);
        Parcelable wrapped = Parcels.wrap(selectedUser);
        I.putExtra("user", wrapped);
        startActivity(I);
    }

    /**
     * delete selected user
     */
    private void deleteUser() {
        final UsersSettingsActivity weakCopy = this;
        webConection.getApi().deleteUser(selectedUser.getId(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
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

            case R.id.newUser :
                showNewUser();
                break;
            case R.id.modificationUser :
                showUserModify();
                break;
            case R.id.deleteUser :
                deleteUser();
            default:
                break;

        }
    }

    /**
     * method to print user listView
     */
    private void printList(){
        final UsersSettingsActivity weakCopy = this;
        webConection.getApi().getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                listUsers = users;
                listViewUsers.setAdapter(new ArrayAdapter<>(weakCopy, android.R.layout.simple_list_item_single_choice, listUsers));
                listViewUsers.setItemChecked(0, true);
                listViewUsers.setOnItemClickListener(weakCopy);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Retrofit", error.getMessage());
            }
        });
    }

    /**
     * Permet de mettre à jour la posistion de l'utilisateur séléctionner dans la liste.
     *
     * @param parent L'AdapterView ou le click a eu lieu.
     * @param view La view au le click a eu lieu
     * @param position La position de la vue dans l'AdapterView.
     * @param id La ligne id de l'item séléctionner.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedUser = (User) parent.getItemAtPosition(position);
    }

    /**
     * Methode to refresh listView when the activity is resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        printList();
    }
}
