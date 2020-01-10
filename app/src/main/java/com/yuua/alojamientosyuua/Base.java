package com.yuua.alojamientosyuua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    public void cambiar(View view)
    {

    }


}
