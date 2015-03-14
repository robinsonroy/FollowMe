package com.followme.followme.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.followme.followme.R;

import java.util.ArrayList;

/**
 * Created by Robinson on 14/03/15.
 */
public class UserArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;

    public UserArrayAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.list_users, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_users, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values.get(position));

        // Change icon based on name
        String s = values.get(position);

        System.out.println(s);

        if (s.equals("Robinson")) {
            imageView.setImageResource(R.drawable.ic_star_rate_24px);

            return rowView;
        }
        return rowView;
    }
}
