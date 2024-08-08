package com.example.ecoreciclapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecoreciclapp.modelos.Usuario;

public class Menu extends AppCompatActivity {

    Usuario     usuario;
    TextView    registrosUsuario;
    TextView    correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        registrosUsuario     = findViewById(R.id.tvUsuario);
        correo               = findViewById(R.id.tvEmail);

        usuario = (Usuario) getApplicationContext();
        registrosUsuario.setText("Hola " + usuario.nombres);
        correo.setText(usuario.correo);

        Button botonRegistrarReciclaje  = findViewById(R.id.btn_home);
        Button botonRegistrosReciclaje  = findViewById(R.id.btn_setting);
        Button botonEstadisticas        = findViewById(R.id.btn_reports);
        Button botonEstrategias         = findViewById(R.id.btn_strategies);
        Button botonConsejos            = findViewById(R.id.btn_tips);
        Button botonSalir               = findViewById(R.id.btn_regresar);

        botonRegistrarReciclaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrarReciclaje.class);
                startActivity(intent);
            }
        });
        botonRegistrosReciclaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrosReciclaje.class);
                startActivity(intent);
            }
        });
        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reportes.class);
                startActivity(intent);
            }
        });
        botonEstrategias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Estrategias.class);
                startActivity(intent);
            }
        });
        botonConsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConsejosReciclaje.class);
                startActivity(intent);
            }
        });
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
