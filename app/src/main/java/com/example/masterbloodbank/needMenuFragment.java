package com.example.masterbloodbank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class needMenuFragment extends Fragment {

    private Spinner need_blood,need_district;
    private Button need_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_need_menu, container, false);

        need_blood=(Spinner) view.findViewById(R.id.need_spinner_blood);
        need_district=(Spinner) view.findViewById(R.id.need_spinner_district);
        need_btn=(Button) view.findViewById(R.id.need_btn);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Need Menu");


        return view;
    }
}
