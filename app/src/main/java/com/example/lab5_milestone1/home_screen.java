package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class home_screen extends AppCompatActivity {

    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        TextView welcome = (TextView) findViewById(R.id.titleWelcome);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");
        welcome.setText("Welcome "+username);

        notes = readNotes(username);
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note: notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.listNotes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("onItemClick", "Position: "+position);
                Intent intent = new Intent(getApplicationContext(), edit_note_screen.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Note> readNotes(String username) {
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        return dbHelper.readNotes(username);
    }
    //https://developer.android.com/guide/topics/ui/menus#java
    /*
    To specify the options menu for an activity, override
    onCreateOptionsMenu() (fragments provide their own
    onCreateOptionsMenu() callback). In this method, you can inflate
    your menu resource (defined in XML) into the Menu provided in the
    callback. For example:
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //https://developer.android.com/guide/topics/ui/menus#java
    /*
    When the user selects an item from the options menu (including
    action items in the app bar), the system calls your activity's
    onOptionsItemSelected() method. This method passes the MenuItem
    selected. You can identify the item by calling getItemId(), which
    returns the unique ID for the menu item (defined by the android:id
    attribute in the menu resource or with an integer given to the
    add() method). You can match this ID against known menu items to
    perform the appropriate action. For example:
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch(item.getItemId()){
            case R.id.addNote:
                addNote();
                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("username").apply();
        goToLoginScreen();
    }

    private void addNote() {
        goToEditNoteScreen();
    }

    public void goToLoginScreen() {
        Intent intent = new Intent(this, login_screen.class);
        startActivity(intent);
    }

    public void goToEditNoteScreen(){
        Intent intent = new Intent(this, edit_note_screen.class);
        startActivity(intent);
    }
}