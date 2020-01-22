package com.yuua.alojamientosyuua.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuua.alojamientosyuua.DatosApp;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.activitys.Base;
import com.yuua.alojamientosyuua.adaptadores.ItemCardAlojAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Municipio;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.util.ArrayList;

public class FragmentAlojPorCiudad extends Fragment {

    private View view;
    private ArrayList<Alojamiento> alojamientos;
    private RecyclerView rv;
    private TextView nombreCiudad;
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
        nombreCiudad=view.findViewById(R.id.CiudadAlojPorLoc);
        alojamientos=new ArrayList<Alojamiento>();

        if(!DatosApp.DATOSDEBUG)
        {
            Municipio municipio = (Municipio) DatosApp.itemSeleccionado;
            nombreCiudad.setText(municipio.getNombre());
            Consultas consultar=new Consultas();
            Request consulta=consultar.prepararQueryHibernate(60,Alojamiento.class,new String[]{},new String[]{});
            Object resultado=consultar.devolverResultadoPeticion(consulta,Alojamiento.class);
            alojamientos= (ArrayList<Alojamiento>) resultado;
        }
        else
        {
            nombreCiudad.setText("Bilbao");
            alojamientos=DatosApp.getDebugAlojamientos();
        }

        rv.setLayoutManager(Base.llm);
        ItemCardAlojAdapter adapter = new ItemCardAlojAdapter(contextoPadre, alojamientos);
        rv.setAdapter(adapter);


        return view;
    }

}
