package com.example.ecoreciclapp.modelos;

import android.util.Log;

import com.google.gson.Gson;

public class Consejos {
    public String   title; //Aprovecha mejor el papel
    public String   description; //Puedes reciclar papel...
    public String   image; //Imagen a mostrar
    public String   url; //Por ejemplo un video de youtube
    public boolean  isLink; //Por ejemplo un video de youtube

    public Consejos() {
        this.title          = "";
        this.description    = "";
        this.image          = "";
        this.url            = "";
        this.isLink         = false;
    }

    public Consejos(String title, String description, String image, String url, boolean isLink) {
        this.title          = title;
        this.description    = description;
        this.image          = image;
        this.url            = url;
        this.isLink         = isLink;
    }
    public String objetcToJSON (){

        String jsonData = new Gson().toJson(this);
        Log.e("msg", "User to json: " + jsonData);

        return jsonData;

    }
}
