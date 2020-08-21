package com.example.bous;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDettes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDettes extends Fragment {
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

    public FragmentDettes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDettes.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDettes newInstance(String param1, String param2) {
        FragmentDettes fragment = new FragmentDettes();
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
        View view = inflater.inflate(R.layout.fragment_dettes, container, false);
        fab = view.findViewById(R.id.ajout_dettes);
        listView = view.findViewById(R.id.listviewDettes);
        arrayList = new ArrayList<>();
        arrayList.add(new CustomModel("22 Janvier 2020", "Sergio S. OSSON ", 250));
        customAdapter = new CustomAdapter(getContext(), R.layout.custom_listview, arrayList);
        listView.setAdapter(customAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAjoutDettes()).commit();
            }
        });
        return view;
    }
}