package com.yuua.alojamientosyuua.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.adaptadores.ItemSearchResultAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;

import java.util.ArrayList;

public class BuscadorAlojamientos extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_alojamientos);
        mostrarResultados();

        getSupportActionBar().hide();


    }

    public void mostrarResultados()
    {
        rv=findViewById(R.id.recyclerViewBusqueda);
        ArrayList<Alojamiento> alojamientos=new ArrayList<Alojamiento>();
        alojamientos.add(new Alojamiento("Hotel", "Hotel Melia", "Un hotel en bilbao", 600000000, "Sin web", "Sin email", 100, null));
        alojamientos.add(new Alojamiento("Hotel", "Hotel Prueba", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, null));


        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        ItemSearchResultAdapter adapter = new ItemSearchResultAdapter(this, alojamientos);
        rv.setAdapter(adapter);
    }
}
