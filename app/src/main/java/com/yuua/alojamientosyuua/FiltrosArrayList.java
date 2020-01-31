package com.yuua.alojamientosyuua;

import com.google.gson.reflect.TypeToken;
import com.yuua.alojamientosyuua.entidades.Alojamiento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FiltrosArrayList {

    public static final int ASCENDETE=0,DESCENDENTE=1;
    public static final int TIPO=0;
    public static final int NOMBRE=0, PRECIO=1, CAPACIDAD=2;

    public static ArrayList<Alojamiento> ordenarArrayAlojamiento(ArrayList<Alojamiento> array, int variable, int orden)
    {
        switch (variable) {
            case NOMBRE:

                Comparator<Alojamiento> cmp = new Comparator<Alojamiento>() {
                    @Override
                    public int compare(Alojamiento o1, Alojamiento o2) {
                        if (o1.getNombre().charAt(0) < o2.getNombre().charAt(0)) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                };

                Collections.sort(array, cmp);

                break;
            case PRECIO:
                System.out.println();
                break;
            case CAPACIDAD:
                System.out.println();
                break;
        }
        return array;
    }

    public static ArrayList<Alojamiento> filtrarArrayAloj(ArrayList<Alojamiento> array, String variable, int filtro)
    {
        System.out.println("Se va filtrar por: " + variable);
        ArrayList<Alojamiento> alojamientos=new ArrayList<Alojamiento>();
        alojamientos.addAll(array);

        for(int i = array.size()-1;i>=0;i--)
        {
            Alojamiento alojamiento = alojamientos.get(i);
            if(filtro==TIPO)
            {
                System.out.println("Alojamiento: " + alojamiento.getNombre() + " Comprobar tipo:" + alojamiento.getTipo() + " si es " + variable);
                if(!alojamiento.getTipo().equals(variable))
                {
                    alojamientos.remove(i);
                }
            }

        }

        return alojamientos;
    }


}
