package com.yuua.alojamientosyuua.net;

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
            System.out.println("Conectado");
            salida = new ObjectOutputStream(cliente.getOutputStream());
            System.out.println("Get output stream");
            entrada = new ObjectInputStream(cliente.getInputStream());
            System.out.println("Get input stream");
            salida.writeObject(peticion);
            System.out.println("Write object");
            Request peticion = (Request) entrada.readObject();
            System.out.println("Read object");
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
            System.out.println("preclose");
            salida.close();
            System.out.println("postclose");

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
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
