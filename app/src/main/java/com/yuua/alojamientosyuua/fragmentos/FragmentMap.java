package com.yuua.alojamientosyuua.fragmentos;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
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

                CameraPosition googlePlex = CameraPosition.builder().target(new LatLng(localizacion.getLatitud(),localizacion.getLongitud())).zoom(10).bearing(0).tilt(45).build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);

                mMap.addMarker(new MarkerOptions().position(new LatLng(localizacion.getLatitud(),localizacion.getLongitud())).title(nombreMarcador).snippet(localizacion.getDireccion()).icon(BitmapDescriptorFactory.fromResource(icono)));

            }
        });


        return rootView;
    }

    public void AbrirGMaps(View view)
    {
        Log.println(Log.DEBUG,"A","ENTRA");
        Uri gmmIntentUri = Uri.parse("geo:" + localizacion.getLatitud() + ", " + localizacion.getLongitud());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }



}
