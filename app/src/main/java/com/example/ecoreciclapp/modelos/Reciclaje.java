package com.example.ecoreciclapp.modelos;

import android.util.Log;

import com.google.gson.Gson;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Reciclaje {
    public String                   dateTime;
    public double                   gains; //ganancias
    public ArrayList<Material>      materiales;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Reciclaje() {
        this.dateTime       = LocalDateTime.now().toString(); //current date and time
        this.gains          = 0;
        this.materiales     = new ArrayList<Material>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Reciclaje(double gains, ArrayList<Material> materiales) {
        this.dateTime       = LocalDateTime.now().toString(); //current date and time
        this.gains          = gains;
        this.materiales     = materiales;
    }

    public static ArrayList<Material> getBaseMaterials(){

        ArrayList<Material>baseMaterials = new ArrayList<Material>();

        baseMaterials.add(new Material("Papel", 1000));
        baseMaterials.add(new Material("Cartón", 500));
        baseMaterials.add(new Material("Metal", 1500));
        baseMaterials.add(new Material("Plástico", 2000));
        baseMaterials.add(new Material("Vidrio", 800));
        baseMaterials.add(new Material("Orgánico", 1200));

        return baseMaterials;
    }

    public void addMaterial(Material material){
        this.materiales.add(material);
    }

    public String showMaterials(){

        String lista = "";
        for (Material m : materiales) {

            lista += "      > Material: " + m.name + "\n";
            lista += "      + Price: " + m.price + "\n";
            lista += "      + Weight: " + m.weight + "\n";
            lista += "      = Gain: $ " + m.gain + " COP\n";
            lista += "  ----------------------------------------\n";
        }

        calculateTotalGain();

        lista += "  Total gains: $ " + this.gains + " COP\n";
        lista += "================================\n\n";


        return lista;
    }

    public void deleteEmptyMaterials(){
        ArrayList<Material> materiales = new ArrayList<Material>();
        materiales.addAll(this.materiales);

        for (Material m : materiales) {
            if(m.weight == 0){
                this.materiales.remove(m);
            }
        }
    }

    public void calculateTotalGain(){
        this.gains = 0;

        for (Material m : materiales) {
            this.gains += m.gain;
        }
    }

    public String objetcToJSON (){

        String jsonData = new Gson().toJson(this);
        Log.e("msg", "Reciclaje to json: " + jsonData);

        return jsonData;

    }
}
