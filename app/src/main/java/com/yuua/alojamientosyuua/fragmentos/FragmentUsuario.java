package com.yuua.alojamientosyuua.fragmentos;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuua.alojamientosyuua.Base;
import com.yuua.alojamientosyuua.Login;
import com.yuua.alojamientosyuua.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUsuario extends Fragment {


    public FragmentUsuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario, container, false);
    }


    public void aaaa(View view)
    {
        Intent intent = new Intent(Base.contexto, Login.class);
        Base.contexto.startActivity(intent);
    }
}
