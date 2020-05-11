package com.example.masterbloodbank;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewHelper extends RecyclerView.Adapter<RecyclerViewHelper.ViewHolder> {


    Context context;
    private ArrayList<Donor> donor;
    MainActivity activity=new MainActivity();

    public RecyclerViewHelper(Context context, ArrayList<Donor> donor) {
        this.context = context;
        this.donor = donor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_list_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(donor.get(position).getName().toUpperCase());
        holder.blood.setText(donor.get(position).getBlood_group());
        holder.dist.setText(donor.get(position).getDistrict());
        holder.phone.setText(donor.get(position).getPhone());
        final int pos=position;
        final String name=donor.get(position).getName();

        holder.communicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Items[]=new String[]{"Make Call ("+name+")","Send SMS ("+name+")","Cancel"};

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setItems(Items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which==0)
                        {
                            makeCall(donor.get(pos).getPhone());
                            dialog.cancel();
                        }
                        else if(which==1)
                        {
                            sendSMS(donor.get(pos).getPhone());

                            dialog.cancel();
                        }
//                        else if(which==2)
//                        {
//                            dialog.cancel();
//                            shareWtsApp();
//                        }
                        else
                        {
                            dialog.cancel();
                        }

                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

    }

//    private void shareWtsApp() {
//
//
//        final Dialog dialog=new Dialog(context);
//        dialog.setContentView(R.layout.sms_dialog);
//        dialog.setCancelable(false);
//        final EditText sms_name=dialog.findViewById(R.id.sms_name);
//        final EditText sms_phone=dialog.findViewById(R.id.sms_phone);
//        Button btn=dialog.findViewById(R.id.sms_submit_btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String name=sms_name.getText().toString().trim();
//                String phone_no=sms_phone.getText().toString();
//                if(name.isEmpty() || phone_no.isEmpty())
//                {
//                    Toast.makeText(context, "fields cannot be empty", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    dialog.cancel();
//                   String wtsappMsg= "URGENT BLOOD REQUIREMENT(BB Master)\n\nName:" + name + "\nPhone:" + phone_no + "\n contact me immediately.....";
//                    Intent intent=new Intent(Intent.ACTION_SEND);
//                    intent.setType("text/plain");
//                    intent.setPackage("com.whatsapp");
//                    intent.putExtra(Intent.EXTRA_TEXT,wtsappMsg);
//                    context.startActivity(intent);
//
//                }
//
//            }
//        });
//
//        dialog.show();
//
//
//
//    }

    private void sendSMS(final String phone) {

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
//            MainActivity activity=new MainActivity();
            activity.permissionCheck();
        }
        else
        {

            final Dialog dialog=new Dialog(context);
            dialog.setContentView(R.layout.sms_dialog);
            dialog.setCancelable(false);
            final EditText sms_name=dialog.findViewById(R.id.sms_name);
            final EditText sms_phone=dialog.findViewById(R.id.sms_phone);
            Button btn=dialog.findViewById(R.id.sms_submit_btn);
            Button btn_cancel=dialog.findViewById(R.id.sms_cancel_btn);
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name=sms_name.getText().toString().trim();
                    String phone_no=sms_phone.getText().toString();
                    if(name.isEmpty() || phone_no.isEmpty())
                    {
                        Toast.makeText(context, "fields cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        dialog.cancel();
                        String message = "URGENT BLOOD REQUIREMENT(BB Master)\n\nName:" + name + "\nPhone:" + phone_no + "\n contact me immediately.....";
                        SmsManager manager = SmsManager.getDefault();
                        manager.sendTextMessage(phone, null, message, null, null);
                        Toast.makeText(context, "Sms sent successfully", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            dialog.show();

        }
    }


    private void makeCall(String phone) {

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
//            MainActivity activity=new MainActivity();
            activity.permissionCheck();
        }
        else
        {
            Intent intent=new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+phone));
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return donor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,dist,blood,phone;
        private ImageView communicate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.custom_name);
            dist=itemView.findViewById(R.id.custom_dist);
            blood=itemView.findViewById(R.id.custom_blood);
            phone=itemView.findViewById(R.id.custom_phone);
            communicate=itemView.findViewById(R.id.custom_call);
        }
    }
}
