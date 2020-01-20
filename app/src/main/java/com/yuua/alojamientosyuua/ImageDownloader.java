package com.yuua.alojamientosyuua;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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





        return resul;
    }

    private String parseJSON(String resul) {
            String resultado = resul;

        Log.println(Log.DEBUG,"JSON",resul);

        resultado=resultado.substring( resul.indexOf("\"media\":")+9);
        Log.println(Log.DEBUG,"JSON",resultado);
        resultado=resultado.substring(0,resul.indexOf(","));

        resultado=resultado.replace("\\","");

            System.out.println(resultado);

            return resultado;


    }

    private void downloadImage(String imageUrl)
    {
        try {
            System.out.println("URL imagen " +imageUrl);
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(Environment.getDataDirectory().getAbsolutePath() + "/prueba.jpg");
            System.out.println(Environment.getDataDirectory().getAbsolutePath());
            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        }catch (IOException ex){ex.printStackTrace();}
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

            String parseado;
            parseado=parseJSON(all);
            downloadImage(parseado);


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


