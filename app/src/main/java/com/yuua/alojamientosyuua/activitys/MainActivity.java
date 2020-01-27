package com.yuua.alojamientosyuua.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.yuua.alojamientosyuua.R;

public class MainActivity extends AppCompatActivity {

    private Context contexto;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contexto=this;
        getSupportActionBar().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(contexto, Base.class);
                startActivity(intent);
                finish();
            }
        }, 1000);

    }

}
