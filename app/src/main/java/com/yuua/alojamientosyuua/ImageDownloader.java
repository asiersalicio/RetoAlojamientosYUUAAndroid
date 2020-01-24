package com.yuua.alojamientosyuua;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yuua.alojamientosyuua.entidades.Imagen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class ImageDownloader implements Runnable {

    private URL url;
    public boolean finalizado = false;
    private String resul;
    private BufferedWriter writer;
    private String parseado;
    private String criterioBusqueda;
    private int cantidad;
    private ArrayList<String> urlsImagenes;


    public ImageDownloader(String criterioBusqueda, int cantidad)
    {
        this.criterioBusqueda=criterioBusqueda;
        this.cantidad=cantidad;
    }

    public ArrayList<String> obtenerLinksImagenes()
    {
        urlsImagenes = new ArrayList<String>();
        criterioBusqueda=criterioBusqueda.replace(" ","+");
        System.out.println("Query busqueda: " + "https://api.qwant.com/api/search/images?count="+cantidad+"&offset=0&q=" + criterioBusqueda + "&t=web&uiv=1");
        try{
            url=new URL("https://api.qwant.com/api/search/images?count="+cantidad+"&offset=0&q=" + criterioBusqueda + "&t=web&uiv=1");
        }catch (MalformedURLException ex){}

        finalizado=false;
        prepararHilo();
        while(!finalizado)
        {
            Thread.yield();
        }
        System.out.println("URLS: " + urlsImagenes.toString());
        return urlsImagenes;
    }



    private void prepararHilo() {


        Thread hilo = new Thread(this);

        hilo.start();

    }

    private void parseJSON(String resul) {

        try{
            JSONObject obj = new JSONObject(resul);
            JSONArray jsonArray = obj.getJSONObject("data").getJSONObject("result").getJSONArray("items");

            for(int i=0;i<jsonArray.length();i++)
            {
                urlsImagenes.add(jsonArray.getJSONObject(i).getString("media"));
            }


        }catch (JSONException ex){}

    }


    @Override
    public void run() {
        String line = "";
        BufferedReader in = null;
        String jsonCompleto = "";
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null) {
                jsonCompleto += line;
            }
            in.close();
           parseJSON(jsonCompleto);
        } catch (IOException ex) {
        }
        setFinalizado(true);
    }


    private void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    private void setResultado(String string) {
        resul = string;
    }

    private void setParseado(String parseado) {
        this.parseado = parseado;
    }


}


