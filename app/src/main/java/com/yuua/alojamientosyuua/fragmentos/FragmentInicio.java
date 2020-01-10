package com.yuua.alojamientosyuua.fragmentos;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuua.alojamientosyuua.Base;
import com.yuua.alojamientosyuua.Cliente;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.adaptadores.RVAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.reto.net.Request;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInicio extends Fragment{

    public View view;
    public ArrayList<Alojamiento> alojamientos;
    public RecyclerView rv;
    private Context contextoBase;

    public FragmentInicio(Context contextoBase) {
        this.contextoBase=contextoBase;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inicio, container, false);
        rv=view.findViewById(R.id.recyclerViewInicio);
        CargarDatosBD();
        return view;
    }


    public void CargarDatosBD()
    {
        /*Cliente clientetest=new Cliente(null);
        Thread hilotest=new Thread(clientetest);
        hilotest.start();

        Request peticion = new Request(60, new Object[]{"Alojamiento",new String[]{},new String[]{}});
        clientetest.mandarRequest(peticion);
        while (clientetest.resultadoPeticion==null){

        }
        Object resultado=clientetest.resultadoPeticion;*/

        alojamientos=new ArrayList<Alojamiento>();
        alojamientos.add(new Alojamiento("Hotel", "Hotel playa", "Un hotel en la playa", 600000000, "Sin web", "Sin email", 100, null));
        alojamientos.add(new Alojamiento("Hotel", "Hotel Prueba", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, null));

        rv.setLayoutManager(Base.llm);

        RVAdapter adapter = new RVAdapter(contextoBase, alojamientos);
        rv.setAdapter(adapter);
    }


}
