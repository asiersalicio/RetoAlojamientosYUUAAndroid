package com.yuua.alojamientosyuua.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.HotelInfo;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Alojamiento;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    public ArrayList<Alojamiento> alojamientos;
    private Context contextoBase;

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_aloj, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);



        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {
        personViewHolder.nombreHotel.setText(alojamientos.get(i).getNombre());
        personViewHolder.descHotel.setText(alojamientos.get(i).getDescripcion());
        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.INFO,"A",alojamientos.get(personViewHolder.getAdapterPosition()).getNombre());
                Intent intento = new Intent(contextoBase, HotelInfo.class);
                intento.putExtra("alojamiento", alojamientos.get(personViewHolder.getAdapterPosition()));
                contextoBase.startActivity(intento);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return alojamientos.size();
    }


    public RVAdapter(Context contextoBase, ArrayList<Alojamiento> alojamientos){
        this.alojamientos = alojamientos;
        this.contextoBase=contextoBase;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView nombreHotel;
        TextView descHotel;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.CardH);
            nombreHotel = (TextView)itemView.findViewById(R.id.cardNombreH);
            descHotel = (TextView)itemView.findViewById(R.id.cardDescH);
        }
    }



}