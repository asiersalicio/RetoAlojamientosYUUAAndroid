package com.yuua.alojamientosyuua.net;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Localizacion;
import com.yuua.alojamientosyuua.entidades.Municipio;
import com.yuua.alojamientosyuua.entidades.Pais;
import com.yuua.alojamientosyuua.entidades.Territorio;
import com.yuua.reto.net.Request;

import java.util.ArrayList;
import java.util.List;

public class Consultas {

    public static final int QUERY_CON_CONDICIONES=60;
    public static final int QUERY_CON_CONDICIONES_LIKE=65;

    public Request prepararInsertHibernate(Class clase ,Object[] objeto) {
        String nombreSimple = clase.getSimpleName();
        Request peticion;
        Gson parser =new Gson();
        String json=parser.toJson(objeto);
        peticion = new Request(50, new Object[]{nombreSimple,json});
        return peticion;
    }

    public Request prepararQueryHibernate(int codigo, Class claseObjeto, String[] campos, String[] condiciones) {
        Request peticion = new Request(codigo, new Object[]{claseObjeto.getSimpleName(), campos, condiciones});
        return peticion;
    }

    public Object devolverResultadoPeticion(Request peticion, Class tipo) {
        Gson gson = new Gson();
        List<?> list = new ArrayList<>();
        String jsonResultado = ejecutarPeticion(peticion);
        switch (tipo.getSimpleName()) {
            case "Alojamiento":
                list = gson.fromJson(jsonResultado, new TypeToken<List<Alojamiento>>() {
                }.getType());
                break;
            case "Pais":
                list = gson.fromJson(jsonResultado, new TypeToken<List<Pais>>() {
                }.getType());
                break;
            case "Territorio":
                list = gson.fromJson(jsonResultado, new TypeToken<List<Territorio>>() {
                }.getType());
                break;
            case "Municipio":
                list = gson.fromJson(jsonResultado, new TypeToken<List<Municipio>>() {
                }.getType());
                break;
            case "Localizacion":
                list = gson.fromJson(jsonResultado, new TypeToken<List<Localizacion>>() {
                }.getType());
                break;
        }
        return list;
    }

    private String ejecutarPeticion(Request peticion) {
        Cliente cliente = getCliente(peticion);
        String resultado = cliente.leerJson();
        return resultado;
    }

    public boolean devolverResultadoPeticionBoolean(Request peticion) {
        Cliente cliente = getCliente(peticion);
        boolean resultadoboolean=cliente.leerBoolean();
        return resultadoboolean;
    }


    private Cliente getCliente(Request peticion) {
        Cliente cliente = new Cliente(peticion);
        Thread hiloCliente = new Thread(cliente);
        hiloCliente.start();
        return cliente;
    }

}
