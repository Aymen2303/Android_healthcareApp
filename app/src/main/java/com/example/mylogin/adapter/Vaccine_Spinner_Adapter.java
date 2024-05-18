package com.example.mylogin.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mylogin.Classes.Vaccine_Class;

import java.util.List;

public class Vaccine_Spinner_Adapter extends ArrayAdapter<Vaccine_Class> {

    private LayoutInflater inflater;
    private List<Vaccine_Class> vaccineList;

    public Vaccine_Spinner_Adapter(Context context, List<Vaccine_Class> vaccineList) {
        super(context, 0, vaccineList);
        inflater = LayoutInflater.from(context);
        this.vaccineList = vaccineList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        Vaccine_Class vaccine = vaccineList.get(position);

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(vaccine.getName());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        Vaccine_Class vaccine = vaccineList.get(position);

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(vaccine.getName());

        return view;
    }




}

