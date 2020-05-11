package com.example.masterbloodbank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masterbloodbank.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class about_MenuFragment extends Fragment {

    public about_MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about__menu, container, false);
    }
}
