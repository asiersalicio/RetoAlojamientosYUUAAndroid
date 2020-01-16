package com.yuua.alojamientosyuua.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.activitys.HotelInfo;
import com.yuua.alojamientosyuua.entidades.Alojamiento;

import java.util.ArrayList;

public class ItemSearchResultAdapter extends RecyclerView.Adapter<ItemSearchResultAdapter.SearchResult> {

    private ArrayList<Alojamiento> alojamientos;
    private Context contextoPadre;


    public ItemSearchResultAdapter(Context contextoPadre, ArrayList<Alojamiento> alojamientos){
        this.alojamientos = alojamientos;
        this.contextoPadre = contextoPadre;
    }

    public static class SearchResult extends RecyclerView.ViewHolder {

        ImageView icono;
        TextView texto;

        SearchResult(View itemView) {
            super(itemView);
            icono=itemView.findViewById(R.id.searchResultIcon);
            texto=itemView.findViewById(R.id.searchResultText);
        }
    }

    @NonNull
    @Override
    public ItemSearchResultAdapter.SearchResult onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_result, viewGroup, false);
        ItemSearchResultAdapter.SearchResult pvh = new ItemSearchResultAdapter.SearchResult(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ItemSearchResultAdapter.SearchResult searchResult, int i) {
        searchResult.texto.setText("PRUEBA");


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return alojamientos.size();
    }


}
