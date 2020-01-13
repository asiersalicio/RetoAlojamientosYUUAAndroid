package com.yuua.alojamientosyuua.fragmentos;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.Base;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.adaptadores.RVAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.net.Consultas;
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
        /**
        Request peticion = new Request(60, new Object[]{"Alojamiento",new String[]{},new String[]{}});
        Cliente cliente = new Cliente(peticion);
        Thread hiloCliente=new Thread(cliente);
        hiloCliente.start();

        Gson gson=new Gson();
        List<Alojamiento> list = gson.fromJson(cliente.leerJson(), new TypeToken<List<Alojamiento>>() {}.getType());

         **/

        Consultas consultar=new Consultas();
        Request consulta=consultar.prepararQueryHibernate(60,Alojamiento.class,new String[]{},new String[]{});
        Object resultado=consultar.devolverResultadoPeticion(consulta,Alojamiento.class);


        rv.setLayoutManager(Base.llm);

        RVAdapter adapter = new RVAdapter(contextoBase, alojamientos);
        rv.setAdapter(adapter);
    }


}
