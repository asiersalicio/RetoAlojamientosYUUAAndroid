package com.yuua.alojamientosyuua.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yuua.alojamientosyuua.DatosApp;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Usuario;
import com.yuua.alojamientosyuua.fragmentos.FragmentInicio;
import com.yuua.alojamientosyuua.fragmentos.FragmentReservas;
import com.yuua.alojamientosyuua.fragmentos.FragmentUsuario;

public class Base extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;

    private FragmentInicio fragmentInicio;
    private FragmentReservas fragment_reservas;
    private FragmentUsuario fragment_usuario;
    public static Context contexto;

    public static LinearLayoutManager llm;


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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_base, menu);

        return true;
    }


    public void Inicializar()
    {
        contexto=this;
        bottomNavigationView=findViewById(R.id.bottomnavigationview);

        llm = new LinearLayoutManager(contexto);
        fragmentInicio = new FragmentInicio(contexto);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase,fragmentInicio).commit();
        AnadirListeners();
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



}
