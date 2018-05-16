package com.apps.michelramirez.comes_3;

/**
 * Created by emmesis on 01/05/18.
 */

public class Item {
    private String title;

    //     Image name (Without extension)
    private String image;
    private float precio;
    private int id;

    public Item(String title, String image, float precio, int id) {
        this.title = title;
        this.image = image;
        this.precio = precio;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString()  {
        return this.title +" (Cantidad: "+ this.precio +")";
    }
}
