package com.begawoinc.smartfuelmeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button loginButton;
    Button gotoSignin;
    EditText phoneNum,passWord;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginbutton);
        gotoSignin = findViewById(R.id.backtosignin);
        phoneNum = findViewById(R.id.phonenumber);
        passWord = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser();
            }
        });

        gotoSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Signin.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void isUser() {
        String userEnteredPhone = phoneNum.getText().toString().trim();
        String userEnteredPassword = passWord.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://smartfuelmeter-fcc8b-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        Query checkuser = reference.orderByChild("phoneNo").equalTo(userEnteredPhone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phoneNum.setError(null);
                    String passfromdatabase = snapshot.child(userEnteredPhone).child("password").getValue(String.class);
                    if (passfromdatabase.equals(userEnteredPassword)){

                        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("PHONENUMBER", userEnteredPhone);
                        editor.commit();

                        phoneNum.setError(null);

                        Intent intent = new Intent(Login.this, Home.class);
                        intent.putExtra("phoneNo", userEnteredPhone);
                        startActivity(intent);
                        finish();
                    } else {
                        passWord.setError("Wrong Password");
                        passWord.requestFocus();
                    }
                } else {
                    phoneNum.setError("No Such User Exists");
                    phoneNum.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "ERROR::"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}