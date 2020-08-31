package com.example.bous;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAjoutDepenses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAjoutDepenses extends Fragment implements AdapterView.OnItemSelectedListener {
    private ArrayList<ObjetDepenses> arrayListObjetDepenses;
    private DatePickerDialog picker;
    private ArrayList<String> arrayNoms = new ArrayList<>();
    //ArrayList<Integer> arrayId = new ArrayList<>();
    private Spinner spinner;
    private Map<Integer, String> mapObjectById = new HashMap<>();
    private ArrayAdapter arrayAdapter;
    private ObjetDepenses objetDepenses;
    private String selectedItem;
    private EditText editTextDate, editTextMontant;
    private Button buttonSauver, buttonAnnuler;
    private ArrayList<EditText> arrayListEditText;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAjoutDepenses() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAjoutDepenses.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAjoutDepenses newInstance(String param1, String param2) {
        FragmentAjoutDepenses fragment = new FragmentAjoutDepenses();
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
        View view = inflater.inflate(R.layout.fragment_ajout_depenses, container, false);
        spinner = view.findViewById(R.id.spinner_ObjetDepense);
        buttonAnnuler = view.findViewById(R.id.annuler_depenses_btn);
        buttonSauver = view.findViewById(R.id.sauver_depenses_btn);
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextMontant = view.findViewById(R.id.editTextMontant);
        arrayListEditText = new ArrayList<>();
        arrayListEditText.add(editTextDate);
        arrayListEditText.add(editTextMontant);
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
        //date
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

                                date = String.valueOf(year) + "-" + tmpMois + "-" + tmpJour;
                                editTextDate.setText(date);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        //recuperation des objets de depenses
        arrayListObjetDepenses = DatabaseManager.getDatabaseManager(getContext()).selectObjetDepenses();
        for (ObjetDepenses o : arrayListObjetDepenses) {
            mapObjectById.put(o.getId(), o.getNom());
            arrayNoms.add(o.getNom());
        }
        spinner.setOnItemSelectedListener(this);
        arrayAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayNoms);
        spinner.setAdapter(arrayAdapter);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedItem = spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void sauver() {
        if (FieldValidator.getFieldValidor().estRempli(arrayListEditText)) {
            //les champs sont remplis
            String date;
            float montant;
            int idObjectSelected = getKeyByValue(mapObjectById, selectedItem);
            if (idObjectSelected != 409) {
                //on a trouve une seule cle pr la valeur
                montant = Float.parseFloat(editTextMontant.getText().toString());
                date = editTextDate.getText().toString();
                Depenses depenses = new Depenses(date, montant, idObjectSelected);
                long result = DatabaseManager.getDatabaseManager(getContext()).insertDepenses(depenses);
                if (result != -1) {
                    //insertion reussie
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDepense()).commit();
                    Screen.display("Depense enregistree", getContext());
                } else {
                    Screen.display("ECHEC D'ENREGISTREMENT", getContext());

                }
            } else {
                //plusieurs clef pour la valeur
                Screen.display("ERREUR", getContext());
            }
        }
    }

    public void annuler() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDepense()).commit();
        Screen.display("ANNULATION", getContext());
    }

    public int getKeyByValue(Map<Integer, String> hashMap, String value) {
        int key = 0, compteur = 0;
        for (Map.Entry<Integer, String> set : hashMap.entrySet()) {
            if (set.getValue() == value) {
                key = set.getKey();
                compteur++;
            }
        }
        if (compteur == 1) {
            return key;
        } else {
            return 409;
        }
    }
}