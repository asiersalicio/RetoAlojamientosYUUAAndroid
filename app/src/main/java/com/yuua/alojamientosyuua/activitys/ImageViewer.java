package com.yuua.alojamientosyuua.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.yuua.alojamientosyuua.ImageDownloader;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.adaptadores.ItemImageAdapter;
import com.yuua.alojamientosyuua.entidades.Imagen;

import java.util.ArrayList;

public class ImageViewer extends AppCompatActivity implements Runnable {

    private RecyclerView rv;
    private String searchFor;
    private ArrayList<Imagen> imagenes;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        context=this;
        getSupportActionBar().hide();

    }

    @Override
    protected void onStart() {
        super.onStart();
        searchFor=getIntent().getExtras().getString("searchFor");
        imagenes=(ArrayList<Imagen>) getIntent().getExtras().getSerializable("listaLinksImagenes");
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
        ImageDownloader imageDownloader;
        if(searchFor!=null)
        {
            imageDownloader = new ImageDownloader(searchFor,5);
            imagenes=imageDownloader.obtenerImagenes();
        }
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
        else
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(getString(R.string.imagesNotAvailable));
                    builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                }
            });
        }


    }
}
