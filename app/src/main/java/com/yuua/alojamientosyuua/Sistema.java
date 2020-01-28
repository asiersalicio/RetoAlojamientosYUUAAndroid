package com.yuua.alojamientosyuua;

import android.net.Uri;

import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Localizacion;
import com.yuua.alojamientosyuua.entidades.Usuario;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class Sistema {

    public static Usuario user;
    public static boolean SIMULACIONALOJAMIENTOS=true;
    public static boolean SIMULACIONUSUARIO=true;

    /*
    public static ArrayList<Alojamiento> getDebugAlojamientos(){
        Localizacion loc = new Localizacion(new Pais(new char[]{'3','4'},"España"), new Municipio(new char[]{'0','1'}, "Bilbao"),new Territorio(new char[]{'1'},"Vizcaya"),"48001","Alameda Rekalde", 43.263433, -2.934844);
        ArrayList<Alojamiento> alojamientos=new ArrayList<Alojamiento>();
        alojamientos.add(new Alojamiento("Hotel", "Hotel Melia Bilbao", "Un hotel en bilbao", 600000000, "Sin web", "Sin email", 100, loc));
        alojamientos.add(new Alojamiento("Hotel", "Sercotel Bilbao", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, loc));
        alojamientos.add(new Alojamiento("Hotel", "7 Kale Bed And Breakfast Bilbao", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, loc));
        alojamientos.add(new Alojamiento("Hotel", "Hotel Carlton", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, loc));
        alojamientos.add(new Alojamiento("Hotel", "Hotel Ibis Bilbao", "Un hotel de prueba", 600000000, "Sin web", "Sin email", 100, loc));
        return alojamientos;
    }


    public static ArrayList<Municipio> getDebugMunicipios()
    {
        ArrayList<Municipio> municipios=new ArrayList<Municipio>();
        municipios.add(new Municipio(new char[]{'4','8'},"Bilbao"));
        municipios.add(new Municipio(new char[]{'0','2'},"Prueba"));
        return municipios;
    }*/

    public static Usuario getDebugUser()
    {
        return new Usuario("12345678Z","Usuario","Prueba","cliente","prueba","202cb962ac59075b964b07152d234b70",new Date(),"prueba@prueba.com",600000000, null);
    }

    public static ArrayList<Object> getDebugUsers()
    {
        ArrayList<Object> usuarios = new ArrayList<Object>();
        usuarios.add(getDebugUser());
        return usuarios;
    }

}
