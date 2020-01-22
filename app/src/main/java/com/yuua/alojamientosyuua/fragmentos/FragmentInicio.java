package com.yuua.alojamientosyuua.fragmentos;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.DatosApp;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.activitys.Base;
import com.yuua.alojamientosyuua.adaptadores.ItemCardAlojAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.util.ArrayList;

public class FragmentInicio extends Fragment{

    public View view;
    public ArrayList<Alojamiento> alojamientos;
    public RecyclerView rv;
    private Context contextoBase;

    public FragmentInicio(Context contextoBase) {
        this.contextoBase=contextoBase;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inicio, container, false);
        rv=view.findViewById(R.id.recyclerViewInicio);
        rv.requestFocus();
        cargarDatosBD();
        return view;
    }

    public void cargarDatosBD() {

        if(!DatosApp.DATOSDEBUG)
        {
            Consultas consultar=new Consultas();
            Request consulta=consultar.prepararQueryHibernate(60,Alojamiento.class,new String[]{},new String[]{});
            Object resultado=consultar.devolverResultadoPeticion(consulta,Alojamiento.class);
            alojamientos= (ArrayList<Alojamiento>) resultado;
        }
        else
        {
            alojamientos=DatosApp.getDebugAlojamientos();
        }


        rv.setLayoutManager(Base.llm);
        ItemCardAlojAdapter adapter = new ItemCardAlojAdapter(contextoBase, alojamientos);
        rv.setAdapter(adapter);
    }


}
