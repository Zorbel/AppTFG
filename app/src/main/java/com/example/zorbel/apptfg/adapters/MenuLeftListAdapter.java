package com.example.zorbel.apptfg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zorbel.apptfg.views.MenuLeftItem;
import com.example.zorbel.apptfg.R;

import java.util.List;

public class MenuLeftListAdapter extends ArrayAdapter {

    public MenuLeftListAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_left_item, null);
        }


        TextView name = (TextView) convertView.findViewById(R.id.menu_entry);
        ImageView im = (ImageView) convertView.findViewById(R.id.menu_entry_image);

        MenuLeftItem item = (MenuLeftItem) getItem(position);
        name.setText(item.getName());
        if (item.getImage() != null) {
            im.setImageDrawable(item.getImage());
        }

        return convertView;
    }
}

