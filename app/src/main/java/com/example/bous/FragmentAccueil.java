package com.example.bous;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAccueil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAccueil extends Fragment {
    private onButtonAccueilClicked accueilButtonListener;
    Button depensesButton;
    Button revenusButton;
    Button dettesButton;
    Button creancesButton;
    Button statsButton;
    Button epargneButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onButtonAccueilClicked) {
            accueilButtonListener = (onButtonAccueilClicked) context;

        } else {
            throw new ClassCastException(context.toString() + "must implement accueilButtonListener");
        }

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAccueil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAccueil.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAccueil newInstance(String param1, String param2) {
        FragmentAccueil fragment = new FragmentAccueil();
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
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        depensesButton = (Button) view.findViewById(R.id.depense_btn);
        revenusButton = (Button) view.findViewById(R.id.revenus_btn);
        epargneButton = (Button) view.findViewById(R.id.epargne_btn);
        dettesButton = (Button) view.findViewById(R.id.dettes_btn);
        creancesButton = (Button) view.findViewById(R.id.creances_btn);
        statsButton = (Button) view.findViewById(R.id.stats_btn);

        depensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accueilButtonListener.depensesButtonAction();
            }
        });
        revenusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accueilButtonListener.revenusButtonAction();
            }
        });
        dettesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accueilButtonListener.dettesButtonAction();
            }
        });
        creancesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accueilButtonListener.creancesButtonAction();
            }
        });
        epargneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accueilButtonListener.epargneButtonAction();
            }
        });
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accueilButtonListener.statsButtonAction();
            }
        });
        return view;
    }

    public interface onButtonAccueilClicked {

        public void depensesButtonAction();

        public void revenusButtonAction();

        public void epargneButtonAction();

        public void dettesButtonAction();

        public void creancesButtonAction();

        public void statsButtonAction();
    }
}