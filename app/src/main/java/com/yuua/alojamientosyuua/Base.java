package com.yuua.alojamientosyuua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yuua.alojamientosyuua.adaptadores.RVAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;

import java.util.ArrayList;

public class Base extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;

    private FragmentInicio fragmentInicio;
    private fragment_Reservas fragment_reservas;
    private fragment_Usuario fragment_usuario;

    public static LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Inicializar();


    }


    public void Inicializar()
    {

        bottomNavigationView=findViewById(R.id.bottomnavigationview);

        llm = new LinearLayoutManager(this);


        fragmentInicio = new FragmentInicio();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase,fragmentInicio).commit();
        AnadirListeners();
    }


    public void AnadirListeners()
    {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch(menuItem.getItemId())
                {
                    case R.id.bottomnavhome:
                        fragmentInicio = new FragmentInicio();
                        selectedFragment = fragmentInicio;
                    break;
                    case R.id.bottomnavbookings:
                        fragment_reservas = new fragment_Reservas();
                        selectedFragment = fragment_reservas;
                        break;
                    case R.id.bottomnavuser:
                        fragment_usuario = new fragment_Usuario();
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
