package com.yuua.alojamientosyuua;


import com.yuua.alojamientosyuua.entidades.ImagenOnline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImageDownloader implements Runnable {

    private URL url;
    public boolean finalizado = false;
    private String resul;
    private BufferedWriter writer;
    private String parseado;
    private String criterioBusqueda;
    private int cantidad;
    private ArrayList<ImagenOnline> urlsImagenes;


    public ImageDownloader(String criterioBusqueda, int cantidad)
    {
        this.criterioBusqueda=criterioBusqueda;
        this.cantidad=cantidad;
    }

    public ArrayList<ImagenOnline> obtenerImagenes()
    {
        urlsImagenes = new ArrayList<ImagenOnline>();
        criterioBusqueda=criterioBusqueda.replace(" ","+");
        String query = "https://api.qwant.com/api/search/images?count="+cantidad+"&offset=0&q=" + criterioBusqueda + "&t=web&uiv=1";
        System.out.println("Query busqueda: " + query);
        try{
            url=new URL(query);
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
                JSONObject itemJson = jsonArray.getJSONObject(i);
                ImagenOnline imagen = new ImagenOnline(itemJson.getString("media"),itemJson.getString("title"));
                urlsImagenes.add(imagen);
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


