package com.yuua.alojamientosyuua.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.ObjetoGenerico;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.activitys.InfoReservas;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Reserva;

import java.util.ArrayList;

    public class ItemReservaAdapter extends RecyclerView.Adapter<com.yuua.alojamientosyuua.adaptadores.ItemReservaAdapter.ReservaItem> {

        public ArrayList<Reserva> itemsAdaptador;
        private Context contextoPadre;

        public ItemReservaAdapter(Context contextoPadre, ArrayList<Reserva> items){
            this.itemsAdaptador = items;
            this.contextoPadre = contextoPadre;
        }

        public static class ReservaItem extends RecyclerView.ViewHolder {

            ConstraintLayout constraintLayout;
            TextView bookTextItem, bookLocItem, bookFromItem, bookToItem;

            ReservaItem(View itemView) {
                super(itemView);
                constraintLayout=(ConstraintLayout)itemView.findViewById(R.id.constraintBookItem);
                bookTextItem=(TextView)itemView.findViewById(R.id.bookAlocItem);
                bookLocItem=(TextView)itemView.findViewById(R.id.bookLocitem);
                bookFromItem=(TextView)itemView.findViewById(R.id.bookFromItem);
                bookToItem=(TextView)itemView.findViewById(R.id.bookToItem);
            }
        }

        @NonNull
        @Override
        public ReservaItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reserva, viewGroup, false);
            ReservaItem pvh = new ReservaItem(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(final com.yuua.alojamientosyuua.adaptadores.ItemReservaAdapter.ReservaItem reservaItem, int i) {
            final Reserva reserva = itemsAdaptador.get(i);
            reservaItem.bookLocItem.setText(reserva.getAlojamiento().getLocalizacion().getTmunicipio());
            reservaItem.bookTextItem.setText(reserva.getAlojamiento().getNombre());
            reservaItem.bookFromItem.setText(reserva.getFechaEntrada().toGMTString());
            reservaItem.bookToItem.setText(reserva.getFechaSalida().toGMTString());

            reservaItem.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent(contextoPadre, InfoReservas.class);
                    intento.putExtra("reserva", reserva);
                    contextoPadre.startActivity(intento);
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

    }


