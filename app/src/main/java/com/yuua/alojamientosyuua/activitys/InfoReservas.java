package com.yuua.alojamientosyuua.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.Sistema;
import com.yuua.alojamientosyuua.adaptadores.ItemCardAlojAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Reserva;

import java.util.ArrayList;

public class InfoReservas extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView nReserva, fechaEntrada, fechaSalida;
    private Reserva reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_reservas);
        getSupportActionBar().setTitle(getString(R.string.bookingInformation));
        inicializar();
    }

    private void inicializar() {
        recyclerView=findViewById(R.id.recyclerViewInfoReservas);
        nReserva=findViewById(R.id.nReservaInfoReserva);
        fechaEntrada=findViewById(R.id.fechaEntradaInfoReserva);
        fechaSalida=findViewById(R.id.fechaSalidaInfoReserva);
        reserva=(Reserva) getIntent().getSerializableExtra("reserva");
        rellenarDatos();
    }

    private void rellenarDatos() {
        ArrayList<Alojamiento> alojamientos=new ArrayList<Alojamiento>();
        alojamientos.add(reserva.getAlojamiento());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        ItemCardAlojAdapter adapter = new ItemCardAlojAdapter(this, alojamientos, null, null, false);
        recyclerView.setAdapter(adapter);

        nReserva.setText(Integer.toString(reserva.getId()));
        fechaEntrada.setText(reserva.getFechaEntrada().toGMTString());
        fechaSalida.setText(reserva.getFechaSalida().toGMTString());

    }

    public void btnCancelarReserva(View view)
    {

    }
}
