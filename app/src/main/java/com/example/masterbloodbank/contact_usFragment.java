package com.example.masterbloodbank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.masterbloodbank.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;


public class contact_usFragment extends Fragment {

    private TextInputLayout subject,message;
    private FloatingActionButton email_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_contact_us, container, false);


        subject= view.findViewById(R.id.subject);
        message= view.findViewById(R.id.message);

        email_btn=(FloatingActionButton) view.findViewById(R.id.email_btn);



        email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMail();
            }
        });


        return view;
    }

    private void sendMail() {

        String msg=message.getEditText().getText().toString();
        String sub=subject.getEditText().getText().toString();

        if(msg.isEmpty())
        {
            message.setError("Field cannot be empty..");
            message.requestFocus();
        }
        if (sub.isEmpty())
        {
            subject.setError("Field cannot be empty..");
            subject.requestFocus();
        }
        if(!msg.isEmpty() & !sub.isEmpty())
        {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"jishnuvedhikz88@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT,sub);
            intent.putExtra(Intent.EXTRA_TEXT,msg);
            startActivity(intent);
        }
    }
}
