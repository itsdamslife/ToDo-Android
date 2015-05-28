package com.damodar.guiexamples;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {


    private EditText newTaskTextField;

    public ListView todoListView;

    private List<Map> favs;
    private ListAdapter favAdapter;

    ListAdapter getAdapter() {
        return favAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newTaskTextField = (EditText)findViewById(R.id.editText);
        todoListView = (ListView)findViewById(R.id.theListView);

        favs = new ArrayList<Map>();

        fetchJSONAndFetchData();

        favAdapter = new ToDoRowAdapter(this, favs);

        ListView theListView = (ListView)findViewById(R.id.theListView);

        theListView.setAdapter(favAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String tvShowSelected = "You selected \"" + String.valueOf(parent.getItemAtPosition(position)) + "\"";
                Toast.makeText(MainActivity.this, tvShowSelected, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {

        convertToJSONAndStore();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        convertToJSONAndStore();

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTask(View view) {

        String task = String.valueOf(newTaskTextField.getText());

        if(task.length() <= 0) {
            return;
        }

        Map<String, Boolean> itemToDo = new HashMap<String, Boolean>();
        itemToDo.put(task, false);

        favs.add(0,itemToDo);

        ArrayAdapter adp = (ArrayAdapter)favAdapter;
        adp.notifyDataSetChanged();

        newTaskTextField.setText("");

        Toast.makeText(this, task + " is added", Toast.LENGTH_SHORT ).show();
    }

    public void updateDataSource(int position, Map<String, Boolean> item) {

        favs.remove(position);
        favs.add(position, item);

    }

    public void convertToJSONAndStore()
    {
        //converting the collection into a JSON
        JSONArray result= new JSONArray(favs);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("TODOLIST", 0);

        //Storing the string in pref file
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString("TODOLIST", result.toString());
        prefEditor.commit();
    }

    public void fetchJSONAndFetchData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("TODOLIST", 0);

        //Getting the JSON from pref
        String storedCollection = pref.getString("TODOLIST", null);

        if(storedCollection.length() <= 0) {
            return;
        }

        //Parse the string to populate your favs list
        try {

            JSONArray array = new JSONArray(storedCollection);

            for(int i =0; i<array.length(); i++) {

                JSONObject dict = (JSONObject)array.get(i);

                Map<String, Boolean> aItem = new HashMap<String, Boolean>();

                Iterator<String> keysItr = dict.keys();
                while(keysItr.hasNext()) {

                    String key = keysItr.next();
                    Object value = dict.get(key);
                    Boolean val = Boolean.valueOf(value.toString());

                    aItem.put(key, val);
                }

                favs.add(aItem);
            }


        } catch (JSONException e) {
            Log.e("ToDo", "while parsing", e);
        }
    }

}
