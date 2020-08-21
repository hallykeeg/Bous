package com.example.bous;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAjoutCreances#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAjoutCreances extends Fragment {
    DatePickerDialog picker;
    EditText editTextDate;
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
        editTextDate = view.findViewById(R.id.editTextDate);
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
}