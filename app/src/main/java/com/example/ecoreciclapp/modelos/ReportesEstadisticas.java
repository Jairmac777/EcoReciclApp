package com.example.ecoreciclapp.modelos;

import android.os.Build;

import java.time.LocalDateTime;

public class ReportesEstadisticas {
    public Double           value;
    public LocalDateTime date;

    public ReportesEstadisticas() {
        this.value  = 0.0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.date   = LocalDateTime.now();
        }
    }

    public ReportesEstadisticas(Double value, String date) {
        this.value  = value;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.date   = LocalDateTime.parse(date);
        }
    }
}
