package com.example.bous;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreances#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreances extends Fragment {
    com.getbase.floatingactionbutton.FloatingActionButton fab;
    ListView listView;
    ArrayList<CustomModel> arrayList;
    CustomAdapter customAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCreances() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCreances.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCreances newInstance(String param1, String param2) {
        FragmentCreances fragment = new FragmentCreances();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creances, container, false);
        fab = view.findViewById(R.id.ajout_creances);
        listView = view.findViewById(R.id.listviewCreances);
        arrayList = DatabaseManager.getDatabaseManager(getContext()).selectCreances();
        customAdapter = new CustomAdapter(getContext(), R.layout.custom_listview, arrayList);
        listView.setAdapter(customAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                String individu = arrayList.get(position).getObjet();
                float montant = arrayList.get(position).getMontant();
                builder.setTitle("Remboursement de Creances");
                builder.setMessage("Avez-vous recu " + montant + " HTG de " + individu + " ?");
                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long resultat = arrayList.get(position).rembourserCreances(getContext(), DaysOfMonth.getDate());
                        dialog.dismiss();
                        if (resultat != -1) {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreances()).commit();
                            Screen.display("Remboursement enregistre!", getContext());
                        } else {
                            Screen.display("Erreur!", getContext());
                        }
                    }
                });

                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }

            ;

        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAjoutCreances()).commit();
            }
        });

        return view;
    }
}