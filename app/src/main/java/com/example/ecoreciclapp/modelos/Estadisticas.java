package com.example.ecoreciclapp.modelos;

import java.util.ArrayList;

public class Estadisticas {
    public String                           materialName;
    public ArrayList<ReportesEstadisticas>  gains;
    public ArrayList<ReportesEstadisticas>  weights;
    public double                           totalGain;
    public double                           totalWeight;

    public Estadisticas() {
        this.materialName   = "";
        this.gains          = new ArrayList<ReportesEstadisticas>();
        this.weights        = new ArrayList<ReportesEstadisticas>();
        this.totalGain      = 0;
        this.totalWeight    = 0;
    }

    public Estadisticas(String materialName, ArrayList<ReportesEstadisticas> gains, ArrayList<ReportesEstadisticas> weights) {
        this.materialName   = materialName;
        this.gains          = gains;
        this.weights        = weights;

        calculateTotalGain();
        calculateTotalWeight();
    }

    public Estadisticas(String materialName, ArrayList<ReportesEstadisticas> gains, ArrayList<ReportesEstadisticas> weights, double totalGain, double totalWeight) {
        this.materialName   = materialName;
        this.gains          = gains;
        this.weights        = weights;
        this.totalGain      = totalGain;
        this.totalWeight    = totalWeight;

        calculateTotalGain();
        calculateTotalWeight();
    }

    public void calculateTotalGain(){
        for (ReportesEstadisticas value : gains) {
            this.totalGain += value.value;
        }
    }

    public void calculateTotalWeight(){
        for (ReportesEstadisticas value : weights) {
            this.totalWeight += value.value;
        }
    }
}
