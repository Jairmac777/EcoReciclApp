package com.example.ecoreciclapp.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.ecoreciclapp.modelos.Consejos;
import com.example.ecoreciclapp.modelos.Reciclaje;
import com.example.ecoreciclapp.modelos.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    private Context     context;
    public File         userFile; //Almacenar infomación de los usuarios
    public File         advicesFile; //AL macenar los consejos

    public FileManager(Context context) {
        this.context    = context;
        userFile        = loadFileOrCreate("db_user");
        advicesFile     = loadFileOrCreate("db_advices");
    }

    private File loadFileOrCreate(String fileName) {

        File file = new File(context.getFilesDir(), fileName + ".txt");

        if (file.exists()) {
            Log.e("msg", "El archivo " + file.getName() + " ya existe en: " + file.getAbsolutePath());
            return file;

        } else {

            try {
                file.createNewFile();
                Log.e("msg", "El archivo " + file.getName() + " fue creado exitosamente en: " + file.getAbsolutePath());

                return file;

            } catch (Exception e) {
                Log.e("msg", "Error al crear el archivo " + file.getName(), e);
                return null;
            }
        }
    }

    public boolean insertNewUser(Usuario usuario) {

        try {
            //Si el email no existe en el archivo entonces lo insertamos
            if (!validateIfUserExist(usuario)) {

                BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true));

                writer.write(usuario.objetcToJSON());
                writer.newLine();
                writer.close();

                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show();

                Log.e("msg", "Se ha insertado el nuevo usuario en " + userFile.getName() + " exitosamente");

                return true;

            } else {
                Toast.makeText(context, "El usuario " + usuario.correo + " ya está registrado", Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Toast.makeText(context, "Error al escribir en el archivo " + userFile.getName() + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return false;
    }

    public boolean validateIfUserExist(Usuario usuario) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFile));

            String data;
            while ((data = reader.readLine()) != null) {

                //Convertimos el dato leido en un objeto de tipo User
                Usuario dbUser = new Gson().fromJson(data, Usuario.class);

                //Si el email ya existe retornamos true
                if (dbUser.correo.equals(usuario.correo)) {
                    return true;
                }
            }
            reader.close();

        } catch (IOException e) {
            Toast.makeText(context, "Error al leer el archivo " + userFile.getName() + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Si llega hasta acá es porque el email no existe en la base de datos
        return false;
    }

    public Usuario findUserByEmailAndPassword(Usuario usuario) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFile));

            String data;
            while ((data = reader.readLine()) != null) {

                //Convertimos el dato leido en un objeto de tipo User
                Usuario dbUser = new Gson().fromJson(data, Usuario.class);

                Log.e("msg", "Email: " + dbUser.correo + ", Password: " + dbUser.contrasena);
                Log.e("msg", "Email: " + usuario.correo + ", Password: " + usuario.contrasena);

                //Si las credenciles coinciden con los registros en la base de datos retornamos true
                if (dbUser.correo.equals(usuario.correo) && dbUser.contrasena.equals(usuario.contrasena)) {
                    Log.e("msg", "Email: " + usuario.contrasena + ", Password: " + usuario.contrasena + " <- Correct!");

                    return dbUser;
                }
            }

            reader.close();

        } catch (IOException e) {
            Toast.makeText(context, "Error al leer el archivo " + userFile.getName() + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Si llega hasta acá es porque los datos del usuario no coinciden con los registros de la base de datos
        return null;
    }

    public void insertNewRecycling(Usuario usuario) {

        try {

            ArrayList<String> newFileData = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(new FileReader(userFile));

            String data;
            while ((data = reader.readLine()) != null) {

                //Convertimos el dato leido en un objeto de tipo User
                Usuario dbUser = new Gson().fromJson(data, Usuario.class);

                //Buscamos el usuario actual en la base de datos
                if (dbUser.correo.equals(usuario.correo)) {

                    //Actualizamos sus registros de reciclaje de la base de datos
                    dbUser.reciclajes = usuario.reciclajes;
                }

                newFileData.add(new Gson().toJson(dbUser));
            }

            reader.close();

            //Guardamos los datos en la base de datos
            BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, false)); //false para sobreescribir el archivo

            //Insertamos los nuevos datos en el archivo
            for (String newData : newFileData) {
                writer.write(newData);
                writer.newLine();
            }

            writer.close();

            Toast.makeText(context, "El reciclaje se ha registrado con éxito", Toast.LENGTH_LONG).show();

            Log.e("msg", "Se ha insertado el nuevo reciclaje en " + userFile.getName() + " exitosamente");

        } catch (IOException e) {
            Toast.makeText(context, "Error al escribir en el archivo " + userFile.getName() + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public boolean readRecyclingsFromUser(Usuario usuario) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFile));

            String data;
            while ((data = reader.readLine()) != null) {

                //Convertimos el dato leido en un objeto de tipo User
                Usuario dbUser = new Gson().fromJson(data, Usuario.class);

                //Si encontramos los registros el usuario los almacenamos en el objeto user
                if (dbUser.correo.equals(usuario.correo)) {
                    usuario.reciclajes = dbUser.reciclajes;

                    return true;
                }
            }
            reader.close();

        } catch (IOException e) {
            Toast.makeText(context, "Error al leer el archivo " + userFile.getName() + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Si llega hasta acá es porque el usuario no tiene ningún registro de reciclaje
        return false;
    }
    public void insertAdvices(ArrayList<Consejos> adviceList) {

        try {
            //Guardamos los consejos en la base de datos
            BufferedWriter writer = new BufferedWriter(new FileWriter(advicesFile, false)); //false para sobreescribir el archivo

            for (Consejos a: adviceList) {
                writer.write(a.objetcToJSON()); //Insertamos el consejo en formato JSON dentro del archivo de texto
                writer.newLine();
            }

            writer.close();

            Log.e("msg", "Los consejos se han insertado en " + advicesFile.getName() + " exitosamente");

        } catch (IOException e) {
            Toast.makeText(context, "Error al escribir en el archivo " + advicesFile.getName() + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public ArrayList<Consejos> loadDevicesFromDatabase() {

        ArrayList<Consejos> adviceList = new ArrayList<Consejos>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(advicesFile));

            String data;
            while ((data = reader.readLine()) != null) {

                Log.e("msg", data);

                //Convertimos el dato leido en un objeto de tipo Advice
                Consejos dbAdvice = new Gson().fromJson(data, Consejos.class);

                adviceList.add(dbAdvice);
            }

            reader.close();

            Log.e("msg", "Se han cargado los concejos desde " + advicesFile.getName() + " exitosamente");

            return adviceList;

        } catch (IOException e) {
            Toast.makeText(context, "Error leer los concejos en el archivo " + advicesFile.getName() + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return null;

    }
}
