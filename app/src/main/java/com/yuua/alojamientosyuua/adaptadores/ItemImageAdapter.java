package com.yuua.alojamientosyuua.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yuua.alojamientosyuua.ImageDownloader;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Imagen;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ItemImageAdapter extends RecyclerView.Adapter<ItemImageAdapter.Image>{

    private ArrayList<String> imagenes;
    private Context contextoPadre;


    public ItemImageAdapter(Context contextoPadre, ArrayList<String> imagenes){
        this.imagenes = imagenes;
        this.contextoPadre = contextoPadre;

    }

    public static class Image extends RecyclerView.ViewHolder {


        ImageView imageView;

        public ConstraintLayout cl;

        Image(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item_imageview);
        }
    }

    @NonNull
    @Override
    public ItemImageAdapter.Image onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false);
        ItemImageAdapter.Image pvh = new ItemImageAdapter.Image(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ItemImageAdapter.Image image, final int i) {
            System.out.println("Aplicando imagen: " + imagenes.get(i));
            Picasso.get().load(imagenes.get(i)).into(image.imageView);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }


}
