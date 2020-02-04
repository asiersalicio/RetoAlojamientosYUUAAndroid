package com.yuua.alojamientosyuua.fragmentos;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.activitys.Base;
import com.yuua.alojamientosyuua.activitys.Login;


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
    }

    private void anadirListeners() {
    }


    public void iniciarSesion()
    {
        Intent intent = new Intent(Base.contexto, Login.class);
        Base.contexto.startActivity(intent);
    }
}
