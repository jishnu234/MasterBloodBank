package com.example.masterbloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    String blood_group,district;
    RecyclerViewHelper adapter;
    private ArrayList<Donor> arrayList;
    private Donor donor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list);

        recyclerView=findViewById(R.id.recycler_view);
        arrayList=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Donor");
        donor=new Donor();

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            blood_group=extras.getString("blood");
            district=extras.getString("district");
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecyclerViewHelper(this,arrayList);
        recyclerView.setAdapter(adapter);

        inflateLayout();
    }



    private void inflateLayout() {

        arrayList.clear();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot donorSnap:dataSnapshot.getChildren())
                {
                    if(donorSnap.child("district").getValue(String.class).equals(district) &&
                            donorSnap.child("blood_group").getValue(String.class).equals(blood_group))
                    {
                            String donor_name=donorSnap.child("name").getValue(String.class);
                            String donor_phone=donorSnap.child("phone").getValue(String.class);
                            String donor_blood=donorSnap.child("blood_group").getValue(String.class);
                            String donor_district=donorSnap.child("district").getValue(String.class);

                            donor.setBlood_group(donor_blood);
                            donor.setDistrict(donor_district);
                            donor.setPhone(donor_phone);
                            donor.setName(donor_name);
                            donor.setAge(null);

                            arrayList.add(new Donor(donor_name,null,donor_phone,donor_blood,donor_district));
                            adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




//        recyclerView.notify();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
