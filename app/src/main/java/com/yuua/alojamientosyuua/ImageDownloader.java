package com.yuua.alojamientosyuua;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

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



        return resul;
    }

    @Override
    public void run() {
        String line = "";
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null) {
                Log.println(Log.DEBUG,"PRUEBA IMAGEN",line);
                if(line.contains("<img"))
                {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.html", true)));
                    out.println(line);
                    out.close();
                    Log.println(Log.DEBUG,"IMAGEN",line);
                }
            }
            in.close();
        } catch (IOException ex) {
        }
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


