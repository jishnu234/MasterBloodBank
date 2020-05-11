package com.example.masterbloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Fragment needMenu,contact,about,donate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

         drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
         navigationView=(NavigationView)findViewById(R.id.navigation_view);
         toolbar=(Toolbar)findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         needMenu=new needMenuFragment();
         contact=new contact_usFragment();
         about=new about_MenuFragment();
         donate=new Donate_bloodFragment();

//         SplashScreen.splashScreen.finish();



        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.drawertoggleColor));
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,needMenu).commit();
        navigationView.setCheckedItem(R.id.need_blood);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.need_blood:
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container,needMenu,null);
                        fragmentTransaction.commit();
                        break;
                    case R.id.donate_blood:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,donate,null).commit();
                        break;
                    case R.id.contact_us:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,contact,null).commit();
                        navigationView.getCheckedItem().setChecked(false);
                        break;
                    case R.id.about:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,about,null).commit();
                        navigationView.getCheckedItem().setChecked(false);

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        permissionCheck();
    }

    public void permissionCheck() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED
        & ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},125);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},145);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==125)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            else
            {
                Toast.makeText(this, "permission Denied..", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==145)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            else
            {
                Toast.makeText(this, "permission Denied..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("EXIT !");
            builder.setIcon(R.drawable.blood_drop);
            builder.setMessage("Do you really want to exit");
            builder.setCancelable(false);
            builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
            Button positive=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positive.setTextColor(Color.RED);
            Button negative=dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negative.setTextColor(Color.RED);


        }
    }
}
