package com.yuua.alojamientosyuua;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImageDownloader extends Thread {

    private URL url;
    private boolean finalizado=false;
    private String resul;
    private BufferedWriter writer;

    private Context context;


    public ImageDownloader(Context context)
    {
        this.context=context;
    }


    public String savePage(final String URL) throws IOException {

        url = new URL(URL);
        start();

        while (!finalizado){

        }


        parseJSON(resul);


        return resul;
    }

    private void parseJSON(String resul) {
        Log.println(Log.DEBUG,"JSON",resul);

            resul=resul.substring( resul.indexOf("\"media\":")+9);
            resul=resul.substring(0,resul.indexOf("\",\""));

            resul=resul.replace("\\","");

            System.out.println(resul);



    }

    @Override
    public void run() {
        String line = "";
        BufferedReader in = null;
        String all = "";
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null) {


                all+=line;


            }
            in.close();
        } catch (IOException ex) {
        }
        setResultado(all);
        setFinalizado(true);
    }


    private void setFinalizado(boolean finalizado)
    {
        this.finalizado=finalizado;
    }

    private void setResultado(String string)
    {
        resul=string;
    }





}


