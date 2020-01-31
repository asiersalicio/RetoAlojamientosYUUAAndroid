package com.yuua.alojamientosyuua.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.adaptadores.ItemCardAlojAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Reserva;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.util.ArrayList;

import javax.xml.datatype.Duration;

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
        Consultas consultas = new Consultas();
        Request peticion = consultas.prepararQueryHibernate(Consultas.QUERY_DELETE,Reserva.class,new String[]{"id"},new String[]{String.valueOf(reserva.getId())});
        if(consultas.devolverResultadoPeticionBoolean(peticion))
        {
            Toast.makeText(this,getString(R.string.bookingDeleted), Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {
            Toast.makeText(this, getString(R.string.deleteBookingError), Toast.LENGTH_LONG).show();
        }
    }
}
