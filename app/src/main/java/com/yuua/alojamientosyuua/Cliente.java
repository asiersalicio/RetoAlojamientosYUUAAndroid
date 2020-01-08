package com.yuua.alojamientosyuua;

import android.content.Context;

import com.yuua.reto.net.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Cliente implements Runnable{
    private final int PUERTO = 55555;
    private final String IP = "192.168.101.233";
    private Context context;
    private Socket cliente = null;
    private ObjectOutputStream salida = null;
    private ObjectInputStream entrada = null;

    public Cliente(Context contexto) {
        this.context=contexto;
    }

    @Override
    public void run() {
        try {
            cliente = new Socket(IP, PUERTO);
            System.out.println("Conexión realizada con servidor");
            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());
            mandarRequest(new Request(1,"holi"));
            while (true) {
                Request peticion = (Request) entrada.readObject();
                switch (peticion.getCodigoPeticion()) {
                    case 0:
                        break;
                    default:
                        break;
                }
            }

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
    }

    public void mandarRequest(Request peticion) {
        try {
            salida.writeObject(peticion);
        } catch (IOException e) {
        }
    }

    public void finalizar() {
        mandarRequest(new Request(0,null));
        try {
            cliente.close();
            entrada.close();
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
