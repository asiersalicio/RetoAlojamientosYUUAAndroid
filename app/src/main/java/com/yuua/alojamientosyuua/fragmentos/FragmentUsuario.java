package com.yuua.alojamientosyuua.fragmentos;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yuua.alojamientosyuua.Base;
import com.yuua.alojamientosyuua.Login;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.adaptadores.RVAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUsuario extends Fragment {

    private Button boton;
    private View view;

    public FragmentUsuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_usuario, container, false);
        inicializar();
        anadirListeners();
        return view;
    }

    private void inicializar() {
        boton=view.findViewById(R.id.botonPrueba);
    }

    private void anadirListeners() {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
    }


    public void iniciarSesion()
    {
        Intent intent = new Intent(Base.contexto, Login.class);
        Base.contexto.startActivity(intent);
    }
}
