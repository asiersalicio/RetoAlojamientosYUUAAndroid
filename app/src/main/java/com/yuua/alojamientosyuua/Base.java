package com.yuua.alojamientosyuua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Base extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Inicializar();


    }

    public void Inicializar()
    {

        bottomNavigationView=findViewById(R.id.bottomnavigationview);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutbase,new fragment_Inicio()).commit();
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
                    selectedFragment = new fragment_Inicio();
                    break;
                    case R.id.bottomnavbookings:
                        selectedFragment = new fragment_Reservas();
                        break;
                    case R.id.bottomnavuser:
                    selectedFragment = new fragment_Usuario();
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
