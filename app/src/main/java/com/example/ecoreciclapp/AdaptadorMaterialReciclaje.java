package com.example.ecoreciclapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecoreciclapp.modelos.Material;
import java.util.ArrayList;

public class AdaptadorMaterialReciclaje extends RecyclerView.Adapter<AdaptadorMaterialReciclaje.MyViewHolder> {

    public RegistrarReciclaje registrarReciclaje;
    public ArrayList<Material> materiales;

    public AdaptadorMaterialReciclaje(RegistrarReciclaje registrarReciclaje, ArrayList<Material> materiales){
        this.registrarReciclaje = registrarReciclaje;
        this.materiales = materiales;
    }

    @NonNull
    @Override
    public AdaptadorMaterialReciclaje.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view   = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.registrar_material_reciclaje, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMaterialReciclaje.MyViewHolder holder, int position) {

        Material material = materiales.get(position);
        holder.nombreMaterial.setText(material.name);
        holder.price.setText(material.price + "");
        holder.weight.setText(material.weight + "");
        material.calculateGain(material.price);
        holder.gain.setText(material.gain + "");
        holder.price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String value = holder.price.getText().toString();

                if(!value.isEmpty()) {

                    double newPrice = Double.parseDouble(value);
                    material.calculateGain(newPrice);
                    holder.gain.setText(material.gain + "");

                    registrarReciclaje.reciclaje.calculateTotalGain();
                    registrarReciclaje.totalGanancia.setText("Total ganancia: $ " + registrarReciclaje.reciclaje.gains + " COP");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        holder.weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String value = holder.weight.getText().toString();

                if(!value.isEmpty()) {

                    double newWeight = Double.parseDouble(value);
                    material.weight = newWeight;
                    material.calculateGain();
                    holder.gain.setText(material.gain + "");

                    registrarReciclaje.reciclaje.calculateTotalGain();
                    registrarReciclaje.totalGanancia.setText("Total ganancia: $ " + registrarReciclaje.reciclaje.gains + " COP");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}

        });
    }

    @Override
    public int getItemCount() {
        return this.materiales.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nombreMaterial;
        TextView price;
        TextView weight;
        TextView gain;

        public MyViewHolder(@NonNull View item) {
            super(item);

            nombreMaterial  = item.findViewById(R.id.nombreMaterial);
            price           = item.findViewById(R.id.price);
            weight          = item.findViewById(R.id.weight);
            gain            = item.findViewById(R.id.gain);
        }
    }
}
