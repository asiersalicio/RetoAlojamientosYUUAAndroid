package com.yuua.alojamientosyuua.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.yuua.alojamientosyuua.DatosApp;
import com.yuua.alojamientosyuua.ImageDownloader;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Localizacion;
import com.yuua.alojamientosyuua.entidades.Reserva;
import com.yuua.alojamientosyuua.fragmentos.FragmentMap;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HotelInfo extends AppCompatActivity {

    private Alojamiento alojamiento;
    public TextView nombreHotel;
    public Context contexto;
    public FragmentMap fragmentMap;
    public TextView descHotel;
    private Button btnReservar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);
        DatosApp.currentContext = this;
        inizializar();

    }

    public void inizializar() {
        contexto = this;
        btnReservar = findViewById(R.id.btnReservarInfoHotel);

        alojamiento = (Alojamiento) getIntent().getSerializableExtra("alojamiento");


        if (DatosApp.fechaEntrada == null || DatosApp.fechaSalida == null) {
            btnReservar.setText("Comprobar disponibilidad");
        } else {

            btnReservar.setText("Reservar para: " + DatosApp.fechaEntrada.toLocaleString() + " " + DatosApp.fechaSalida.toLocaleString());
        }

        nombreHotel = findViewById(R.id.NombreInfoH);
        descHotel = findViewById(R.id.descInfoH);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();


        if (alojamiento.getLocalizacion() != null) {
            ft.replace(R.id.map_info_frame_H, new FragmentMap(alojamiento.getLocalizacion(), alojamiento.getNombre(), R.drawable.hotel));
            ft.commitAllowingStateLoss();
        }

        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reserva res = new Reserva(-1, new Date(), new Date(), alojamiento, null);
                Consultas consultar = new Consultas();
                Request consulta = consultar.prepararInsertHibernate(Reserva.class,new Object[]{res});
                consultar.devolverResultadoPeticionBoolean(consulta);
            }
        });

        RellenarDatos();
    }


    public void RellenarDatos() {
        nombreHotel.setText(alojamiento.getNombre());
        descHotel.setText(alojamiento.getDescripcion());
    }


    private void setFechaEntrada() {

        DatePickerDialog datePickerDialog;
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                Date startDate = newDate.getTime();
                DatosApp.fechaEntrada = startDate;
                String fdate = sd.format(startDate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        Calendar max6months = Calendar.getInstance();
        max6months.setTime(new Date());
        max6months.add(Calendar.MONTH, 6);
        Date datemax6months = max6months.getTime();

        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.getDatePicker().setMaxDate(datemax6months.getTime());
        datePickerDialog.show();

    }

    private void setFechaSalida() {

        DatePickerDialog datePickerDialog;
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                Date startDate = newDate.getTime();
                DatosApp.fechaSalida = startDate;
                String fdate = sd.format(startDate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        Calendar max6months = Calendar.getInstance();
        max6months.setTime(new Date());
        max6months.add(Calendar.MONTH, 6);
        Date datemax6months = max6months.getTime();

        datePickerDialog.getDatePicker().setMinDate(DatosApp.fechaEntrada.getTime());
        datePickerDialog.getDatePicker().setMaxDate(datemax6months.getTime());
        datePickerDialog.show();

    }

    public void btnGaleriaImagenes(View view)
    {
        Intent galeria = new Intent(this, ImageViewer.class);
        galeria.putExtra("aloj", alojamiento);
        startActivity(galeria);
    }
}
