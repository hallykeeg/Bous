package com.example.bous;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAjoutRevenus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAjoutRevenus extends Fragment implements AdapterView.OnItemSelectedListener {
    ArrayList<SourceRevenus> arrayListSourceRevenus;
    ArrayList<String> arrayNoms = new ArrayList<>();
    //ArrayList<Integer> arrayId = new ArrayList<>();
    Spinner spinner;
    ArrayAdapter arrayAdapter;
    SourceRevenus sourceRevenusItem;
    long idSelected;

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
        arrayListSourceRevenus = DatabaseManager.getDatabaseManager(getContext()).selectSourceRevenus();
        for (int i = 0; i < arrayListSourceRevenus.size(); i++) {
            arrayNoms.add(arrayListSourceRevenus.get(i).getId(), arrayListSourceRevenus.get(i).getNom());
        }
        spinner.setOnItemSelectedListener(this);
        arrayAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayNoms);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        idSelected = id;
        System.out.println(id);
        System.out.println(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}