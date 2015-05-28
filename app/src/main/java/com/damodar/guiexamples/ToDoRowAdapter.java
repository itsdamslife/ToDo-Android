package com.damodar.guiexamples;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by damodar on 28/05/15.
 */
public class ToDoRowAdapter extends ArrayAdapter<Map> {

    MainActivity ctx;
    List<Map> tasks;

    public ToDoRowAdapter(Context context, List<Map> values) {

        super(context, R.layout.todo_item, values);

        ctx = (MainActivity)context;
        tasks = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());

        View theView = convertView;
        if(theView == null) {
            theView = theInflater.inflate(R.layout.todo_item, parent, false);
        }

        Map aTask = getItem(position);

        TextView textView = (TextView)theView.findViewById(R.id.todoText);
        String key = "";
        Boolean value = false;

        Iterator myVeryOwnIterator = aTask.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            key=(String)myVeryOwnIterator.next();
            value=(Boolean)aTask.get(key);
        }

        textView.setText(key);

        if(value == true)
            textView.setTextColor(Color.LTGRAY);
        else
            textView.setTextColor(Color.BLACK);

        CheckBox cbx = (CheckBox)theView.findViewById(R.id.checkMark);
        cbx.setTag(position);
        cbx.setChecked(value);

        cbx.setOnCheckedChangeListener(itemDone);

        return theView;

    }

    CompoundButton.OnCheckedChangeListener itemDone = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
           int position = (int)buttonView.getTag();

            Map aTask = getItem(position);

            String key = "";
            Boolean value = false;

            Iterator myVeryOwnIterator = aTask.keySet().iterator();
            while(myVeryOwnIterator.hasNext()) {
                key=(String)myVeryOwnIterator.next();
                value=(Boolean)aTask.get(key);
            }

            value = buttonView.isChecked();
            aTask.put(key, value);
            ctx.updateDataSource(position, aTask);


            View vw = (View)buttonView.getParent();
            TextView textView = (TextView) vw.findViewById(R.id.todoText);

            if(value == true)
                textView.setTextColor(Color.LTGRAY);
            else
                textView.setTextColor(Color.BLACK);

            String doneStr = "";
            if(value == true) {
                doneStr = "completed";
            }
            else {
                doneStr = "incomplete";
            }


            String toastText = key + " is " + doneStr;

            Toast.makeText(ctx, toastText, Toast.LENGTH_SHORT).show();

        }
    };
}
