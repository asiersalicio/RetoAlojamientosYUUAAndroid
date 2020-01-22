package com.yuua.alojamientosyuua;

import android.content.Context;

import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Municipio;
import com.yuua.alojamientosyuua.entidades.Usuario;

import java.util.ArrayList;
import java.util.Date;

public class DatosApp {

    public static Context currentContext;
    public static Usuario user;
    public static boolean DATOSDEBUG =false;
    public static Object itemSeleccionado;
    public static Date fechaEntrada, fechaSalida;

    public static ArrayList<Alojamiento> getDebugAlojamientos(){
        ArrayList<Alojamiento> alojamientos=new ArrayList<Alojamiento>();
        alojamientos.add(new Alojamiento("Hotel", "Hotel Melia Bilbao", "Un hotel en bilbao", 600000000, "Sin web", "Sin email", 100, null));
        alojamientos.add(new Alojamiento("Hotel", "Sercotel Bilbao", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, null));
        alojamientos.add(new Alojamiento("Hotel", "7 Kale Bed And Breakfast Bilbao", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, null));
        alojamientos.add(new Alojamiento("Hotel", "Hotel Carlton", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, null));
        alojamientos.add(new Alojamiento("Hotel", "Hotel Ibis Bilbao", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, null));
        return alojamientos;
    }

    public static ArrayList<Municipio> getDebugMunicipios()
    {
        ArrayList<Municipio> municipios=new ArrayList<Municipio>();
        municipios.add(new Municipio(new char[]{'4','8'},"Bilbao"));
        municipios.add(new Municipio(new char[]{'0','2'},"Prueba"));
        return municipios;
    }

}
