package com.yuua.alojamientosyuua;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

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
            Looper.prepare();
            Toast.makeText(this.context,"hilo empezado",Toast.LENGTH_LONG);
            cliente = new Socket(IP, PUERTO);
            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());
            mandarRequest(new Request(0,"holi"));
            cliente.close();
            entrada.close();
            salida.close();
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
}
