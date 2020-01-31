package com.yuua.alojamientosyuua.fragmentos;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yuua.alojamientosyuua.FiltrosArrayList;
import com.yuua.alojamientosyuua.ImageDownloader;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.activitys.Base;
import com.yuua.alojamientosyuua.adaptadores.ItemCardAlojAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.ImagenOnline;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class FragmentAlojPorCiudad extends Fragment implements Runnable{

    private View view;
    private ArrayList<Alojamiento> alojamientos;
    private RecyclerView rv;
    private TextView nombreCiudad;
    private Context contextoPadre;
    private ImageView imagenCiudad;
    private String municipio;
    private Date fechaEntrada, fechaSalida;
    private Spinner tipoAlocPorLoc, tipoOrdenAlocPorLoc, ordenAlocPorLoc;
    private ArrayList<Alojamiento> alojamientosOriginal;

    public FragmentAlojPorCiudad(Context context, String municipio, Date fechaEntrada, Date fechaSalida) {
        this.municipio=municipio;
        this.contextoPadre=context;
        this.fechaEntrada=fechaEntrada;
        this.fechaSalida=fechaSalida;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aloj_por_ciudad, container, false);
        rv=view.findViewById(R.id.recyclerViewAlojPorLoc);
        nombreCiudad=view.findViewById(R.id.CiudadAlojPorLoc);
        imagenCiudad=view.findViewById(R.id.ImagenAlojPorLoc);
        tipoAlocPorLoc=view.findViewById(R.id.tipoAlocAlocPorLoc);
        tipoOrdenAlocPorLoc=view.findViewById(R.id.tipoOrdenAlocPorLoc);
        ordenAlocPorLoc=view.findViewById(R.id.ordenAlocPorLoc);

        alojamientos=new ArrayList<Alojamiento>();
        nombreCiudad.setText(municipio);

        establecerAdaptadoresAloj();
        establecerAdaptadoresTipoAloc();
        establecerOrdenTipoAloj();
        establecerOrdenAscDesc();


        return view;
    }

    private void establecerOrdenAscDesc() {
        ArrayList<String> tipos= new ArrayList<String>();
        tipos.add(getString(R.string.upward));
        tipos.add(getString(R.string.falling));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Base.contexto, android.R.layout.simple_spinner_item, tipos);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ordenAlocPorLoc.setAdapter(arrayAdapter);
    }

    private void establecerOrdenTipoAloj() {
        ArrayList<String> tipos= new ArrayList<String>();
        tipos.add(getString(R.string.name));
        tipos.add(getString(R.string.price));
        tipos.add(getString(R.string.capacity));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Base.contexto, android.R.layout.simple_spinner_item, tipos);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoOrdenAlocPorLoc.setAdapter(arrayAdapter);

        tipoOrdenAlocPorLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Orden tipo: " + position);
                switch (position)
                {
                    case 0:
                        if(ordenAlocPorLoc.getSelectedItemPosition()==0)
                        {
                            System.out.println("ASCENDENTE");
                            FiltrosArrayList.ordenarArrayAlojamiento(alojamientos,FiltrosArrayList.NOMBRE,FiltrosArrayList.ASCENDETE);
                        }
                        else
                        {
                            System.out.println("DESCENDENTE");
                            FiltrosArrayList.ordenarArrayAlojamiento(alojamientos,FiltrosArrayList.NOMBRE,FiltrosArrayList.DESCENDENTE);
                        }



                        break;
                    case 1:
                        break;
                    case 2:
                        break;

                }

                rv.setLayoutManager(Base.llm);
                ItemCardAlojAdapter adapterAloj = new ItemCardAlojAdapter(contextoPadre, alojamientos, fechaEntrada, fechaSalida,true);
                rv.setAdapter(adapterAloj);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void establecerAdaptadoresAloj()
    {
        Consultas consultar=new Consultas();
        Request consulta=consultar.alojamientosDisponiblesEntreFechasEnMunicipio(municipio,fechaEntrada,fechaSalida);
        Object resultado=consultar.devolverResultadoPeticion(consulta,Alojamiento.class);
        alojamientos=(ArrayList<Alojamiento>) resultado;
        alojamientosOriginal=new ArrayList<Alojamiento>();
        alojamientosOriginal.addAll(alojamientos);
        Thread descargarImagen = new Thread(this);
        descargarImagen.start();
        rv.setLayoutManager(Base.llm);
        ItemCardAlojAdapter adapterAloj = new ItemCardAlojAdapter(contextoPadre, alojamientos, fechaEntrada, fechaSalida,true);
        rv.setAdapter(adapterAloj);
    }

    public void establecerAdaptadoresTipoAloc()
    {
        Consultas consultas = new Consultas();
        Request request=consultas.queryCampoDistinct(Alojamiento.class, "tipo");
        ArrayList<String> tipos=(ArrayList<String>) consultas.devolverResultadoPeticion(request, String.class);
        tipos.add(0,getString(R.string.all));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Base.contexto, android.R.layout.simple_spinner_item, tipos);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoAlocPorLoc.setAdapter(arrayAdapter);

        tipoAlocPorLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    alojamientos=FiltrosArrayList.filtrarArrayAloj(alojamientosOriginal, (String) tipoAlocPorLoc.getSelectedItem(), FiltrosArrayList.TIPO);
                    rv.setLayoutManager(Base.llm);
                    ItemCardAlojAdapter adapterAloj = new ItemCardAlojAdapter(contextoPadre, alojamientos, fechaEntrada, fechaSalida,true);
                    rv.setAdapter(adapterAloj);
                }
                else
                {
                    alojamientos=alojamientosOriginal;
                    rv.setLayoutManager(Base.llm);
                    ItemCardAlojAdapter adapterAloj = new ItemCardAlojAdapter(contextoPadre, alojamientos, fechaEntrada, fechaSalida,true);
                    rv.setAdapter(adapterAloj);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void run() {
        final ArrayList<ImagenOnline> imagenes;
        ImageDownloader imageDownloader = new ImageDownloader(municipio,1);
        imagenes= imageDownloader.obtenerImagenes();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    Picasso.get().load(imagenes.get(0).media).into(imagenCiudad);
                }catch (IllegalArgumentException ex){}
                catch (IndexOutOfBoundsException ex2){}
            }
        });
    }
}
