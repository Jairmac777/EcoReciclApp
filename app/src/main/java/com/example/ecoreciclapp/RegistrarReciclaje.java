package com.example.ecoreciclapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoreciclapp.helpers.FileManager;
import com.example.ecoreciclapp.modelos.Material;
import com.example.ecoreciclapp.modelos.Reciclaje;
import com.example.ecoreciclapp.modelos.Usuario;

import java.util.ArrayList;

public class RegistrarReciclaje extends AppCompatActivity {

    Usuario                      usuario;
    RecyclerView                 lista;
    AdaptadorMaterialReciclaje   adaptador;
    ArrayList<Material>          baseMateriales;
    Button                       botonRegistrar;
    Button                       botonReiniciar;
    Button                       botonRegresarMenu;
    TextView                     totalGanancia;
    Reciclaje                    reciclaje;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_reciclaje);

        baseMateriales = new ArrayList<Material>();
        baseMateriales.add(new Material("Papel", 1000));
        baseMateriales.add(new Material("Cartón", 500));
        baseMateriales.add(new Material("Metal", 1500));
        baseMateriales.add(new Material("Plástico", 2000));
        baseMateriales.add(new Material("Vidrio", 800));
        baseMateriales.add(new Material("Orgánico", 1200));

        //Recuperamos el usuario GLOBAL de la aplicación
        usuario = ((Usuario)getApplicationContext());

        reciclaje = new Reciclaje();
        reciclaje.materiales = baseMateriales;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        lista                   = findViewById(R.id.lista);
        botonRegistrar          = findViewById(R.id.botonRegistrar);
        botonReiniciar          = findViewById(R.id.botonReiniciar);
        botonRegresarMenu       = findViewById(R.id.botonRecords);
        totalGanancia           = findViewById(R.id.totalGains);
        lista                   .setLayoutManager(layoutManager);
        lista                   .setHasFixedSize(true);

        adaptador = new AdaptadorMaterialReciclaje(this, reciclaje.materiales);
        lista.setAdapter(adaptador);

        botonRegistrar.setOnClickListener(view -> {
            reciclaje.calculateTotalGain();
            reciclaje.deleteEmptyMaterials();

            //Toast.makeText(this, "Total gains: $ " + recycling.gains + " COP", Toast.LENGTH_SHORT).show();

            //Agregamos el reciclaje al usuario
            usuario.addReciclaje(reciclaje);

            //Guardamos los cambios en la base de datos
            storageRecyclingInDatabase();

        });

        botonReiniciar.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistrarReciclaje.class);
            startActivity(intent);

            Toast.makeText(this, "Ya puedes registrar otro reciclaje", Toast.LENGTH_SHORT).show();
        });

        botonRegresarMenu.setOnClickListener(view -> {
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        });

    }
    private void storageRecyclingInDatabase(){

        FileManager fileManager = new FileManager(this);
        fileManager.insertNewRecycling(usuario);
    }
}