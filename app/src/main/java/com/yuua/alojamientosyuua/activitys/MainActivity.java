package com.yuua.alojamientosyuua.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.yuua.alojamientosyuua.DatosApp;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatosApp.currentContext=this;
        getSupportActionBar().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(DatosApp.currentContext, Base.class);
                startActivity(intent);
                finish();
            }
        }, 1000);

    }

}
