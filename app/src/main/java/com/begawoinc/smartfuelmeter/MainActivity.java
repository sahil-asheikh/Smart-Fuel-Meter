package com.begawoinc.smartfuelmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                boolean isEmailEmpty = sharedPreferences.getString("PHONENUMBER", "").isEmpty();

                if (!isEmailEmpty){
                    String phoneNum = sharedPreferences.getString("PHONENUMBER", "");
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("phoneNo", phoneNum);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, Login.class );
                    startActivity(intent);
                    finish();
                }
            }
        },1500);
    }
}