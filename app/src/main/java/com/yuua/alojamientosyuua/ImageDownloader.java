package com.yuua.alojamientosyuua;


import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.yuua.alojamientosyuua.entidades.Imagen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImageDownloader extends Thread {

    private URL url;
    public static boolean finalizado = false;
    private String resul;
    private BufferedWriter writer;
    private Imagen imagen = null;
    private static String parseado;


    private ImageDownloader(String url)
    {
        try{
            this.url=new URL(url);
        }
        catch (MalformedURLException ex){ex.printStackTrace();}
        prepararHilo();

    }

    public static ArrayList<String> obtenerLinksImagenes(String urljson)
    {
        ImageDownloader id = new ImageDownloader(urljson);
        id.prepararHilo();
        ArrayList<String> urls;
        urls=new ArrayList<String>();
        urls.add(parseado);
        return urls;
    }

    private void prepararHilo() {

        this.start();

        while (!finalizado) {
            Thread.yield();
        }


    }

    private String parseJSON(String resul) {
        String resultado = resul;
        resultado = resultado.substring(resul.indexOf("\"media\":") + 9);
        resultado = resultado.substring(0, resultado.indexOf("\""));
        resultado = resultado.replace("\\", "");
        System.out.println(resultado);
        return resultado;
    }


    @Override
    public void run() {
        String line = "";
        BufferedReader in = null;
        String all = "";
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null) {

                all += line;

            }
            in.close();

           setParseado(parseJSON(all));


        } catch (IOException ex) {
        }
        setFinalizado(true);
    }


    private void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    private void setResultado(String string) {
        resul = string;
    }

    private void setParseado(String parseado) {
        this.parseado = parseado;
    }


}


