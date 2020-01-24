package com.yuua.alojamientosyuua.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.net.URL;

public class Imagen implements Serializable {

    public String media;
    public String title;

    public Imagen(URL media, String nombreImagen) {
        this.media = media.toString();
        this.title = title;
    }


}
