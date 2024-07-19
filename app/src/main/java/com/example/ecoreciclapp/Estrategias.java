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

        String[] strategies = {"Reciclaje de alimentos", "Reciclaje de orgánico", "Reciclaje de documentos",
                "Reciclaje de alimentos", "Separar elementos", "Crear campañas de reciclaje",
                "Grupos eco ambientales", "Cifras históricas", "Cómo monetizar el reciclaje"};

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