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
 * Use the {@link FragmentAjoutRevenus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAjoutRevenus extends Fragment implements AdapterView.OnItemSelectedListener {
    private ArrayList<SourceRevenus> arrayListSourceRevenus;
    private DatePickerDialog picker;
    private ArrayList<String> arrayNoms = new ArrayList<>();
    //ArrayList<Integer> arrayId = new ArrayList<>();
    private Spinner spinner;
    private Map<Integer, String> mapSourceById = new HashMap<>();
    private ArrayAdapter arrayAdapter;
    private SourceRevenus sourceRevenusItem;
    private String selectedItem;
    private EditText editTextDate, editTextMontant, editTextMontantEpargne;
    private Button buttonSauver, buttonAnnuler;
    private ArrayList<EditText> arrayListEditText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAjoutRevenus() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAjoutRevenus.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAjoutRevenus newInstance(String param1, String param2) {
        FragmentAjoutRevenus fragment = new FragmentAjoutRevenus();
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
        View view = inflater.inflate(R.layout.fragment_ajout_revenus, container, false);
        spinner = view.findViewById(R.id.spinner_sourceRevenus);
        buttonAnnuler = view.findViewById(R.id.annuler_revenus_btn);
        buttonSauver = view.findViewById(R.id.sauver_revenus_btn);

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
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextMontant = view.findViewById(R.id.editTextMontant);
        editTextMontantEpargne = view.findViewById(R.id.editTextMontantEpargne);
        //Afin de Tester s ils sont remplis
        arrayListEditText = new ArrayList<>();
        arrayListEditText.add(editTextDate);
        arrayListEditText.add(editTextMontant);
        arrayListEditText.add(editTextMontantEpargne);
        //date selection
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
                                    tmpJour = "0" + (dayOfMonth);
                                } else {
                                    tmpJour = String.valueOf(dayOfMonth);
                                }
                                if (month < 10) {
                                    tmpMois = "0" + (month);
                                } else {
                                    tmpMois = String.valueOf(month);
                                }

                                date = (year) + "-" + tmpMois + "-" + tmpJour;
                                editTextDate.setText(date);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        arrayListSourceRevenus = DatabaseManager.getDatabaseManager(getContext()).selectSourceRevenus();
        //on met les noms et id ds un tableau

        for (SourceRevenus s : arrayListSourceRevenus
        ) {
            mapSourceById.put(s.getId(), s.getNom());
            arrayNoms.add(s.getNom());

        }

        spinner.setOnItemSelectedListener(this);
        arrayAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayNoms);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
            //tous les champs sont remplis
            String date;
            float montant, montantEpargne;
            int idSourceSelected = getKeyByValue(mapSourceById, selectedItem);
            if (idSourceSelected != 409) {
                //on a trouve une seule cle pour la valeur
                montant = Float.parseFloat(editTextMontant.getText().toString());
                montantEpargne = Float.parseFloat(editTextMontantEpargne.getText().toString());
                date = editTextDate.getText().toString();
                Revenus revenus = new Revenus(date, montant, montantEpargne, idSourceSelected);
                long resultat = DatabaseManager.getDatabaseManager(getContext()).insertRevenus(revenus);
                if (resultat != -1) {
                    //insertion reussie
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRevenus()).commit();
                    Screen.display("REVENU ENREGISTRE", getContext());
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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRevenus()).commit();
        Screen.display("ANNULATION", getContext());
    }

    public int getKeyByValue(Map<Integer, String> hashMap, String value) {
        int key = 0, compteur = 0;
        for (Map.Entry<Integer, String> set : hashMap.entrySet()) {
            if (set.getValue().equals(value)) {
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