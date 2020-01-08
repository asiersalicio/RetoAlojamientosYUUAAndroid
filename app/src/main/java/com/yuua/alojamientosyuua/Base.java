package com.yuua.alojamientosyuua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
    public RecyclerView rv;
    public ArrayList<Alojamiento> alojamientos;

    private com.yuua.alojamientosyuua.fragmentInicio fragmentInicio;
    private fragment_Reservas fragment_reservas;
    private fragment_Usuario fragment_usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Inicializar();


    }

    public void Inicializar()
    {
        //Establece la pestaña inicio como la activa
        fragmentInicio = new fragmentInicio();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase, fragmentInicio).commit();

        bottomNavigationView=findViewById(R.id.bottomnavigationview);

        rv=fragmentInicio.rv;

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        CargarDatosBD();


        AnadirListeners();
    }

    public void CargarDatosBD()
    {
        alojamientos=new ArrayList<Alojamiento>();
        alojamientos.add(new Alojamiento("Hotel", "Hotel playa", "Un hotel en la playa", 600000000, "Sin web", "Sin email", 100, null));

        RVAdapter adapter = new RVAdapter(alojamientos);
        rv.setAdapter(adapter);
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
                        fragmentInicio = new fragmentInicio();
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
