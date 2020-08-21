package com.example.bous;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    ArrayList<CustomModel> arrayList;
    Context context;


    public CustomAdapter(@NonNull Context context, int resource, ArrayList<CustomModel> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        convertView = layoutInflater.inflate(R.layout.custom_listview, parent, false);


        TextView textViewDate = (TextView) convertView.findViewById(R.id.date_id);
        TextView textViewObjet = (TextView) convertView.findViewById(R.id.id_objet);
        TextView textViewMontant = (TextView) convertView.findViewById(R.id.montant_id);

        textViewDate.setText(arrayList.get(position).getDate());
        textViewObjet.setText(arrayList.get(position).getObjet());
        textViewMontant.setText(String.valueOf(arrayList.get(position).getMontant()));

        return convertView;
    }
}
