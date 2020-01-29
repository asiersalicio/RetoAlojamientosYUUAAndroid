package com.yuua.alojamientosyuua.activitys;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;
import com.yuua.alojamientosyuua.ImageDownloader;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.Sistema;
import com.yuua.alojamientosyuua.adaptadores.ItemImageAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.ImagenOnline;
import com.yuua.alojamientosyuua.entidades.Reserva;
import com.yuua.alojamientosyuua.fragmentos.FragmentMap;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HotelInfo extends AppCompatActivity implements Runnable{

    private Alojamiento alojamiento;
    public TextView nombreHotel;
    public Context contexto;
    public FragmentMap fragmentMap;
    public TextView descHotel;
    private Button btnReservar;
    private ImageView imagen;
    private ImageDownloader imageDownloader;
    private ArrayList<ImagenOnline> imagenes;
    private Date fechaEntrada, fechaSalida;
    private TextView telefono, email, localizacion;
    private boolean mostrarBoton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);
        getSupportActionBar().setTitle(getString(R.string.accomodationInfo));
        inicializar();

    }

    public void inicializar() {
        contexto = this;
        btnReservar = findViewById(R.id.btnReservarInfoHotel);
        imagen=findViewById(R.id.ImagenInfoH);
        alojamiento = (Alojamiento) getIntent().getSerializableExtra("alojamiento");
        fechaEntrada = (Date) getIntent().getSerializableExtra("fechaEntrada");
        fechaSalida = (Date) getIntent().getSerializableExtra("fechaSalida");


        if (fechaEntrada == null || fechaSalida == null) {
            btnReservar.setText("Comprobar disponibilidad");
        } else {

            btnReservar.setText("Reservar para: " + fechaEntrada.toLocaleString() + " " + fechaSalida.toLocaleString());
        }
        mostrarBoton=getIntent().getBooleanExtra("mostrarBoton", true);

        nombreHotel = findViewById(R.id.NombreInfoH);
        descHotel = findViewById(R.id.descInfoH);
        telefono = findViewById(R.id.telefonoInfoH);
        email = findViewById(R.id.correoInfoH);
        localizacion = findViewById(R.id.localizacionInfoH);

        if(!mostrarBoton)
        {
            ConstraintLayout constraintLayout=findViewById(R.id.constraintBtnReservarAhora);
            constraintLayout.getLayoutParams().height=0;
        }

        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReservarPulsado();
            }
        });

        RellenarDatos();
    }

    @Override
    public void run() {
        imageDownloader = new ImageDownloader(alojamiento.getNombre(),5);
        imagenes=imageDownloader.obtenerImagenes();
        if(imagenes.size()>0 & imagenes!=null)
        {
            final ItemImageAdapter adapter = new ItemImageAdapter(this, imagenes);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Picasso.get().load(imagenes.get(0).media).into(imagen);
                }
            });
        }
    }

    public void RellenarDatos() {
        nombreHotel.setText(alojamiento.getNombre());
        descHotel.setText(alojamiento.getDescripcion());
        if(alojamiento.getTelefono()>0)
        {
            telefono.setText(Integer.toString(alojamiento.getTelefono()));
        }
        else
        {
            telefono.setText(getString(R.string.phoneNotAvailable));
        }
        if(alojamiento.getEmail()!=null)
        {
            email.setText(alojamiento.getEmail());
        }
        else
        {
            email.setText(getString(R.string.emailNotAvailable));
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (alojamiento.getLocalizacion() != null) {
            ft.replace(R.id.map_info_frame_H, new FragmentMap(alojamiento.getLocalizacion(), alojamiento.getNombre(), R.drawable.hotel));
            ft.commitAllowingStateLoss();
            localizacion.setText(alojamiento.getLocalizacion().getDireccion() + ", " + alojamiento.getLocalizacion().getCodigoPostal() + ", " + alojamiento.getLocalizacion().getTmunicipio());
        }
        else {
            localizacion.setText(getString(R.string.locationNotAvailable));
        }




        Thread hiloDescargarImagen = new Thread(this);
        hiloDescargarImagen.start();

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
                fechaEntrada = startDate;
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
                fechaSalida = startDate;
                String fdate = sd.format(startDate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        Calendar max6months = Calendar.getInstance();
        max6months.setTime(new Date());
        max6months.add(Calendar.MONTH, 6);
        Date datemax6months = max6months.getTime();

        datePickerDialog.getDatePicker().setMinDate(fechaEntrada.getTime());
        datePickerDialog.getDatePicker().setMaxDate(datemax6months.getTime());
        datePickerDialog.show();

    }

    public void btnGaleriaImagenes(View view)
    {
        Intent galeria = new Intent(this, ImageViewer.class);
        galeria.putExtra("listaLinksImagenes", imagenes);
        startActivity(galeria);
    }

    private void reservarPara(Alojamiento alojamiento, Date fechaEntrada, Date fechaSalida)
    {
        if(Sistema.user==null)
        {
            Intent intentLogin = new Intent(this, Login.class);
            startActivity(intentLogin);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void btnReservarPulsado()
    {
        if(Sistema.user!=null)
        {
            Reserva res = new Reserva(-1, fechaEntrada, fechaSalida, alojamiento, Sistema.user);
            Consultas consultar = new Consultas();
            Request consulta = consultar.prepararInsertHibernate(Reserva.class,new Object[]{res});
            consultar.devolverResultadoPeticionBoolean(consulta);
            Toast.makeText(this, getString(R.string.theAccommodationHasBeenBooked), Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intentlogin = new Intent(this,Login.class);
            startActivity(intentlogin);
            if(Sistema.user!=null){btnReservarPulsado();}
        }
    }

    public void btnAbrirTelefono(View view)
    {
        if(alojamiento.getTelefono()>0)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + alojamiento.getTelefono()));
            startActivity(intent);
        }
    }

    public void btnAbrirEmail(View view)
    {
        if(alojamiento.getEmail()!=null)
        {
            Intent emailIntent;
            emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + alojamiento.getEmail()));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.queryAbout) + ": " + alojamiento.getNombre());
            emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.emailIntro));
            startActivity(emailIntent);
        }

    }
}
