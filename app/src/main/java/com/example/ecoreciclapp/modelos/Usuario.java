package com.example.ecoreciclapp.modelos;

import android.util.Log;
import com.google.gson.Gson;
import java.util.ArrayList;
import android.app.Application;

public class Usuario extends Application {
    public String   nombres;
    public String   apellidos;
    public String   imagenPerfil;
    public String   correo;
    public Long     celular;
    public String   contrasena;
    public String   genero;
    public int      edad;
    public ArrayList<Reciclaje> reciclajes;

    public ArrayList<Float> weigthStats; //Estadísticas de peso
    public ArrayList<Estadisticas> gainStats; //Estadísticas de ganancias

    public Usuario() {
        this.nombres            = "";
        this.apellidos          = "";
        this.imagenPerfil       = "";
        this.correo             = "";
        this.celular            = 0L;
        this.contrasena         = "";
        this.genero             = "";
        this.edad               = 0;
        this.reciclajes         = new ArrayList<Reciclaje>();
        this.weigthStats    = new ArrayList<Float>();
        this.gainStats      = new ArrayList<Estadisticas>();
    }
    public Usuario(String nombres, String apellidos, String imagenPerfil, String correo, Long celular, String contrasena, String genero, int edad) {
        this.nombres            = nombres;
        this.apellidos          = apellidos;
        this.imagenPerfil       = imagenPerfil;
        this.correo             = correo;
        this.celular            = celular;
        this.contrasena         = contrasena;
        this.genero             = genero;
        this.edad               = edad;
        this.reciclajes         = new ArrayList<Reciclaje>();
        this.weigthStats    = new ArrayList<Float>();
        this.gainStats      = new ArrayList<Estadisticas>();
    }

    public void setDefaultData(){
        this.nombres            = "";
        this.apellidos          = "";
        this.imagenPerfil       = "";
        this.correo             = "";
        this.celular            = 0L;
        this.contrasena         = "";
        this.genero             = "";
        this.edad               = 0;
        this.reciclajes         = new ArrayList<Reciclaje>();
        this.weigthStats    = new ArrayList<Float>();
        this.gainStats      = new ArrayList<Estadisticas>();
    }

    public void copyData(Usuario newData){
        this.nombres            = newData.nombres;
        this.apellidos          = newData.apellidos;
        this.imagenPerfil       = newData.imagenPerfil;
        this.correo             = newData.correo;
        this.celular            = newData.celular;
        this.contrasena         = newData.contrasena;
        this.genero             = newData.genero;
        this.edad               = newData.edad;
        this.reciclajes         = newData.reciclajes;
        this.weigthStats        = newData.weigthStats;
        this.gainStats          = newData.gainStats;
    }

    public void addReciclaje(Reciclaje reciclaje) {
        this.reciclajes.add(reciclaje);
    }

    public String showReciclajes(){

        String lista = "";

        for (Reciclaje r : this.reciclajes) {
            lista += "Fecha reciclaje: " + r.dateTime.toString() + "\n\n";
            lista += r.showMaterials();
        }

        return lista;
    }

    public String objetcToJSON (){

        String jsonData = new Gson().toJson(this);
        Log.e("msg", "Usuario para json: " + jsonData);

        return jsonData;

    }

    public void calculateWeigthStats(){

        ArrayList<Material> materialList = Reciclaje.getBaseMaterials();

        Log.e("msg", "Calculando Estadisticas de peso...");

        for(Material list : materialList) {
            Log.e("msg", "Buscando Material... " + list.name);

            float suma      = 0;
            int cantidad    = 0;
            float promedio  = 0;

            for (Reciclaje r : this.reciclajes) {
                Log.e("msg", "Reciclaje: " + r.dateTime.toString());

                for (Material m : r.materiales) {
                    Log.e("msg", "Material: " + m.name);

                    if(list.name.equals(m.name)) {
                        cantidad++;
                        suma += m.weight;
                        Log.e("msg", "(Encontrado) ---> " + m.weight);

                        break;
                    }
                }
                promedio = suma / cantidad;
            }
            this.weigthStats.add(promedio);
            Log.e("msg", "Promedio de " + list.name + ": " + promedio);
        }
    }

    public void calculateGainStats(){

        ArrayList<Material> materialList = Reciclaje.getBaseMaterials();

        Log.e("msg", "Calculando Estadisticas de ganancias...");

        for(Material list : materialList) {
            Log.e("msg", "Buscando Material... " + list.name);


            ArrayList<ReportesEstadisticas> gains     = new ArrayList<ReportesEstadisticas>();
            ArrayList<ReportesEstadisticas> weights   = new ArrayList<ReportesEstadisticas>();

            for (Reciclaje r : this.reciclajes) {
                Log.e("msg", "Reciclaje: " + r.dateTime);

                for (Material m : r.materiales) {
                    Log.e("msg", "Material: " + m.name);

                    if(list.name.equals(m.name)) {
                        gains.add(new ReportesEstadisticas(m.gain, r.dateTime));
                        weights.add(new ReportesEstadisticas(m.weight, r.dateTime));

                        Log.e("msg", "(Encontrado) ---> " + m.gain);
                        Log.e("msg", "(Encontrado) ---> " + m.weight);

                        break;
                    }
                }
            }

            Estadisticas stats = new Estadisticas(list.name, gains, weights);
            this.gainStats.add(stats);

            Log.e("msg", "Total Gain: " + stats.totalGain + " Total Weight: " + stats.totalWeight);

        }
    }

}
