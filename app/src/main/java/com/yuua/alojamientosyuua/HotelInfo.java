package com.yuua.alojamientosyuua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.fragmentos.FragmentMap;

public class HotelInfo extends AppCompatActivity {

    private Alojamiento alojamiento;
    public TextView nombreHotel;
    public Context contexto;
    public FragmentMap fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);
        Inizialiar();

    }

    public void Inizialiar()
    {
        contexto=this;
        alojamiento=(Alojamiento) getIntent().getSerializableExtra("alojamiento");

        nombreHotel=findViewById(R.id.NombreInfoH);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(R.id.map_info_frame_H, new FragmentMap(), "map");
        ft.commitAllowingStateLoss();
        RellenarDatos();
    }



    public void RellenarDatos()
    {
        nombreHotel.setText(alojamiento.getNombre());
    }
}
