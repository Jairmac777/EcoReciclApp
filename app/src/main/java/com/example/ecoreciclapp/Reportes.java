package com.example.ecoreciclapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ecoreciclapp.Menu;
import com.example.ecoreciclapp.modelos.Estadisticas;
import com.example.ecoreciclapp.modelos.Reciclaje;
import com.example.ecoreciclapp.modelos.ReportesEstadisticas;
import com.example.ecoreciclapp.modelos.Usuario;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Reportes extends AppCompatActivity {

    Usuario usuario;
    private BarChart    graficoBarras;
    private LineChart   graficoLineas;
    private PieChart    graficoTorta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        usuario = (Usuario) getApplicationContext();
        usuario.calculateWeigthStats(); //Calcular las estadisticas de lo reciclajes del usuario
        usuario.calculateGainStats(); //Calcular las estadisticas de lo reciclajes del usuario

        //Recuperamos el usuario GLOBAL de la aplicación
        TextView registrosUsuario = findViewById(R.id.reportes);
        usuario = (Usuario) getApplicationContext();
        registrosUsuario.setText("Estadísticas de " + usuario.nombres);

        graficoBarras   = findViewById(R.id.graficoBarras);
        graficoLineas   = findViewById(R.id.graficoLineas);
        graficoTorta    = findViewById(R.id.graficoTorta);

        graficoBarras   .getAxisRight().setEnabled(false); //No se muestra el eje derecho de la grafica
        graficoLineas   .getXAxis().setEnabled(false);
        graficoLineas   .getAxisLeft().setEnabled(false);
        graficoLineas   .getAxisRight().setEnabled(false);


        graficarPesos(); //grafico de barras
        graficarGanancias(); //grafico de lineas
        graficarPorcentajeGanancias(); //grafico de torta

        Button botonRegresar = findViewById(R.id.salir); //Para regresar al Menú principal

        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });
    }
    private void configurarLeyenda(Object grafico, Class clazz){

        Legend lenged = new Legend();

        if(clazz == BarChart.class) lenged  = ((BarChart)   grafico).getLegend(); //Muestra la leyenda de la grafica
        if(clazz == LineChart.class) lenged = ((LineChart)  grafico).getLegend(); //Muestra la leyenda de la grafica
        if(clazz == PieChart.class) lenged  = ((PieChart)   grafico).getLegend(); //Muestra la leyenda de la grafica

        lenged.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); //Alineacion vertical de la leyenda
        lenged.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); //Alineacion horizontal de la leyenda
        lenged.setOrientation(Legend.LegendOrientation.HORIZONTAL); //Orientacion de la leyenda
        lenged.setDrawInside(false); //No se muestra dentro de la grafica
        lenged.setForm(Legend.LegendForm.SQUARE); //Forma de la leyenda
        lenged.setFormSize(9f); //Tamaño de la forma de la leyenda
        lenged.setTextSize(14f); //Tamaño del texto de la leyenda
        lenged.setXEntrySpace(4f); //Espacio entre las
    }
    private void configurarEjes(Object grafico, Class clazz){

        //Eje X
        String[] labelsEjeX = new String[Reciclaje.getBaseMaterials().size()]; //Labels con los nombres de los materiales

        for (int i = 0; i < Reciclaje.getBaseMaterials().size(); i++) {
            labelsEjeX[i] = Reciclaje.getBaseMaterials().get(i).name;
        }

        XAxis ejeX = new XAxis();

        if(clazz == BarChart.class) ejeX  = ((BarChart)   grafico).getXAxis(); //Obtenemos el eje X del gráfico
        if(clazz == LineChart.class) ejeX = ((LineChart)  grafico).getXAxis(); //Obtenemos el eje X del gráfico
        if(clazz == PieChart.class) ejeX  = ((PieChart)   grafico).getXAxis(); //Obtenemos el eje X del gráfico

        ejeX        .setGranularity(1f);
        ejeX        .setGranularityEnabled(true);
        ejeX        .setCenterAxisLabels(false); //Centra los labels
        ejeX        .setDrawGridLines(false); //No se muestra la grilla de las barras
        ejeX        .setPosition(XAxis.XAxisPosition.BOTTOM);
        ejeX        .setLabelCount(labelsEjeX.length - 1);
        ejeX        .setTextSize(12f);
        //ejeX      .setLabelRotationAngle(-30f);
        ejeX        .setValueFormatter(new IndexAxisValueFormatter(labelsEjeX));

        //EjeY
        YAxis ejeY = new YAxis();

        if(clazz == BarChart.class) ejeY  = ((BarChart)   grafico).getAxisLeft(); //Obtenemos el eje Y del gráfico
        if(clazz == LineChart.class) ejeY = ((LineChart)  grafico).getAxisLeft(); //Obtenemos el eje Y del gráfico

        ejeY        .setValueFormatter(new LargeValueFormatter()); //Formato de los valores del eje Y
        ejeY        .setDrawGridLines(true); //Se muestra la grilla de las barras
        ejeY        .setSpaceTop(10f); //Espacio entre la linea y el eje
        ejeY        .setAxisMinimum(0f); //Valor minimo del eje Y
    }
    private void graficarPesos() {

        //configuramos el gráfico
        graficoBarras.setDrawBarShadow(false); //Muestra la sombra de las barras
        graficoBarras.setDrawValueAboveBar(true); //Muestra el valor encima de la barra
        graficoBarras.getDescription().setEnabled(false); //Muestra la descripcion de la grafica
        graficoBarras.setMaxVisibleValueCount(60); //Muestra solo hasta 60 barras
        graficoBarras.setPinchZoom(true); //No se puede hacer zoom
        graficoBarras.setDrawGridBackground(false); //No se muestra el fondo de la grafica

        configurarLeyenda(graficoBarras, BarChart.class);
        configurarEjes(graficoBarras, BarChart.class);

        ArrayList<BarEntry> barras = new ArrayList<>();

        int count = 0;
        for (float promedio : usuario.weigthStats) {
            barras.add(new BarEntry(count, promedio));
            count++;
        }

        BarDataSet adapter = new BarDataSet(barras, "Ganacia en $ por material");
        adapter.setDrawIcons(false); //Mostrar iconos

        //adapter.setColors(new int[] { R.color.black, R.color.verde_claro, R.color.verde_transparente }, getApplicationContext());

        adapter.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData config = new BarData(adapter); //Crear los datos de la grafica
        config.setBarWidth(0.9f); //Ancho de las barras
        config.setValueTextSize(14f); //Tamaño del texto de los valores

        graficoBarras.setData(config); //Asignar los datos a la grafica
        graficoBarras.setFitBars(false); //Ajuste automatico de las barras
        graficoBarras.invalidate(); //Actualizar la grafica

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {

                DecimalFormat df = new DecimalFormat("#.#kg");
                return df.format(value);
            }
        };

        config.setValueFormatter(formatter);
    }
    private void graficarGanancias() {

        graficoLineas.getDescription().setEnabled(false); //Muestra la descripcion de la grafica
        graficoLineas.setMaxVisibleValueCount(60); //Muestra solo hasta 60 barras
        graficoLineas.setPinchZoom(true); //No se puede hacer zoom
        graficoLineas.setDrawGridBackground(false);

        configurarLeyenda(graficoLineas, LineChart.class);
        //configurarEjes(graficoLineas, LineChart.class);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        int i = 0;
        for(Estadisticas stats : usuario.gainStats) {

            ArrayList<Entry> lineas = new ArrayList<>();

            int count = 0;
            for (ReportesEstadisticas g : stats.gains) {
                lineas.add(new Entry(count, g.value.floatValue()));

                Log.e("msg", "Material: " + stats.materialName + " x: " + count + " y: " + g.value.floatValue());

                count++;
            }

            LineDataSet adapter = new LineDataSet(lineas, stats.materialName);
            adapter.setDrawIcons(false); //Mostrar iconos

            adapter.setColor(ColorTemplate.MATERIAL_COLORS[i]);
            //adapter.setCircleColor(Color.DKGRAY);
            adapter.setLineWidth(4f);
            adapter.setCircleRadius(6f);
            adapter.setValueTextSize(9f);
            adapter.setFormLineWidth(1f);

            dataSets.add(adapter);

            if(i < ColorTemplate.MATERIAL_COLORS.length - 1) i++;
            else i = 0;
        }


        LineData config = new LineData(dataSets); //Crear los datos de la grafica
        config.setValueTextSize(14f); //Tamaño del texto de los valores

        graficoLineas.setData(config); //Asignar los datos a la grafica
        graficoLineas.invalidate(); //Actualizar la grafica

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {

                DecimalFormat df = new DecimalFormat("#.#k");
                return  df.format(value/1000.0);
            }
        };

        config.setValueFormatter(formatter);
        YAxis left = graficoLineas.getAxisLeft();
        left.setValueFormatter(formatter);
    }
    private void graficarPorcentajeGanancias() {

        graficoTorta.setUsePercentValues(true);
        graficoTorta.getDescription().setEnabled(false); //Muestra la descripcion de la grafica
        configurarLeyenda(graficoTorta, PieChart.class);
        //configurarEjes(graficoLineas, LineChart.class);

        ArrayList<PieEntry> porcentajes = new ArrayList<>();

        int count = 0;
        for (Estadisticas stats : usuario.gainStats) {
            porcentajes.add(new PieEntry((float) stats.totalGain, stats.materialName));

            Log.e("msg", "Material: " + stats.materialName + " Total Ganancia: " + stats.totalGain);

            count++;
        }

        PieDataSet adapter = new PieDataSet(porcentajes,  " Porcentaje de ganancias");
        adapter.setDrawIcons(false); //Mostrar iconos

        adapter.setColors(ColorTemplate.MATERIAL_COLORS);
        adapter.setValueTextSize(9f);
        adapter.setFormLineWidth(1f);

        PieData config = new PieData(adapter); //Crear los datos de la grafica
        config.setValueTextSize(14f); //Tamaño del texto de los valores

        graficoTorta.setData(config); //Asignar los datos a la grafica
        graficoTorta.invalidate(); //Actualizar la grafica

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {

                DecimalFormat df = new DecimalFormat("#.#%");
                return df.format(value/100.0);
            }
        };

        config.setValueFormatter(formatter);
    }

}