package com.yuua.alojamientosyuua.activitys;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yuua.alojamientosyuua.ObjetoGenerico;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.Sistema;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Localizacion;
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
    private TextView textoBusqueda;
    private EditText campoFechaEntrada, campoFechaSalida;
    public static LinearLayoutManager llm;
    private Date fechaEntrada, fechaSalida;
    private Object itemSeleccionado;
    private MenuItem btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
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
        getSupportActionBar().setCustomView(R.menu.custom_action_bar);
        getSupportActionBar().setElevation(0);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_base, menu);

        btnCerrarSesion=menu.findItem(R.id.btnCerrarSesionToolbar);
        if(Sistema.user==null)
        {
            btnCerrarSesion.setVisible(false);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnBusquedaToolbar:
                btnBusqueda();
                break;
            case R.id.btnCerrarSesionToolbar:
                cerrarSesion();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
        Sistema.user=null;
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
        finish();
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
        textoBusqueda = findViewById(R.id.buscadorBaseLocAloj);
        campoFechaEntrada = findViewById(R.id.buscadorBaseFEntrada);
        campoFechaSalida = findViewById(R.id.buscadorBaseFSalida);
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
                cerrarBusqueda();
                Fragment selectedFragment = null;
                llm = new LinearLayoutManager(contexto);
                switch (menuItem.getItemId()) {
                    case R.id.bottomnavhome:
                        busquedaPorLocalizacion = false;
                        fragmentInicio = new FragmentInicio(contexto);
                        selectedFragment = fragmentInicio;
                        break;
                    case R.id.bottomnavbookings:
                        if(Sistema.user!=null)
                        {
                            fragment_reservas = new FragmentReservas();
                            selectedFragment = fragment_reservas;
                        }
                        else
                        {
                            fragmentInicio = new FragmentInicio(contexto);
                            selectedFragment = fragmentInicio;
                            Intent intentUsuario=new Intent(contexto, Login.class);
                            contexto.startActivity(intentUsuario);
                        }
                        break;
                    case R.id.bottomnavuser:
                        if(Sistema.user!=null)
                        {
                            fragment_usuario = new FragmentUsuario();
                            selectedFragment = fragment_usuario;

                        }
                        else
                        {
                            fragmentInicio = new FragmentInicio(contexto);
                            selectedFragment = fragmentInicio;
                            Intent intentUsuario=new Intent(contexto, Login.class);
                            contexto.startActivity(intentUsuario);
                        }
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase, selectedFragment).commit();
                return true;
            }
        });

        textoBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBuscador = new Intent(contexto, BuscadorAlojamientos.class);
                intentBuscador.putExtra("busqueda", textoBusqueda.getText().toString());
                startActivityForResult(intentBuscador, 0);
            }
        });

        campoFechaEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFechaEntrada();
            }
        });

        campoFechaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fechaEntrada != null) {
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
                fechaEntrada = startDate;
                String fdate = sd.format(startDate);

                campoFechaEntrada.setText(fdate);

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
                Date fecha = newDate.getTime();
                fechaSalida = fecha;
                String fdate = sd.format(fecha);

                campoFechaSalida.setText(fdate);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            itemSeleccionado = ((ObjetoGenerico) data.getSerializableExtra("item")).getObject();

            if (itemSeleccionado instanceof Alojamiento) {
                Alojamiento aloj = (Alojamiento) itemSeleccionado;
                textoBusqueda.setText(aloj.getNombre());
            } else if (itemSeleccionado instanceof String) {
                String muni = (String) itemSeleccionado;
                textoBusqueda.setText(muni);
            }


        }
    }

    public void establecerDatosUsuario() {
        if (Sistema.user != null) {
            Usuario user = Sistema.user;
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview);
            bottomNavigationView.getMenu().getItem(2).setTitle(user.getNombreUsuario());
            btnCerrarSesion.setVisible(true);
        }
    }

    public void btnBusquedaPulsado(View view) {
        btnBusqueda();
    }

    public void btnBuscarPulsado(View view) {
        if(validarDatosBusqueda()) {
            cerrarBusqueda();
            if(itemSeleccionado instanceof Alojamiento) {
                //TO-DO
            }else if(itemSeleccionado instanceof String) {
                buscarPorLocalizacion();
            }
        }
    }

    private boolean validarDatosBusqueda() {
        boolean valido=true;
        if(itemSeleccionado==null) {
            valido=false;
            textoBusqueda.setError(getString(R.string.theSearchBarMustnBeNull));
        }
        else {
            textoBusqueda.setError(null);
        }
        if(fechaEntrada==null) {
            valido = false;
            campoFechaEntrada.setError(getString(R.string.theCheckInDateCannotBeNull));
        }
        else {
            campoFechaEntrada.setError(null);
        }
        if(fechaSalida==null) {
            valido=false;
            campoFechaSalida.setError(getString(R.string.theCheckOutDateCannotBeNull));
        }
        else {
            campoFechaSalida.setError(null);
        }
        return valido;
    }

    public void buscarPorLocalizacion() {
        busquedaPorLocalizacion = true;
        llm = new LinearLayoutManager(contexto);
        fragmentAlojPorCiudad = new FragmentAlojPorCiudad(contexto, (String) itemSeleccionado, fechaEntrada, fechaSalida);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase, fragmentAlojPorCiudad).commit();
    }

    private void abrirBusqueda() {
        ValueAnimator anim = ValueAnimator.ofInt(toolbar.getMeasuredHeight(), 600);
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

    public void btnBorrarResultado(View view)
    {
        textoBusqueda.setText("");
        itemSeleccionado=null;
    }

    public void btnBorrarFechaEntrada(View view)
    {
        campoFechaEntrada.setText("");
        fechaEntrada=null;
    }

    public void btnBorrarFechaSalida(View view)
    {
        campoFechaSalida.setText("");
        fechaSalida=null;
    }
}
