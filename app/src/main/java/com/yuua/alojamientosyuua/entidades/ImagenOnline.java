package com.yuua.alojamientosyuua.entidades;

import java.io.Serializable;

public class ImagenOnline implements Serializable {

    public String media;
    public String title;

    public ImagenOnline(String media, String title) {
        this.media = media;
        this.title = title;
    }


}
