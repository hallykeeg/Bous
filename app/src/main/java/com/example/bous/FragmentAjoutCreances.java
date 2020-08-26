package com.example.bous;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAjoutCreances#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAjoutCreances extends Fragment {
    DatePickerDialog picker;
    EditText editTextDate, editTextMontant, editTextIndividu;
    Button buttonSauver, buttonAnnuler;
    ArrayList<EditText> arrayListEditText;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAjoutCreances() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAjoutCreances.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAjoutCreances newInstance(String param1, String param2) {
        FragmentAjoutCreances fragment = new FragmentAjoutCreances();
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
        View view = inflater.inflate(R.layout.fragment_ajout_creances, container, false);
        //recuperation des composants graphiques
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextIndividu = view.findViewById(R.id.editTextIndividu);
        editTextMontant = view.findViewById(R.id.editTextMontant);

        //ajouter les champs pr verifier s ils sont remplis
        arrayListEditText = new ArrayList<>();
        arrayListEditText.add(editTextDate);
        arrayListEditText.add(editTextIndividu);
        arrayListEditText.add(editTextMontant);

        buttonAnnuler = view.findViewById(R.id.annuler_creance_btn);
        buttonSauver = view.findViewById(R.id.sauver_btn_creance);
        //listeners
        buttonSauver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sauver();
            }
        });
        buttonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annuler();
            }
        });
        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String tmpJour, tmpMois, date;
                                int day, month = monthOfYear + 1;
                                if (dayOfMonth < 10) {
                                    tmpJour = "0" + String.valueOf(dayOfMonth);
                                } else {
                                    tmpJour = String.valueOf(dayOfMonth);
                                }
                                if (month < 10) {
                                    tmpMois = "0" + String.valueOf(month);
                                } else {
                                    tmpMois = String.valueOf(month);
                                }
                                date = tmpJour + "-" + tmpMois + "-" + String.valueOf(year);
                                editTextDate.setText(date);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        return view;
    }

    public void sauver() {
        String date, individu;
        float montant;
        if (FieldValidator.getFieldValidor().estRempli(arrayListEditText)) {
            //tous les champs sont remplis
            date = editTextDate.getText().toString();
            individu = editTextIndividu.getText().toString();
            montant = Float.parseFloat(editTextMontant.getText().toString());
            long resultat;
            resultat = DatabaseManager.getDatabaseManager(getContext()).insertCreances(new Creances(date, individu, montant));
            if (resultat != -1) {
                //insertion reussie
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreances()).commit();
                Screen.display("CREANCE ENREGISTREE", getContext());
            } else {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreances()).commit();
                Screen.display("ERREUR LORS DE L'ENREGISTREMENT", getContext());
            }
        }

    }

    public void annuler() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreances()).commit();
        Screen.display("ANNULATION", getContext());
    }
}