package com.begawoinc.smartfuelmeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Home extends AppCompatActivity {

    FirebaseDatabase rootnode;
    DatabaseReference reference,ref;
    TextView petrolText, todaysfuelPrice , petrolLitre;
    Double petrol, totalfuel, todaysprice;
    public  static String phoneNumber;
    String str, date;
    int counter, INCcounter;

    BottomNavigationView bottomNavigationView;

    public  static  int fromlogin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        phoneNumber = getIntent().getExtras().getString("phoneNo");
        todaysfuelPrice = findViewById(R.id.todaysfuelprice);
        petrolText = findViewById(R.id.fuelfilledprice);
        petrolLitre = findViewById(R.id.petrolLitre);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.history:
                        Intent intentHistory = new Intent(Home.this, History.class);
                        intentHistory.putExtra("phoneNo", phoneNumber);
                        startActivity(intentHistory);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.about:
                        Intent intentAbout = new Intent(Home.this, About.class);
                        intentAbout.putExtra("phoneNo", phoneNumber);
                        startActivity(intentAbout);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.dashboard:
                        return true;
                }
                return false;
            }
        });

        reference = FirebaseDatabase.getInstance("https://smartfuelmeter-fcc8b-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("TodaysPetrolPrice").child("ngp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                todaysprice =  snapshot.getValue(Double.class);
                String todayPriceString = String.format("%.2f", todaysprice);
                todaysfuelPrice.setText(todayPriceString+" ₹");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "ERROR::"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ref =  FirebaseDatabase.getInstance("https://smartfuelmeter-fcc8b-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users").child(phoneNumber).child("counter");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                counter = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "ERROR::"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        reference = FirebaseDatabase.getInstance("https://smartfuelmeter-fcc8b-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users").child(phoneNumber).child("petrolFilled");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                petrol = snapshot.getValue(Double.class);
                String petrolString = String.format("%.2f", petrol);
                petrolLitre.setText(petrolString + " L");
                totalfuel = todaysprice * petrol;
//                str = String.valueOf(totalfuel);
                str = String.format("%.2f", totalfuel);

                date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//                reference = FirebaseDatabase.getInstance("https://smartfuelmeter-fcc8b-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users").child(phoneNumber).child("FuelDetails").child("FuelData"+counter);
//
//                reference.child("price").setValue(str);
//                reference.child("date").setValue(date);
//                reference.child("litre").setValue(String.valueOf(petrol));
//
//                INCcounter = counter+1;
//
//                ref.setValue(INCcounter);
                petrolText.setText(str+" ₹");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "ERROR::"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}