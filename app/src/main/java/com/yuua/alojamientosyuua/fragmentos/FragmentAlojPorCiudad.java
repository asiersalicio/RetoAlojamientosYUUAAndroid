package com.yuua.alojamientosyuua.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yuua.alojamientosyuua.DatosApp;
import com.yuua.alojamientosyuua.ImageDownloader;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.activitys.Base;
import com.yuua.alojamientosyuua.adaptadores.ItemCardAlojAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Imagen;
import com.yuua.alojamientosyuua.entidades.Municipio;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.util.ArrayList;
import java.util.Date;

public class FragmentAlojPorCiudad extends Fragment implements Runnable{

    private View view;
    private ArrayList<Alojamiento> alojamientos;
    private RecyclerView rv;
    private TextView nombreCiudad;
    private Context contextoPadre;
    private ImageView imagenCiudad;
    private Municipio municipio;

    public FragmentAlojPorCiudad(Context context) {
        this.contextoPadre=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aloj_por_ciudad, container, false);
        view.findViewById(R.id.recyclerViewAlojPorLoc);
        rv=view.findViewById(R.id.recyclerViewAlojPorLoc);
        nombreCiudad=view.findViewById(R.id.CiudadAlojPorLoc);
        imagenCiudad=view.findViewById(R.id.ImagenAlojPorLoc);
        alojamientos=new ArrayList<Alojamiento>();

        municipio = (Municipio) DatosApp.itemSeleccionado;

        if(!DatosApp.DATOSDEBUG)
        {
            nombreCiudad.setText(municipio.getNombre());

            Consultas consultar=new Consultas();
            Request consulta=consultar.alojamientosDisponiblesEntreFechasEnMunicipio(new Date(),new Date(),municipio);
            Object resultado=consultar.devolverResultadoPeticion(consulta,Alojamiento.class);
            alojamientos= (ArrayList<Alojamiento>) resultado;

        }
        else
        {
            nombreCiudad.setText(municipio.getNombre());
            alojamientos=DatosApp.getDebugAlojamientos();

        }

        Thread descargarImagen = new Thread(this);
        descargarImagen.start();

        rv.setLayoutManager(Base.llm);
        ItemCardAlojAdapter adapter = new ItemCardAlojAdapter(contextoPadre, alojamientos);
        rv.setAdapter(adapter);


        return view;
    }

    @Override
    public void run() {
        final ArrayList<Imagen> imagenes;
        ImageDownloader imageDownloader = new ImageDownloader(municipio.getNombre(),1);
        imagenes= imageDownloader.obtenerImagenes();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    Picasso.get().load(imagenes.get(0).media).into(imagenCiudad);
                }catch (IllegalArgumentException ex){}
            }
        });
    }
}
