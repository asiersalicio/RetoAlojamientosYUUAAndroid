package com.yuua.alojamientosyuua.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        mostrarResultados();

        getSupportActionBar().hide();


    }

    public void mostrarResultados()
    {
        rv=findViewById(R.id.recycler_vierw_image);
        ArrayList<String> imagenes = new ArrayList<String>();

        imagenes=ImageDownloader.obtenerLinksImagenes("https://api.qwant.com/api/search/images?count=1&offset=0&q=hotel bilbao&t=web&uiv=1");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        ItemImageAdapter adapter = new ItemImageAdapter(this, imagenes);
        rv.setAdapter(adapter);
    }
}
