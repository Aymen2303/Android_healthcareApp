package com.example.mylogin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mylogin.Classes.Client_Class;

import java.util.List;

public class Client_Spinner_Adapter extends ArrayAdapter<Client_Class> {

    private LayoutInflater inflater;
    private List<Client_Class> clientList;


    public Client_Spinner_Adapter(Context context, List<Client_Class> clientList) {
        super(context, 0, clientList);
        inflater = LayoutInflater.from(context);
        this.clientList = clientList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        Client_Class client = clientList.get(position);

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(client.getId() + " - " + client.getName());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        Client_Class client = clientList.get(position);

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(client.getId() + " - " + client.getName());

        return convertView;
    }

}
