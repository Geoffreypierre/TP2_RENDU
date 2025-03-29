package com.example.tp2_suite.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tp2_suite.R;

import java.util.ArrayList;

public class GridAdaptateur extends ArrayAdapter {
    ArrayList<Item> birdList ;

    public GridAdaptateur(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        birdList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v ;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.adaptateur_grid_layout, null);
        TextView textView = (TextView) v.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView3);
        textView.setText(birdList.get(position).getNomExo());
        imageView.setImageResource(birdList.get(position).getImagexo());

        return v;

    }

}
