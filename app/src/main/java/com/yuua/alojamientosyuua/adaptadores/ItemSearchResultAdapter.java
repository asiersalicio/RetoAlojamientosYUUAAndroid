package com.yuua.alojamientosyuua.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.ObjetoGenerico;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Municipio;

import java.util.ArrayList;

public class ItemSearchResultAdapter extends RecyclerView.Adapter<ItemSearchResultAdapter.SearchResult> {

    public ArrayList<Object> itemsAdaptador;
    private Context contextoPadre;
    private Intent intent;

    public ItemSearchResultAdapter(Context contextoPadre, ArrayList<Object> items){
        this.itemsAdaptador = items;
        this.contextoPadre = contextoPadre;
        this.intent=intent;
    }

    public static class SearchResult extends RecyclerView.ViewHolder {
        ImageView icono;
        TextView texto;
        TextView textoDescripcion;
        ImageView iconoDescripcion;
        ConstraintLayout cl;
        SearchResult(View itemView) {
            super(itemView);
            icono=itemView.findViewById(R.id.searchResultIcon);
            texto=itemView.findViewById(R.id.searchResultText);
            cl=itemView.findViewById(R.id.constraintSearchItem);
            textoDescripcion=itemView.findViewById(R.id.searchResultTextDesc);
            iconoDescripcion =itemView.findViewById(R.id.searchResultIconDesc);
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
        final Object item = itemsAdaptador.get(i);
        if(item instanceof Alojamiento) {
            Alojamiento aloj = (Alojamiento) item;
            searchResult.texto.setText(aloj.getNombre());
            searchResult.icono.setImageResource(R.drawable.ic_hotel_blue_24dp);
            searchResult.textoDescripcion.setText(aloj.getLocalizacion().getTmunicipio().getNombre());
            searchResult.textoDescripcion.setVisibility(View.VISIBLE);
            searchResult.iconoDescripcion.setVisibility(View.VISIBLE);

        } else if(item instanceof Municipio){
            Municipio muni = (Municipio) item;
            searchResult.texto.setText(muni.getNombre());
            searchResult.icono.setImageResource(R.drawable.ic_location_city_black_24dp);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) searchResult.texto.getLayoutParams();
            params.verticalBias = 0.5f; // here is one modification for example. modify anything else you want :)
            searchResult.texto.setLayoutParams(params);

            searchResult.textoDescripcion.setVisibility(View.INVISIBLE);
            searchResult.iconoDescripcion.setVisibility(View.INVISIBLE);
        }
        searchResult.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("item", new ObjetoGenerico(item));
                ((Activity)contextoPadre).setResult(Activity.RESULT_OK,returnIntent);
                ((Activity)contextoPadre).finish();
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return itemsAdaptador.size();
    }

    public void reemplazarItems(ArrayList<Object> items)
    {
        this.itemsAdaptador.clear();
        this.itemsAdaptador.add(items);
        //notifyDataSetChanged();
        System.out.println("Cambiada lista de hoteles con: " + items.size() + " items");
    }
}
