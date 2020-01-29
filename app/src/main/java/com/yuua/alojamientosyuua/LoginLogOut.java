package com.yuua.alojamientosyuua;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.yuua.alojamientosyuua.entidades.Usuario;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.util.ArrayList;

public class LoginLogOut {



    public static void logearse(Context context) {
        SharedPreferences login=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        String idDni;
        String pass;

        idDni=login.getString("idDni", "");
        pass=login.getString("contrasena", "");
        System.out.println("Intentando logearse localmente con: " + idDni + ":" + pass);
        Consultas consultas = new Consultas();
        Request peticion = consultas.prepararQueryHibernate(Consultas.QUERY_CON_CONDICIONES, Usuario.class, new String[]{"idDni","contrasena"},new String[]{idDni,pass});
        ArrayList<Usuario> usuarios = (ArrayList<Usuario>)consultas.devolverResultadoPeticion(peticion, Usuario.class);
        if(usuarios.size()==1)
        {
            Sistema.user=usuarios.get(0);

           ;

        }

    }


    public static void guardarLogin(Context context, String idDni, String pass) {
        SharedPreferences login=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = login.edit();
        editor.putString("idDni",idDni);
        editor.putString("contrasena",pass);
        editor.commit();
    }

    public static void cerrarSesion(Context context)
    {
        Sistema.user=null;
        SharedPreferences login=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = login.edit();
        editor.putString("idDni","");
        editor.putString("contrasena","");
        editor.commit();
    }
}
