package com.yuua.alojamientosyuua.fragmentos;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Api;
import com.yuua.alojamientosyuua.Base;
import com.yuua.alojamientosyuua.net.AsynCliente;
import com.yuua.alojamientosyuua.net.Cliente;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.adaptadores.RVAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Localizacion;
import com.yuua.reto.net.Request;

import java.util.ArrayList;

public class FragmentInicio extends Fragment{

    public View view;
    public ArrayList<Alojamiento> alojamientos;
    public RecyclerView rv;
    private Context contextoBase;
    private AsyncTask clienteAsyncrono;

    public FragmentInicio(Context contextoBase) {
        this.contextoBase=contextoBase;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inicio, container, false);
        rv=view.findViewById(R.id.recyclerViewInicio);
        cargarDatosBD();
        return view;
    }

    public void cargarDatosBD() {
        /*Request peticion = new Request(60, new Object[]{"Alojamiento",new String[]{},new String[]{}});
        Cliente cliente = new Cliente(peticion);
        Thread hiloCliente=new Thread(cliente);
        hiloCliente.start();
        
        cliente.leerJson();*/

        Localizacion loc = new Localizacion();
        loc.setLatitud(43.2673868);
        loc.setLongitud(-2.941894);
        loc.setCodigoPostal("48011");
        loc.setDireccion("Leizaola Lehendakariaren Kalea, 29");


        alojamientos=new ArrayList<Alojamiento>();
        alojamientos.add(new Alojamiento("Hotel", "Hotel Melia", "Un hotel en bilbao", 600000000, "Sin web", "Sin email", 100, loc));
        alojamientos.add(new Alojamiento("Hotel", "Hotel Prueba", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, null));

        rv.setLayoutManager(Base.llm);

        RVAdapter adapter = new RVAdapter(contextoBase, alojamientos);
        rv.setAdapter(adapter);
    }


}
