package com.yuua.alojamientosyuua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        getSupportActionBar().hide();
        Cliente clientetest=new Cliente(this);
        Thread hilotest=new Thread(clientetest);
        hilotest.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(context, Base.class);
                startActivity(intent);
                finish();
            }
        }, 3000);


    }
}
