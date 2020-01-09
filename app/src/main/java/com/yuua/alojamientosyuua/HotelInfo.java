package com.yuua.alojamientosyuua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.yuua.alojamientosyuua.entidades.Alojamiento;

import org.w3c.dom.Text;

public class HotelInfo extends AppCompatActivity {

    private Alojamiento alojamiento;
    public TextView nombreHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);
        Inizialiar();

    }

    public void Inizialiar()
    {
        alojamiento=(Alojamiento) getIntent().getSerializableExtra("alojamiento");

        nombreHotel=findViewById(R.id.cardNombreInfoH);
        nombreHotel.setText(alojamiento.getNombre());
    }
}
