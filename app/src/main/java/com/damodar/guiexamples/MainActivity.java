package com.damodar.guiexamples;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    private EditText newTaskTextField;

    private ListView todoListView;

    private List<String> favs;
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

        favs = new ArrayList<String>();

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

        favs.add(0,task);

        ArrayAdapter adp = (ArrayAdapter)favAdapter;
        adp.notifyDataSetChanged();

        newTaskTextField.setText("");

        Toast.makeText(this, "Added \"" + task + "\"", Toast.LENGTH_SHORT ).show();
    }

}
