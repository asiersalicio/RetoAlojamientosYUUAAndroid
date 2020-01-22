package com.yuua.alojamientosyuua.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.yuua.alojamientosyuua.ImageDownloader;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.adaptadores.ItemImageAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Imagen;
import com.yuua.alojamientosyuua.entidades.Municipio;

import java.util.ArrayList;

public class ImageViewer extends AppCompatActivity {

    private RecyclerView rv;
    private String searchFor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        getSupportActionBar().hide();

    }

    @Override
    protected void onStart() {
        super.onStart();
        searchFor=getIntent().getExtras().getString("searchFor");
        mostrarResultados();
    }

    public void mostrarResultados()
    {
        rv=findViewById(R.id.recycler_vierw_image);
        ArrayList<String> imagenes;

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        imagenes=ImageDownloader.obtenerLinksImagenes(searchFor,1);
        ItemImageAdapter adapter = new ItemImageAdapter(this, imagenes);
        rv.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
