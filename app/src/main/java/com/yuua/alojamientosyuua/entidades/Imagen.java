package com.yuua.alojamientosyuua.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URL;

public class Imagen {

    public URL url;
    public byte[] b = new byte[2048];

    public Imagen(URL url, byte[] byteArray) {
        this.url = url;
        this.b = b;

    }

    public Bitmap getBitmap(int Width, int Height)
    {
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        return Bitmap.createScaledBitmap(bmp, 100, 100, false);
    }
}
