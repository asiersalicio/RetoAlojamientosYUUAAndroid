package com.yuua.alojamientosyuua;

import java.io.Serializable;

public class ObjetoGenerico implements Serializable {

    private Object object;

    public ObjetoGenerico(Object object)
    {
        this.object=object;
    }

    public Object getObject()
    {
        return object;
    }


}
