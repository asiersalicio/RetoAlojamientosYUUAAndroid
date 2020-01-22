package com.yuua.alojamientosyuua.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yuua.alojamientosyuua.DatosApp;
import com.yuua.alojamientosyuua.ObjetoGenerico;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Municipio;
import com.yuua.alojamientosyuua.entidades.Usuario;
import com.yuua.alojamientosyuua.fragmentos.FragmentAlojPorCiudad;
import com.yuua.alojamientosyuua.fragmentos.FragmentInicio;
import com.yuua.alojamientosyuua.fragmentos.FragmentReservas;
import com.yuua.alojamientosyuua.fragmentos.FragmentUsuario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Base extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    private boolean busquedaAbierta = false;
    private boolean busquedaPorLocalizacion = false;
    private FragmentInicio fragmentInicio;
    private FragmentAlojPorCiudad fragmentAlojPorCiudad;
    private FragmentReservas fragment_reservas;
    private FragmentUsuario fragment_usuario;
    public static Context contexto;
    private ConstraintLayout toolbar;
    private EditText buscador;
    private EditText fechaEntrada, fechaSalida;
    public static LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        DatosApp.currentContext = this;
        Inicializar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        establecerDatosUsuario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setElevation(0);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnBusquedaToolbar:
                btnBusqueda();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (busquedaAbierta)
            cerrarBusqueda();
        else if (busquedaPorLocalizacion)
            mostrarFragmentInicio();
        else
            finish();
    }

    public void Inicializar() {
        contexto = this;
        toolbar = findViewById(R.id.searchbar);
        toolbar.getLayoutParams().height = 1;
        buscador = findViewById(R.id.buscadorBaseLocAloj);
        fechaEntrada = findViewById(R.id.buscadorBaseFEntrada);
        fechaSalida = findViewById(R.id.buscadorBaseFSalida);
        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        mostrarFragmentInicio();
        AnadirListeners();
    }

    public void mostrarFragmentInicio() {
        busquedaPorLocalizacion = false;
        llm = new LinearLayoutManager(contexto);
        fragmentInicio = new FragmentInicio(contexto);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase, fragmentInicio).commit();
    }

    public void AnadirListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                llm = new LinearLayoutManager(contexto);
                switch (menuItem.getItemId()) {
                    case R.id.bottomnavhome:
                        busquedaPorLocalizacion = false;
                        fragmentInicio = new FragmentInicio(contexto);
                        selectedFragment = fragmentInicio;
                        break;
                    case R.id.bottomnavbookings:
                        fragment_reservas = new FragmentReservas();
                        selectedFragment = fragment_reservas;
                        break;
                    case R.id.bottomnavuser:
                        fragment_usuario = new FragmentUsuario();
                        selectedFragment = fragment_usuario;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase, selectedFragment).commit();
                return true;
            }
        });

        buscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buscador = new Intent(contexto, BuscadorAlojamientos.class);
                startActivityForResult(buscador, 0);
            }
        });

        fechaEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFechaEntrada();
            }
        });

        fechaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DatosApp.fechaEntrada != null) {
                    setFechaSalida();
                }
            }
        });


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

                fechaEntrada.setText(fdate);

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

                fechaSalida.setText(fdate);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            DatosApp.itemSeleccionado = ((ObjetoGenerico) data.getSerializableExtra("item")).getObject();

            if (DatosApp.itemSeleccionado instanceof Alojamiento) {
                Alojamiento aloj = (Alojamiento) DatosApp.itemSeleccionado;
                buscador.setText("\uD83D\uDECF: " +aloj.getNombre());
            } else if (DatosApp.itemSeleccionado instanceof Municipio) {
                Municipio muni = (Municipio) DatosApp.itemSeleccionado;
                buscador.setText("\uD83C\uDF06: " + muni.getNombre());
            }


        }
    }

    public void establecerDatosUsuario() {
        if (DatosApp.user != null) {
            Usuario user = DatosApp.user;
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview);
            bottomNavigationView.getMenu().getItem(2).setTitle(user.getNombreUsuario());
        }
    }

    public void btnBusquedaPulsado(View view) {
        btnBusqueda();
    }

    public void btnBuscarPulsado(View view) {
        cerrarBusqueda();

        if(DatosApp.itemSeleccionado instanceof Alojamiento)
        {

        }else if(DatosApp.itemSeleccionado instanceof Municipio)
        {
            buscarPorLocalizacion();
        }


    }

    public void buscarPorLocalizacion() {
        busquedaPorLocalizacion = true;
        llm = new LinearLayoutManager(contexto);
        fragmentAlojPorCiudad = new FragmentAlojPorCiudad(contexto);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase, fragmentAlojPorCiudad).commit();
    }

    private void abrirBusqueda() {
        Log.println(Log.INFO, "Toolbar", "Barra de busqueda pulsada");
        ValueAnimator anim = ValueAnimator.ofInt(toolbar.getMeasuredHeight(), 1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = val;
                toolbar.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(500);
        anim.start();
        findViewById(R.id.framelayoutbase).setForeground(new ColorDrawable(ContextCompat.getColor(this, R.color.searching)));
        busquedaAbierta = true;
    }

    private void cerrarBusqueda() {
        Log.println(Log.INFO, "Toolbar", "Barra de busqueda pulsada");
        ValueAnimator anim = ValueAnimator.ofInt(toolbar.getMeasuredHeight(), 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = val;
                toolbar.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(500);
        anim.start();
        findViewById(R.id.framelayoutbase).setForeground(null);
        busquedaAbierta = false;
    }

    private void btnBusqueda() {
        if (busquedaAbierta) {
            cerrarBusqueda();
        } else {
            abrirBusqueda();
        }
    }
}
