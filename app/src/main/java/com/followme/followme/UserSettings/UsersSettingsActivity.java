package com.followme.followme.UserSettings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.followme.followme.DoorSettings.DoorsSettingsActivity;
import com.followme.followme.Http.WebConnection;
import com.followme.followme.Model.User;
import com.followme.followme.R;
import com.followme.followme.RoomSettings.RoomSettingsActivity;
import com.followme.followme.SpeakerSettings.SpeakersSettingsActivity;
import com.followme.followme.View.ErrorDialog;
import com.followme.followme.View.ErrorFinishDialog;
import com.followme.followme.View.SwipeDismissListViewTouchListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
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
public class UsersSettingsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, android.widget.AdapterView.OnItemLongClickListener{

    /**
     * Tag to save main user in share preference
     */
    public final static String MAIN_USER_NAME = "main user name";
    public final static String MAIN_USER_ID = "main user id";
    public final static String MAIN_USER_BRACELET = "main user bracelet";

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
     * position de l'utilsateur selection dans la ListView listViewUsers
     */
    private User selectedUser = null;

    /**
     * long selected user (application user
     */
    private User mainUser;

    /**
     * adaptater for listView
     */
    private ArrayAdapter<User> mAdapter;

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
        Intent I = new Intent(UsersSettingsActivity.this, NewUserSettingName.class);//BraceletSyncActivity.class);
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
    private void deleteUser(User user) {
        final UsersSettingsActivity weakCopy = this;
        final User deleteUser = user;
        webConection.getApi().deleteUser(deleteUser.getId(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                //printList();
                if(deleteUser.getId() == mainUser.getId()){
                    mainUser = null;
                    findMainUser();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorDialog dialog = new ErrorDialog("Delete Error", "OK", weakCopy);
                dialog.openDialog();
                mAdapter.add(deleteUser);
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

                mAdapter = new ArrayAdapter<User>(weakCopy,
                        android.R.layout.simple_list_item_single_choice,
                        listUsers);

                listViewUsers.setAdapter(mAdapter);

                SwipeDismissListViewTouchListener touchListener =
                        new SwipeDismissListViewTouchListener(
                                listViewUsers,
                                new SwipeDismissListViewTouchListener.DismissCallbacks() {
                                    @Override
                                    public boolean canDismiss(int position) {
                                        return true;
                                    }

                                    @Override
                                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                        for (int position : reverseSortedPositions) {
                                            User tempUSer = mAdapter.getItem(position);
                                            mAdapter.remove(tempUSer);
                                            deleteUser(tempUSer);
                                        }
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                listViewUsers.setOnTouchListener(touchListener);
                listViewUsers.setOnScrollListener(touchListener.makeScrollListener());


                listViewUsers.setItemChecked(0, true);
                listViewUsers.setOnItemClickListener(weakCopy);
                listViewUsers.setOnItemLongClickListener(weakCopy);

                if(users != null){
                    if(users.size() != 0){
                        selectedUser = users.get(0);
                    }
                }
                findMainUser();
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        mainUser =(User) listViewUsers.getItemAtPosition(position);
        save(mainUser);

        printMainUser();
        return false;
    }

    private void findMainUser(){
        if(mainUser == null){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            if(preferences.getInt(MAIN_USER_ID, -1) == -1){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(MAIN_USER_ID, -1);
            }
            int mainUserID = preferences.getInt(MAIN_USER_ID, -1);

            for(int i=0; i < listUsers.size(); i++){
                if(listUsers.get(i).getId() == mainUserID){
                    mainUser = listUsers.get(i);
                    save(mainUser);
                }
            }

            if(mainUser == null){
                if(listUsers.size() != 0) {
                    mainUser = listUsers.get(0);
                   save(mainUser);
                }
            }
        }
        else{
            for(int i = 0; i<listUsers.size(); i++){
                if(mainUser.getId() == listUsers.get(i).getId()){
                    mainUser = listUsers.get(i);
                }
            }
        }
        printMainUser();
    }
    private void printMainUser(){
        TextView textMainUser = (TextView) findViewById(R.id.mainUser);
        if(mainUser == null){
            textMainUser.setText("There is no main user");
        }else
        textMainUser.setText(mainUser.getName() + " is the main user");
    }

    private void save(User user){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MAIN_USER_NAME, mainUser.getName());
        editor.putInt(MAIN_USER_ID, mainUser.getId());
        editor.putLong(MAIN_USER_BRACELET, mainUser.getBraceletID());
        editor.commit();
    }
}
