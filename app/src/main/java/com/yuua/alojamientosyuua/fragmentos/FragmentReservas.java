package com.yuua.alojamientosyuua.fragmentos;


import android.app.DownloadManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.Sistema;
import com.yuua.alojamientosyuua.activitys.Base;
import com.yuua.alojamientosyuua.adaptadores.ItemCardAlojAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Municipio;
import com.yuua.alojamientosyuua.entidades.Usuario;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.util.ArrayList;


public class FragmentReservas extends Fragment {

    private RecyclerView recyclerView;


    public FragmentReservas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inicializar();
        return inflater.inflate(R.layout.fragment_reservas, container, false);
    }

    private void inicializar() {

        recyclerView=getView().findViewById(R.id.recyclerViewInfoReservas);

        Consultas consultas = new Consultas();
        Request peticionMunicipio = consultas.prepararQueryHibernate(Consultas.QUERY_CON_CONDICIONES, Usuario.class, new String[]{"idDni"}, new String[]{Sistema.user.getIdDni()});

        ArrayList<Alojamiento> alojamientos=new ArrayList<Alojamiento>();

        alojamientos=(ArrayList<Alojamiento>) consultas.devolverResultadoPeticion(peticionMunicipio, Alojamiento.class);

        LinearLayoutManager llm = new LinearLayoutManager(Base.contexto);

        recyclerView.setLayoutManager(Base.llm);
        ItemCardAlojAdapter adapter = new ItemCardAlojAdapter(Base.contexto, alojamientos, null, null,false);
        recyclerView.setAdapter(adapter);

    }


}
