package com.yuua.alojamientosyuua.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yuua.alojamientosyuua.Sistema;
import com.yuua.alojamientosyuua.ImageDownloader;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.activitys.Base;
import com.yuua.alojamientosyuua.adaptadores.ItemCardAlojAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.ImagenOnline;
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
    private Date fechaEntrada, fechaSalida;

    public FragmentAlojPorCiudad(Context context, Municipio municipio, Date fechaEntrada, Date fechaSalida) {
        this.municipio=municipio;
        this.contextoPadre=context;
        this.fechaEntrada=fechaEntrada;
        this.fechaSalida=fechaSalida;
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


        if(!Sistema.SIMULACIONALOJAMIENTOS)
        {
            nombreCiudad.setText(municipio.getNombre());

            Consultas consultar=new Consultas();
            Request consulta=consultar.alojamientosDisponiblesEntreFechasEnMunicipio(municipio,fechaEntrada,fechaSalida);
            Object resultado=consultar.devolverResultadoPeticion(consulta,Alojamiento.class);
            alojamientos= (ArrayList<Alojamiento>) resultado;

        }
        else
        {
            nombreCiudad.setText(municipio.getNombre());
            alojamientos= Sistema.getDebugAlojamientos();

        }

        Thread descargarImagen = new Thread(this);
        descargarImagen.start();

        rv.setLayoutManager(Base.llm);
        ItemCardAlojAdapter adapter = new ItemCardAlojAdapter(contextoPadre, alojamientos, fechaEntrada, fechaSalida,true);
        rv.setAdapter(adapter);


        return view;
    }

    @Override
    public void run() {
        final ArrayList<ImagenOnline> imagenes;
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
