package com.yuua.alojamientosyuua.fragmentos;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Localizacion;

public class FragmentMap extends Fragment {

    private Localizacion localizacion;
    private String nombreMarcador;
    private int icono;

    public FragmentMap(Localizacion localizacion, String nombreMarcador, int icono) {
        this.localizacion = localizacion;
        this.nombreMarcador = nombreMarcador;
        this.icono=icono;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.2726747,-2.5048312), 9));

                CameraPosition googlePlex = CameraPosition.builder().target(new LatLng(localizacion.getLatitud(),localizacion.getLongitud())).zoom(12).bearing(0).tilt(45).build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);



                Marker marcador = mMap.addMarker(new MarkerOptions().position(new LatLng(localizacion.getLatitud(),localizacion.getLongitud())).title(nombreMarcador).snippet(localizacion.getDireccion()).icon(BitmapDescriptorFactory.fromResource(icono)));
                marcador.showInfoWindow();

            }
        });

        ConstraintLayout boton = rootView.findViewById(R.id.constraintMap);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirGMaps();
            }
        });


        return rootView;
    }

    public void AbrirGMaps()
    {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/search/?api=1&query=" + nombreMarcador + " " + localizacion.getDireccion() + ", " + localizacion.getCodigoPostal()));
        startActivity(intent);
    }



}
