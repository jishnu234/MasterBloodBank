package com.example.masterbloodbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.masterbloodbank.ListActivity;
import com.example.masterbloodbank.MainActivity;
import com.example.masterbloodbank.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class needMenuFragment extends Fragment {

    private Spinner need_blood, need_district;
    private Button need_btn;
    private DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_need_menu, container, false);

        reference=FirebaseDatabase.getInstance().getReference("Donor");
        need_blood = (Spinner) view.findViewById(R.id.need_spinner_blood);
        need_district = (Spinner) view.findViewById(R.id.need_spinner_district);
        need_btn = (Button) view.findViewById(R.id.need_btn);


        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Need Menu");

        need_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDonor();
            }
        });


        return view;
    }

    private void getDonor() {

        if(!validateBlood() | !validateDistrict())
        {
            Toast.makeText((MainActivity)getActivity(), "Fields Cannot be empty..Please choose values to continue", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            final String blood=need_blood.getSelectedItem().toString();
            final String dist=need_district.getSelectedItem().toString();



            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot child:dataSnapshot.getChildren())
                    {
                        if(child.child("district").getValue(String.class).equals(dist))
                        {
                            if(child.child("blood_group").getValue(String.class).equals(blood))
                            {

                                Intent intent =new Intent((MainActivity)getActivity(), ListActivity.class);
                                intent.putExtra("blood",blood);
                                intent.putExtra("district",dist);
                                startActivity(intent);

                                need_blood.setSelection(0);
                                need_district.setSelection(0);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }


    private Boolean validateBlood() {
        String blood = need_blood.getSelectedItem().toString();

        if (blood.equals("Choose your blood group")) {
            return false;
        } else {
            return true;
        }
    }

    private Boolean validateDistrict() {

        String dist = need_district.getSelectedItem().toString();

        if (dist.equals("Choose your district")) {
            return false;
        } else {
            return true;
        }
    }
}
