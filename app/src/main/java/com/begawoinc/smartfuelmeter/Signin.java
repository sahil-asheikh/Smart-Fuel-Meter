package com.begawoinc.smartfuelmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signin extends AppCompatActivity {

    Button signinButton;
    Button gotoLogin;
    EditText userName,eMail,phoneNumber,passWord;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signinButton = findViewById(R.id.signinbutton);
        gotoLogin = findViewById(R.id.backtologin);
        userName = findViewById(R.id.username);
        eMail = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneno);
        passWord = findViewById(R.id.password);


        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance("https://smartfuelmeter-fcc8b-default-rtdb.asia-southeast1.firebasedatabase.app");
                reference = rootNode.getReference("Users");

                String name = userName.getText().toString().trim();
                String email = eMail.getText().toString().trim();
                String phone = phoneNumber.getText().toString().trim();
                String password = passWord.getText().toString().trim();
                double petrolFilled = 1.0;
                int counter = 0;

                UserHelperClass helperClass = new UserHelperClass(name,email,phone,password,petrolFilled,counter);

                reference.child(phone).setValue(helperClass);

                Toast.makeText(Signin.this, "Please Login to continue", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Signin.this, Login.class);
                intent.putExtra("phoneNo", phone);
                startActivity(intent);
                finish();

            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}