package com.apps.michelramirez.comes_3;

/**
 * Created by emmesis on 01/05/18.
 */

public class Item_orden {
    private String fecha;
    private String ubicacion;
    private int id;

    public Item_orden(String fecha, String ubicacion, int id) {
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    @Override
    public String toString()  {
        return this.fecha +" (Ubicación: "+ this.ubicacion +")";
    }
}

