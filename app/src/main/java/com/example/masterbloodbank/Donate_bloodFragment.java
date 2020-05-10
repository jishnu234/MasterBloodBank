package com.example.masterbloodbank;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Donate_bloodFragment extends Fragment {

    private TextInputLayout reg_name,reg_email,reg_phone;
    private Spinner reg_blood,reg_district;
    private Button reg_btn;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_donate_blood, container, false);

        reference=FirebaseDatabase.getInstance().getReference("Donor");
        reg_name=view.findViewById(R.id.reg_name);
        reg_email=view.findViewById(R.id.reg_email);
        reg_phone=view.findViewById(R.id.reg_phone);
        reg_blood=view.findViewById(R.id.reg_blood_group);
        reg_district=view.findViewById(R.id.reg_district);
        reg_btn=view.findViewById(R.id.reg_btn);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Registration");

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        return view;
    }

    private void registerUser() {

        if( !validateEmail() | !validatePhone() | !validateUser())
        {
            return;
        }
        if( !validateDistrict())
        {
            Toast.makeText((MainActivity)getActivity(), "please select your district", Toast.LENGTH_SHORT).show();
        }
        if(!validateBlood())
        {
            Toast.makeText((MainActivity)getActivity(), "please select your blood group", Toast.LENGTH_SHORT).show();
        }




//        Toast.makeText((MainActivity)getActivity(),"validation successfull", Toast.LENGTH_SHORT).show();
        String name=reg_name.getEditText().getText().toString().trim();
        final String phone=reg_phone.getEditText().getText().toString().trim();
        final String email=reg_email.getEditText().getText().toString();
        String blood=reg_blood.getSelectedItem().toString();
        String dist=reg_district.getSelectedItem().toString();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(phone).exists())
                {

                    if(email.equals(dataSnapshot.child(phone).child("email").getValue(String.class))) {
                        Toast.makeText((MainActivity) getActivity(), "user Already found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private Boolean validateDistrict() {

        String district=reg_district.getSelectedItem().toString();
        String exception="Choose your district";
        if(district.equals(exception))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    private Boolean validateUser() {

        String user=reg_name.getEditText().getText().toString().trim();
        String nowhiteSpaces="\\A\\w{4,20}\\z";

        if(user.isEmpty())
        {
            reg_name.setError("This field cannot be empty");
            return false;
        }
        else if(!user.matches(nowhiteSpaces))
        {
            reg_name.setError("white spaces are not allowed");
            return false;
        }
        else
        {
            reg_name.setError(null);
            return true;
        }

    }
    private Boolean validateEmail() {
        String emailId=reg_email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(emailId.isEmpty())
        {
            reg_email.setError("This field cannot be empty");
            return false;
        }
        else if(!emailId.matches(emailPattern))
        {
            reg_email.setError("Invalid email entered");
            return false;
        }
        else
        {
            reg_email.setError(null);
            return true;
        }
    }
    private Boolean validatePhone(){

        String phone=reg_phone.getEditText().getText().toString().trim();
        if(phone.isEmpty())
        {
            reg_phone.setError("Field cannot be empty");
            return false;
        }
        else if(phone.length()!=10)
        {
            reg_phone.setError("Invalid Phone Number entered");
            return false;
        }
        else
        {
            reg_phone.setError(null);
            return true;
        }
    }

    private Boolean validateBlood() {
        String blood=reg_blood.getSelectedItem().toString();
        String exception="Choose your blood group";
        if(blood.equals(exception))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
