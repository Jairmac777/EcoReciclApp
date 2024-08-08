package com.example.ecoreciclapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecoreciclapp.helpers.Encriptar;
import com.example.ecoreciclapp.helpers.FileManager;
import com.example.ecoreciclapp.modelos.Usuario;


public class MainActivity extends AppCompatActivity {

    Usuario             usuario;
    EditText            correo;
    EditText            contrasena;
    Button              botonIngresa;
    CheckedTextView     botonRegistrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo            = findViewById(R.id.ingresaCorreo);
        contrasena        = findViewById(R.id.ingresaContrasena);
        botonIngresa      = findViewById(R.id.ingresa);
        botonRegistrate   = findViewById(R.id.registrate);

        EditText passwordEditText = findViewById(R.id.ingresaContrasena);
        ImageButton togglePasswordVisibilityOff = findViewById(R.id.togglePasswordVisibilityOff);

        togglePasswordVisibilityOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    togglePasswordVisibilityOff.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    togglePasswordVisibilityOff.setImageResource(R.drawable.baseline_visibility_24);
                }
                //Mueve el cursor al final del texto
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });

        botonIngresa.setOnClickListener(view -> {
            login();
        });

        botonRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registrarse.class);
                startActivity(intent);
            }
        });

    }

    public void login() {
        String correo = this.correo.getText().toString();
        String contrasena = this.contrasena.getText().toString();

        //Obtenemos el usuario GLOBAL de la aplicación
        usuario             = (Usuario) getApplicationContext();
        usuario             .setDefaultData();
        usuario.correo      = correo;
        usuario.contrasena  = Encriptar.encryptPassword(contrasena); //Encriptamos la constraseña ingresada

        if(!correo.isEmpty() && !contrasena.isEmpty()) {

            FileManager fileManager = new FileManager(this);

            //Validar credenciales en base de datos
            Usuario result = fileManager.findUserByEmailAndPassword(usuario);

            if (result != null) {

                usuario.copyData(result); //Actualizamos el usuario GLOBAL de la aplicación con los datos de la base de datos

                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);

                Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
        }
    }
}
