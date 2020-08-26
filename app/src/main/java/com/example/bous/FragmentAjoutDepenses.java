package com.example.bous;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAjoutDepenses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAjoutDepenses extends Fragment {
    private ArrayList<ObjetDepenses> arrayListObjetDepenses;
    private DatePickerDialog picker;
    private ArrayList<String> arrayNoms = new ArrayList<>();
    //ArrayList<Integer> arrayId = new ArrayList<>();
    private Spinner spinner;
    private Map<Integer, String> mapObjectById = new HashMap<>();
    private ArrayAdapter arrayAdapter;
    private ObjetDepenses objetDepenses;
    private String selectedItem;
    private EditText editTextDate, editTextMontant,;
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
        return inflater.inflate(R.layout.fragment_ajout_depenses, container, false);
    }
}