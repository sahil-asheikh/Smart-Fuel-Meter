package com.begawoinc.smartfuelmeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    DatabaseReference reference;
    MyAdapter myAdapter;
    ArrayList<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Home.fromlogin =1;
        String phoneNumber = getIntent().getExtras().getString("phoneNo");

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        recyclerView = findViewById(R.id.recyclerView);

        bottomNavigationView.setSelectedItemId(R.id.history);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.history:
                        return true;
                    case R.id.about:
                        Intent intentAbout = new Intent(History.this, About.class);
                        intentAbout.putExtra("phoneNo", phoneNumber);
                        startActivity(intentAbout);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.dashboard:
                        Intent intentHome = new Intent(History.this, Home.class);
                        intentHome.putExtra("phoneNo", phoneNumber);
                        startActivity(intentHome);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });


       reference = FirebaseDatabase.getInstance("https://smartfuelmeter-fcc8b-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users").child(Home.phoneNumber).child("FuelDetails");
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       list = new ArrayList<>();
       myAdapter = new MyAdapter(this,list);
       recyclerView.setAdapter(myAdapter);


       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               for (DataSnapshot dataSnapshot: snapshot.getChildren()){


                   User user = dataSnapshot.getValue(User.class);
                   list.add(user);

               }
               myAdapter.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });







    }
}