package com.yuua.alojamientosyuua.fragmentos;


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
import com.yuua.alojamientosyuua.adaptadores.ItemReservaAdapter;
import com.yuua.alojamientosyuua.entidades.Reserva;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.util.ArrayList;


public class FragmentReservas extends Fragment {

    private RecyclerView recyclerView;
    private View view;


    public FragmentReservas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reservas, container, false);
        inicializar();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Consultas consultas = new Consultas();
        Request peticionReservas = consultas.buscarAlojamientosReservadosPorDni(Sistema.user.getIdDni());
        ArrayList<Reserva> reservas=new ArrayList<Reserva>();
        reservas=(ArrayList<Reserva>) consultas.devolverResultadoPeticion(peticionReservas, Reserva.class);

        LinearLayoutManager llm = new LinearLayoutManager(Base.contexto);

        recyclerView.setLayoutManager(Base.llm);
        ItemReservaAdapter adapter = new ItemReservaAdapter(Base.contexto, reservas);
        recyclerView.setAdapter(adapter);
    }

    private void inicializar() {

        recyclerView=view.findViewById(R.id.recyclerViewReservas);
    }


}
