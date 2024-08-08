package com.example.ecoreciclapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoreciclapp.helpers.FileManager;
import com.example.ecoreciclapp.modelos.Consejos;

import java.util.ArrayList;

public class ConsejosReciclaje extends AppCompatActivity {

    ArrayList<Consejos>     consejos;
    ArrayList<Consejos>     consejos2;
    RecyclerView            lista;
    RecyclerView            listaHorizontal;
    AdaptadorConsejos       adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejos);

        //Lista vertical
        consejos = new ArrayList<Consejos>();
        consejos.add(new Consejos("Reciclar vidrio", "Puedes reciclar vidrio de manera eficiente y sin contaminar el medio ambiente", "vidrio", "", false));
        consejos.add(new Consejos("Reciclar papel", "Puedes reciclar papel de manera eficiente y sin contaminar el medio ambiente", "papel", "", false));
        consejos.add(new Consejos("Reciclar cartón", "Puedes reciclar cartón de manera eficiente y sin contaminar el medio ambiente", "carton", "", false));
        consejos.add(new Consejos("Reciclar metal", "Puedes reciclar metal de manera eficiente y sin contaminar el medio ambiente", "metal", "", false));
        consejos.add(new Consejos("Reciclar orgánicos", "Puedes reciclar residuos orgánicos de manera eficiente y sin contaminar el medio ambiente", "organicos", "", false));
        consejos.add(new Consejos("Reciclar plástico", "Puedes reciclar plástico de manera eficiente y sin contaminar el medio ambiente", "plastico", "", false));
        FileManager fileManager = new FileManager(this);
        fileManager.insertAdvices(consejos);

        //Cargamos los consejos desde la base de datos
        ArrayList<Consejos> consejosReciclajeDB = fileManager.loadDevicesFromDatabase();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext());
        lista   = findViewById(R.id.lista);
        lista   .setLayoutManager(layoutManager);
        lista   .setHasFixedSize(true);
        adaptador = new AdaptadorConsejos(this, consejosReciclajeDB);
        lista   .setAdapter(adaptador);

        //Lista horizontal
        consejos2 = new ArrayList<Consejos>();
        consejos2.add(new Consejos("Tip para vidrio", "Mira este video para saber como reciclar vidrio", "", "reciclaje", false));
        consejos2.add(new Consejos("Tip para papel", "Mira este vitruedeo para saber como reciclar papel", "", "https://www.youtube.com/watch?v=YE33M1Xb0to", true));
        consejos2.add(new Consejos("Tip para cartón", "Mira este video para saber como reciclar cartón", "", "https://www.youtube.com/watch?v=04lrOKvIOxg", true));
        consejos2.add(new Consejos("Tip para metal", "Mira este video para saber como reciclar metal", "", "https://www.youtube.com/watch?v=9jpy7L3XVqE", true));
        consejos2.add(new Consejos("Tip para orgánicos", "Mira este video para saber como reciclar residuos orgánicos", "", "https://www.youtube.com/watch?v=uV--g9Oun6g", true));
        consejos2.add(new Consejos("Tip para plástico", "Mira este video para saber como reciclar plástico", "", "https://www.youtube.com/watch?v=ozo8e9hrKc4", true));

        fileManager.insertAdvices(consejos2);
        ArrayList<Consejos> consejosDB2 = fileManager.loadDevicesFromDatabase();
        LinearLayoutManager lmHorizontal
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        listaHorizontal = findViewById(R.id.listaHorizontal);
        listaHorizontal .setLayoutManager(lmHorizontal);
        listaHorizontal .setHasFixedSize(true);
        adaptador = new AdaptadorConsejos(this, consejosDB2);
        listaHorizontal .setAdapter(adaptador);

        Button botonRegresar = findViewById(R.id.botonRegresar);

        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });
    }

}