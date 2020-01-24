package com.yuua.alojamientosyuua.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.net.URL;

public class Imagen implements Serializable {

    public String media;
    public String title;

    public Imagen(String media, String title) {
        this.media = media;
        this.title = title;
    }


}
