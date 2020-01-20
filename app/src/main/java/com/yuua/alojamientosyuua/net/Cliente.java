package com.yuua.alojamientosyuua.net;

import android.content.Context;
import android.util.Log;

import com.yuua.alojamientosyuua.activitys.MainActivity;
import com.yuua.reto.net.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class Cliente implements Runnable {
    private final int PUERTO = 55555;
    private final String IP = "192.168.101.233";
    private Socket cliente = null;
    private ObjectOutputStream salida = null;
    private ObjectInputStream entrada = null;
    private Request peticion;
    public String jsonResultado = "";
    public Boolean booleanResultado = null;

    public Cliente(Request peticion) {
        this.peticion = peticion;
    }

    @Override
    public void run() {
        try {
            cliente = new Socket(IP, PUERTO);
            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());
            salida.writeObject(peticion);
            Request peticion = (Request) entrada.readObject();
            switch (peticion.getCodigoPeticion()) {
                //Buscar alojamientos disponibles por fechas
                case 31:
                    jsonResultado = (String) peticion.getObjetoEnviado();
                //Respuesta consulta fechas aloj
                case 41:
                    booleanResultado=(boolean) peticion.getObjetoEnviado();
                //Respuesta insert - boolean
                case 51:
                    booleanResultado = (boolean) peticion.getObjetoEnviado();
                    break;
                //Respuesta peticion - objeto
                case 61:
                    jsonResultado = (String) peticion.getObjetoEnviado();
                    break;
                case 71:
                    break;
                default:
                    break;
            }
            salida.close();
        } catch (SocketTimeoutException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mandarRequest(Request peticion) {
        try {
            salida.writeObject(peticion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String leerJson() {
        while (jsonResultado.equals("")) {
        }
        return jsonResultado;
    }

    public boolean leerBoolean() {
        while (booleanResultado == null) {
        }
        return booleanResultado.booleanValue();
    }
}
