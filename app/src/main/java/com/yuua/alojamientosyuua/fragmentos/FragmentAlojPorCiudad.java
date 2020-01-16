package com.yuua.alojamientosyuua.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.activitys.Base;
import com.yuua.alojamientosyuua.adaptadores.ItemCardAlojAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;

import java.util.ArrayList;

public class FragmentAlojPorCiudad extends Fragment {

    private View view;
    private ArrayList<Alojamiento> alojamientos;
    private RecyclerView rv;
    private Context contextoPadre;

    public FragmentAlojPorCiudad(Context context) {
        this.contextoPadre=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aloj_por_ciudad, container, false);
        view.findViewById(R.id.recyclerViewAlojPorLoc);
        rv=view.findViewById(R.id.recyclerViewAlojPorLoc);
        alojamientos=new ArrayList<Alojamiento>();
        alojamientos.add(new Alojamiento("Hotel", "Hotel Melia", "Un hotel en bilbao", 600000000, "Sin web", "Sin email", 100, null));
        alojamientos.add(new Alojamiento("Hotel", "Hotel Prueba", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, null));


        rv.setLayoutManager(Base.llm);
        ItemCardAlojAdapter adapter = new ItemCardAlojAdapter(contextoPadre, alojamientos);
        rv.setAdapter(adapter);


        return view;
    }

}
