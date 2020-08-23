package com.example.bous;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAjoutSourceRevenus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAjoutSourceRevenus extends Fragment {
    EditText editTextSource;
    DatabaseManager databaseManager;
    Button annulerBtn, sauverBtn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAjoutSourceRevenus() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAjoutSourceRevenus.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAjoutSourceRevenus newInstance(String param1, String param2) {
        FragmentAjoutSourceRevenus fragment = new FragmentAjoutSourceRevenus();
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
        View view = inflater.inflate(R.layout.fragment_ajout_source_revenus, container, false);
        editTextSource = view.findViewById(R.id.source_revenus_id);
        annulerBtn = view.findViewById(R.id.annuler_btn);
        sauverBtn = view.findViewById(R.id.sauvegarder_btn);
        //listeners
        sauverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sauver();
            }
        });

        annulerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annuler();
            }
        });
        return view;
    }

    public void annuler() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRevenus()).commit();
        String message = "Insertion annulee";
        Toast toast = Toast.makeText(getContext(), message, LENGTH_SHORT);
        toast.setGravity((Gravity.BOTTOM | Gravity.CENTER_VERTICAL), 1, 5);
        toast.show();
    }

    public void sauver() {
        if (FieldValidator.getFieldValidor().estRempli(editTextSource)) {
            databaseManager = new DatabaseManager(getContext());
            String source = editTextSource.getText().toString().toUpperCase();
            long resultat = databaseManager.insertSourceRevenus(source);
            if (resultat == 409) {
                //duplicate
                String message = source + " existe deja!";
                Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
                toast.setGravity((Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL), 1, 5);
                toast.show();

            } else if (resultat != -1) {
                //insertion reussie
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRevenus()).commit();
                String message = source + " a ete enregistre avec succes!";
                Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
                toast.setGravity((Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL), 1, 5);
                toast.show();
                editTextSource.setText("");
            } else {
                //insertion echouee
                String message = "echec lors de l'enregistrement!";
                Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
                toast.setGravity((Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL), 1, 5);
                toast.show();
                editTextSource.setText("");
            }
        }
    }
}