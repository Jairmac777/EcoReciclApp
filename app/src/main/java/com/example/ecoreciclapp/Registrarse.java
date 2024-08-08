package com.example.ecoreciclapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecoreciclapp.helpers.Encriptar;
import com.example.ecoreciclapp.helpers.FileManager;
import com.example.ecoreciclapp.helpers.Validacion;
import com.example.ecoreciclapp.modelos.Usuario;


public class Registrarse extends AppCompatActivity {

    Usuario     usuario;
    EditText    nombres;
    EditText    apellidos;
    EditText    celular;
    EditText    correo;
    EditText    contrasena;
    EditText    confirmar_contrasena;
    TextView    errores;
    Button      botonRegistrar;
    Button      botonContinuar;
    CheckBox    checkBoxTerminos;

    public enum TipoCampo { REQUERIDO, NO_REQUERIDO };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        nombres                 = findViewById(R.id.nombres);
        apellidos               = findViewById(R.id.apellidos);
        celular                 = findViewById(R.id.celular);
        correo                  = findViewById(R.id.correo);
        contrasena              = findViewById(R.id.contrasena);
        confirmar_contrasena    = findViewById(R.id.confirmarContrasena);
        errores                 = findViewById(R.id.errores);
        botonRegistrar          = findViewById(R.id.registrar);
        botonContinuar          = findViewById(R.id.continuar);
        checkBoxTerminos        = findViewById(R.id.checkBox2);

        //Ocultar contraseñas
        EditText passwordEditText = findViewById(R.id.contrasena);
        ImageButton passwordNoVisibility = findViewById(R.id.passwordNoVisibility);

        passwordNoVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordNoVisibility.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordNoVisibility.setImageResource(R.drawable.baseline_visibility_24);
                }
                //Mueve el cursor al final del texto
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });
        EditText confirmPasswordEditText = findViewById(R.id.confirmarContrasena);
        ImageButton passwordVisibilityOff = findViewById(R.id.passwordVisibilityOff);

        passwordVisibilityOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmPasswordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisibilityOff.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    confirmPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordVisibilityOff.setImageResource(R.drawable.baseline_visibility_24);
                }
                //Mueve el cursor al final del texto
                confirmPasswordEditText.setSelection(confirmPasswordEditText.getText().length());
            }
        });

        //Validar campos
        botonRegistrar.setOnClickListener(view -> {

            Validacion validacion = new Validacion();

            validacion.validateTextField   (nombres, "nombres", 3, 50, TipoCampo.REQUERIDO);
            validacion.validateTextField   (apellidos, "apellidos", 3, 50, TipoCampo.REQUERIDO);
            validacion.validateNumber      (celular, "teléfono", 10, 20, TipoCampo.NO_REQUERIDO);
            validacion.validateEmail       (correo);
            validacion.validatePassword    (contrasena, confirmar_contrasena, 8, 16);
            validacion.validateCheckbox    (checkBoxTerminos, TipoCampo.REQUERIDO);

            boolean errorResult = validacion.showErrorMessages(errores);

            //Si no hay errores de validación entonces registramos el usuario
            if(!errorResult){
                //Registrar usuario en base de datos
                usuario                 = new Usuario();
                usuario.nombres         = nombres.getText().toString();
                usuario.apellidos       = apellidos.getText().toString();
                usuario.correo          = correo.getText().toString();
                usuario.celular         = Long.parseLong(celular.getText().toString());
                usuario.contrasena      = Encriptar.encryptPassword(contrasena.getText().toString()); //Encriptamos la contraseña

                storageUserInDatabase();

            }

        });

        botonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        TextView textTerminos = findViewById(R.id.terminos);
        textTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void storageUserInDatabase(){

        FileManager fileManager = new FileManager(this);

        if(fileManager.insertNewUser(usuario)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}