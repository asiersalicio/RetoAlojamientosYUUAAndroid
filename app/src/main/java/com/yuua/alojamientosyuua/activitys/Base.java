package com.yuua.alojamientosyuua.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yuua.alojamientosyuua.DatosApp;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Usuario;
import com.yuua.alojamientosyuua.fragmentos.FragmentAlojPorCiudad;
import com.yuua.alojamientosyuua.fragmentos.FragmentInicio;
import com.yuua.alojamientosyuua.fragmentos.FragmentReservas;
import com.yuua.alojamientosyuua.fragmentos.FragmentUsuario;

public class Base extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    private boolean busquedaAbierta=false;
    private boolean busquedaPorLocalizacion=false;
    private FragmentInicio fragmentInicio;
    private FragmentAlojPorCiudad fragmentAlojPorCiudad;
    private FragmentReservas fragment_reservas;
    private FragmentUsuario fragment_usuario;
    public static Context contexto;
    private ConstraintLayout toolbar;
    private EditText buscador;

    public static LinearLayoutManager llm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        DatosApp.currentContext=this;
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
        if(busquedaAbierta)
            cerrarBusqueda();
        else
            if(busquedaPorLocalizacion)
                mostrarFragmentInicio();
            else
                finish();

    }



    public void Inicializar()
    {
        contexto=this;
        toolbar=findViewById(R.id.searchbar);
        toolbar.getLayoutParams().height=1;
        buscador=findViewById(R.id.buscadorBaseLocAloj);
        bottomNavigationView=findViewById(R.id.bottomnavigationview);
        mostrarFragmentInicio();
        AnadirListeners();
    }

    public void mostrarFragmentInicio()
    {
        busquedaPorLocalizacion=false;
        llm = new LinearLayoutManager(contexto);
        fragmentInicio = new FragmentInicio(contexto);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase,fragmentInicio).commit();
    }

    public void AnadirListeners()
    {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                llm = new LinearLayoutManager(contexto);
                switch(menuItem.getItemId())
                {
                    case R.id.bottomnavhome:
                        busquedaPorLocalizacion=false;
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
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase,selectedFragment).commit();
                return true;
            }
        });

        buscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buscador = new Intent(contexto,BuscadorAlojamientos.class);
                startActivityForResult(buscador,0);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==0){
            Object item = data.getStringExtra("item");
            buscador.setText("FUNCIONA");
        }
    }

    public void establecerDatosUsuario()
    {
        if(DatosApp.user!=null)
        {
            Usuario user = DatosApp.user;
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview);
            bottomNavigationView.getMenu().getItem(2).setTitle(user.getNombreUsuario());
        }
    }

    public void btnBusquedaPulsado(View view)
    {
        btnBusqueda();
    }

    public void btnBuscarPulsado(View view)
    {
        cerrarBusqueda();

        buscarPorLocalizacion();
    }

    public void buscarPorLocalizacion()
    {
        busquedaPorLocalizacion=true;
        llm = new LinearLayoutManager(contexto);
        fragmentAlojPorCiudad = new FragmentAlojPorCiudad(contexto);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase,fragmentAlojPorCiudad).commit();
    }

    private void abrirBusqueda()
    {
        Log.println(Log.INFO,"Toolbar","Barra de busqueda pulsada");
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
        busquedaAbierta=true;
    }

    private void cerrarBusqueda()
    {
        Log.println(Log.INFO,"Toolbar","Barra de busqueda pulsada");
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
        busquedaAbierta=false;
    }

    private void btnBusqueda() {
        if(busquedaAbierta)
        {
            cerrarBusqueda();
        }
        else
        {
            abrirBusqueda();
        }
    }





}
