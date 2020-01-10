package com.yuua.alojamientosyuua.net;

import android.os.AsyncTask;

import com.yuua.reto.net.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AsynCliente extends AsyncTask<Request, byte[], Object> {
    private final int PUERTO = 55555;
    private final String IP = "192.168.101.233";
    private Socket cliente = null;
    private ObjectOutputStream salida = null;
    private ObjectInputStream entrada = null;
    public String jsonResultado = "";

    @Override
    protected Object doInBackground(Request... requests) {
        try {
            cliente = new Socket(IP, PUERTO);
            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());
            salida.writeObject(requests);
            Request peticion = (Request) entrada.readObject();
            switch (peticion.getCodigoPeticion()) {
                case 0:
                    break;
                case 61:
                    jsonResultado = (String) peticion.getObjetoEnviado();
                    break;
                default:
                    break;
            }
            return jsonResultado;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cliente != null)
                    cliente.close();
                if (entrada != null)
                    entrada.close();
                if (salida != null)
                    salida.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
