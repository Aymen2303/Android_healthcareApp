package com.example.mylogin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mylogin.Classes.kid_class;

import java.util.List;

public class Kid_Spinner_Adapter extends ArrayAdapter<kid_class> {

    private LayoutInflater inflater;
    private List<kid_class> kidList;

    public Kid_Spinner_Adapter(Context context, List<kid_class> kidList) {
        super(context, 0, kidList);
        inflater = LayoutInflater.from(context);
        this.kidList = kidList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        kid_class kid = kidList.get(position);

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(kid.getKidId() + " - " + kid.getKidName());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        kid_class kid = kidList.get(position);

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(kid.getKidId() + " - " + kid.getKidName());

        return convertView;
    }
}
