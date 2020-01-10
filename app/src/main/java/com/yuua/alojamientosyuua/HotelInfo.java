package com.yuua.alojamientosyuua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Localizacion;
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
        inizializar();

    }

    public void inizializar()
    {
        contexto=this;
        alojamiento=(Alojamiento) getIntent().getSerializableExtra("alojamiento");

        nombreHotel=findViewById(R.id.NombreInfoH);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if(alojamiento.getLocalizacion()!=null)
        {
            ft.replace(R.id.map_info_frame_H, new FragmentMap(alojamiento.getLocalizacion(),alojamiento.getNombre(), R.drawable.hotel));
            ft.commitAllowingStateLoss();
        }

        RellenarDatos();
    }



    public void RellenarDatos()
    {
        nombreHotel.setText(alojamiento.getNombre());
    }
}
