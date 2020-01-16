package com.yuua.alojamientosyuua.net;

import android.content.Context;
import android.util.Log;

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

    public Cliente(Request peticion) {
        this.peticion = peticion;
    }

    @Override
    public void run() {
        try {
            cliente = new Socket(IP,PUERTO);
            Log.println(Log.INFO,"paso","CLIENTE CREADO");
            salida = new ObjectOutputStream(cliente.getOutputStream());
            Log.println(Log.INFO,"paso","SALIDA CREADO");
            entrada = new ObjectInputStream(cliente.getInputStream());
            Log.println(Log.INFO,"paso","ENTRADA CREADO");
            salida.writeObject(peticion);
            Log.println(Log.INFO,"paso","OBJETO ESCRITO");
            Request peticion = (Request) entrada.readObject();
            Log.println(Log.INFO,"paso","OBJETO LEIDO");
            switch (peticion.getCodigoPeticion()) {
                case 61:
                    jsonResultado = (String) peticion.getObjetoEnviado();
                    break;
                default:
                    break;
            }
            salida.close();
        } catch(SocketTimeoutException e) {
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
    public String leerJson(){
        while (jsonResultado.equals("")){
            Log.println(Log.INFO,"paso","ESPERANDO JSON");
        }
        return jsonResultado;
    }


}
