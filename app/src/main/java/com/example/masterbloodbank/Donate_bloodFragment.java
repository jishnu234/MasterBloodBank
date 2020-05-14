package com.example.masterbloodbank;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.masterbloodbank.MainActivity;
import com.example.masterbloodbank.Donor;
import com.example.masterbloodbank.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Donate_bloodFragment extends Fragment {

    private TextInputLayout reg_name,reg_age,reg_phone;
    private Spinner reg_blood,reg_district;
    private Button reg_btn;
    DatabaseReference reference;
    Donor donor;
    String TAG="Donate_blood_fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_donate_blood, container, false);

        reference=FirebaseDatabase.getInstance().getReference("Donor");
        reg_name=view.findViewById(R.id.reg_name);
        reg_age=view.findViewById(R.id.reg_age);
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

        if( !validateAge() | !validatePhone() | !validateUser())
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
        final String phone=reg_phone.getEditText().getText().toString().trim();


         reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(phone).exists())
                {
                        Toast.makeText((MainActivity) getActivity(), "User already registered ", Toast.LENGTH_SHORT).show();
                        reg_age.getEditText().setText("");
                        reg_name.getEditText().setText("");
                        reg_phone.getEditText().setText("");
                        reg_blood.setSelection(0);
                        reg_district.setSelection(0);
                }
                else
                {
                    addData();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            Log.d(TAG, "registerUser: status 0 started");


        }


    private void addData() {


        String age=reg_age.getEditText().getText().toString();
        String blood=reg_blood.getSelectedItem().toString();
        String dist=reg_district.getSelectedItem().toString();
        String name=reg_name.getEditText().getText().toString().trim();
        String phone=reg_phone.getEditText().getText().toString().trim();

        donor=new Donor();

        donor.setAge(age);
        donor.setBlood_group(blood);
        donor.setDistrict(dist);
        donor.setName(name);
        donor.setPhone(phone);

        reference.child(phone).setValue(donor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText((MainActivity)getActivity(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    reg_age.getEditText().setText("");
                    reg_name.getEditText().setText("");
                    reg_phone.getEditText().setText("");
                    reg_blood.setSelection(0);
                    reg_district.setSelection(0);
                }
                else
                {
                    Toast.makeText((MainActivity)getActivity(), "Something went wrong,can't insert data", Toast.LENGTH_SHORT).show();
                }
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
//        String nowhiteSpaces="\\A\\w{4,20}\\z";

        if(user.isEmpty())
        {
            reg_name.setError("This field cannot be empty");
            return false;
        }
//        else if(!user.matches(nowhiteSpaces))
//        {
//            reg_name.setError("white spaces are not allowed");
//            return false;
//        }
        else
        {
            reg_name.setError(null);
            return true;
        }

    }
    private Boolean validateAge() {
        String age=reg_age.getEditText().getText().toString();
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(age.isEmpty())
        {
            reg_age.setError("This field cannot be empty");
            return false;
        }
        else if(Integer.parseInt(age)<18)
        {
            reg_age.setError("You cant register,you are under aged");
            return false;
        }
        else
        {
            reg_age.setError(null);
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
