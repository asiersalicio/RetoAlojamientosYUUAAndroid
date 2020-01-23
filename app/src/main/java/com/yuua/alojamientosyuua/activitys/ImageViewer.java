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

public class ImageViewer extends AppCompatActivity implements Runnable {

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


        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        Thread hilo = new Thread(this);
        hilo.start();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void run() {
        ArrayList<String> imagenes;
        ImageDownloader imageDownloader = new ImageDownloader(searchFor,1);
        imagenes=imageDownloader.obtenerLinksImagenes();
        if(imagenes.size()>0)
        {
            final ItemImageAdapter adapter = new ItemImageAdapter(this, imagenes);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rv.setAdapter(adapter);
                }
            });
        }


    }
}
