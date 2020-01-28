package com.yuua.alojamientosyuua.activitys;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuua.alojamientosyuua.Sistema;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.adaptadores.ItemSearchResultAdapter;
import com.yuua.alojamientosyuua.entidades.Alojamiento;
import com.yuua.alojamientosyuua.entidades.Municipio;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.util.ArrayList;

public class BuscadorAlojamientos extends AppCompatActivity {

    private RecyclerView rv;
    private TextView campoBusqueda;
    private boolean textoCambiado = false;
    private ArrayList<Object> items = new ArrayList<Object>();
    private ProgressBar pb;
    private Context context;
    private boolean salir;
    private ItemSearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_alojamientos);
        context = this;
        rv = findViewById(R.id.recyclerViewBusqueda);
        campoBusqueda = findViewById(R.id.campoBusqueda);
        pb = findViewById(R.id.progressBarBusqueda);


        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        adapter = new ItemSearchResultAdapter(context, items);
        rv.setAdapter(adapter);

        getSupportActionBar().hide();

        campoBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textoCambiado = true;
                activarProgressbar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (!Sistema.SIMULACIONALOJAMIENTOS) {
            iniciarBuscador();
        } else {
            final ArrayList<Object> arrayPruebas = new ArrayList<Object>();
            arrayPruebas.addAll(Sistema.getDebugMunicipios());
            arrayPruebas.addAll(Sistema.getDebugAlojamientos());
            new Thread(new HiloBusqueda(items, adapter) {
                @Override
                public void run() {
                    super.run();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.itemsAdaptador.clear();
                            adapter.itemsAdaptador.addAll(arrayPruebas);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
            ).start();
        }

        campoBusqueda.setText(getIntent().getExtras().getString("busqueda"));
    }





    private void iniciarBuscador() {
        new Thread(new HiloBusqueda(items, adapter) {
            @Override
            public void run() {
                String ultimoTexto = "";
                while (!salir) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    ;


                    if (textoCambiado && campoBusqueda.getText().length() > 0) {
                        activarProgressbar();
                        textoCambiado = false;

                        System.out.println("Intentando actualizar lista de alojamientos...");

                        final ArrayList<Object> nuevoArray;
                        nuevoArray=buscarPorTexto(campoBusqueda.getText().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                    adapter.itemsAdaptador.clear();
                                    adapter.itemsAdaptador.addAll(nuevoArray);
                                    adapter.notifyDataSetChanged();
                                    System.out.println("Actualizando lista de alojamientos busqueda, con: " + nuevoArray.size());

                            }
                        });
                    }
                    desactivarProgresBar();
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        salir = true;
        finish();
    }

    private ArrayList<Object> buscarPorTexto(String texto) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        System.out.println("Buscar en BD por: " + texto);
        Consultas consultar = new Consultas();
        Request peticionMunicipio = consultar.prepararQueryHibernate(Consultas.QUERY_CON_CONDICIONES_LIKE, Municipio.class, new String[]{"nombre"}, new String[]{texto});
        arrayList.addAll((ArrayList<Object>) consultar.devolverResultadoPeticion(peticionMunicipio, Municipio.class));


        Request peticionAlojamientos=consultar.prepararQueryHibernate(Consultas.QUERY_CON_CONDICIONES_LIKE,Alojamiento.class,new String[]{"nombre"},new String[]{texto});

        ArrayList<Object> resultadosBusqueda = (ArrayList<Object>)consultar.devolverResultadoPeticion(peticionAlojamientos,Alojamiento.class);
        if(resultadosBusqueda!=null)
        {
            arrayList.addAll(resultadosBusqueda);
        }

        return arrayList;
    }

    public void borrarBusqueda(View view)
    {
        campoBusqueda.setText("");
    }

    private void activarProgressbar()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pb.setVisibility(View.VISIBLE);
            }
        });
    }

    private void desactivarProgresBar()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pb.setVisibility(View.INVISIBLE);
            }
        });
    }
}
