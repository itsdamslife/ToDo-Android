package com.damodar.guiexamples;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by damodar on 28/05/15.
 */
public class ToDoRowAdapter extends ArrayAdapter<String> {

    MainActivity ctx;
    List<String> tasks;

    public ToDoRowAdapter(Context context, List<String> values) {

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

        String aTask = getItem(position);

        TextView textView = (TextView)theView.findViewById(R.id.todoText);
        textView.setText(aTask);

        CheckBox cbx = (CheckBox)theView.findViewById(R.id.checkMark);
        cbx.setTag(position);

        cbx.setOnCheckedChangeListener(itemDone);

        return theView;

    }

    CompoundButton.OnCheckedChangeListener itemDone = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
           int position = (int)buttonView.getTag();

            Toast.makeText(ctx, "Tapped " + position, Toast.LENGTH_SHORT).show();

            tasks.remove(position);

            ArrayAdapter adp = (ArrayAdapter)ctx.getAdapter();
            adp.notifyDataSetChanged();
        }
    };
}
