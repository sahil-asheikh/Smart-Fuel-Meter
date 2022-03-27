package com.begawoinc.smartfuelmeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class About extends AppCompatActivity {

    FirebaseDatabase rootnode;
    DatabaseReference reference,ref;
    TextView aboutProject;
    BottomNavigationView bottomNavigationView;
    Button logoutbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutProject = findViewById(R.id.aboutProject);
        logoutbutton = findViewById(R.id.logoutbutton);

        Home.fromlogin =1;
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.about);

        String phoneNumber = getIntent().getExtras().getString("phoneNo");

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.history:
                        Intent intentHistory = new Intent(About.this, History.class);
                        intentHistory.putExtra("phoneNo", phoneNumber);
                        startActivity(intentHistory);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.about:
                        return true;

                    case R.id.dashboard:
                        Intent intentHome = new Intent(About.this, Home.class);
                        intentHome.putExtra("phoneNo", phoneNumber);
                        startActivity(intentHome);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                }
                return false;
            }
        });

        reference = FirebaseDatabase.getInstance("https://smartfuelmeter-fcc8b-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("aboutProject");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String abouttext =  snapshot.getValue(String.class);
                aboutProject.setText(abouttext);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(About.this, "ERROR::"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("PHONENUMBER").commit();

                Intent intent = new Intent(About.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}