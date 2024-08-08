package com.example.ecoreciclapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ecoreciclapp.helpers.FileManager;
import com.example.ecoreciclapp.modelos.Usuario;

public class RegistrosReciclaje extends AppCompatActivity {

    Usuario     usuario;
    TextView    registrosUsuario;
    TextView    registros;
    Button      botonRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros_reciclaje);

        registrosUsuario        = findViewById(R.id.userRecords);
        registros               = findViewById(R.id.records);
        botonRegresar           = findViewById(R.id.botonRegresar);

        botonRegresar.setOnClickListener(view -> {
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        });

        //Recuperamos el usuario GLOBAL de la aplicación
        usuario = (Usuario) getApplicationContext();
        registrosUsuario.setText("Registros de " + usuario.nombres);

        getRecyclingsFromDataBase();

    }

    private void getRecyclingsFromDataBase(){

        FileManager fileManager = new FileManager(this);

        if(fileManager.readRecyclingsFromUser(usuario)){

            String recordsList = usuario.showReciclajes();
            registros.setText(recordsList);

            Toast.makeText(this, "Se han recuperado los registros de reciclaje", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "No se encontraron registros de reciclaje", Toast.LENGTH_LONG).show();
        }


    }
}