package com.yuua.alojamientosyuua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cliente clientetest=new Cliente(this);
        Thread hilotest=new Thread(clientetest);
        hilotest.start();
        Intent intent = new Intent(this, Base.class);
        startActivity(intent);


        finish();
    }
}
