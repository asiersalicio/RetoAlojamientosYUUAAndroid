package com.yuua.alojamientosyuua.activitys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.Sistema;
import com.yuua.alojamientosyuua.adaptadores.ItemCardAlojAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;

import java.util.ArrayList;

public class InfoReservas extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_reservas);


        recyclerView=findViewById(R.id.recyclerViewInfoReservas);


        ArrayList<Alojamiento> alojamientos=new ArrayList<Alojamiento>();

        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);
        ItemCardAlojAdapter adapter = new ItemCardAlojAdapter(this, alojamientos, null, null, false);
        recyclerView.setAdapter(adapter);
    }
}
