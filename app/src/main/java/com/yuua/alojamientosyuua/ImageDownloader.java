package com.yuua.alojamientosyuua;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class ImageDownloader extends Thread {

    private URL url;
    private boolean finalizado = false;
    private String resul;
    private BufferedWriter writer;

    private Context context;


    public ImageDownloader(Context context) {
        this.context = context;
    }


    public String savePage(final String URL) throws IOException {

        url = new URL(URL);
        start();

        while (!finalizado) {

        }


        return resul;
    }

    private String parseJSON(String resul) {
        String resultado = resul;
        resultado = resultado.substring(resul.indexOf("\"media\":") + 9);
        resultado = resultado.substring(0, resultado.indexOf(","));
        resultado = resultado.replace("\\", "");
        return resultado;
    }

    private void downloadImage(String imageUrl) {
        try {
            System.out.println("URL imagen " + imageUrl);
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

            String parseado;
            parseado = parseJSON(all);
            downloadImage(parseado);


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


}


