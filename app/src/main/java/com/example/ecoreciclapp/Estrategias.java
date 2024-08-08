package com.example.ecoreciclapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Estrategias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estrategias);

        String[] strategies = {"Grupos para reciclaje orgánico", "Grupos para reciclaje de papel",
                "Grupos para reciclaje de cartón", "Grupos para reciclaje de plástico", "Grupos para reciclaje de metal",
                "Grupos para reciclaje de vidrio", "Grupos Ecoambientales", "Cifras históricas", "Cómo monetizar el reciclaje"};

        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strategies);
        listView.setAdapter(adapter);

        Button botonRegresar = findViewById(R.id.btn_regresar);

        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });
    }
}