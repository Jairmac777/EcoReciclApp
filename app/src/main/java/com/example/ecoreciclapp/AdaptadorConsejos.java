package com.example.ecoreciclapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoreciclapp.modelos.Consejos;

import java.util.ArrayList;

public class AdaptadorConsejos extends RecyclerView.Adapter<AdaptadorConsejos.MyViewHolder>{

    ArrayList<Consejos> adviceList;
    Context context;

    public AdaptadorConsejos(Context context, ArrayList<Consejos> adviceList) {
        this.context = context;
        this.adviceList = adviceList;
    }

    @NonNull
    @Override
    public AdaptadorConsejos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_consejos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorConsejos.MyViewHolder holder, int position) {

        Consejos consejos = adviceList.get(position);

        holder.title.setText(consejos.title);
        holder.description.setText(consejos.description);

        if(!consejos.url.isEmpty()) {
            holder.videoLocal.setVisibility(View.VISIBLE);
            holder.image.setVisibility(View.GONE);

            if(consejos.isLink){
                showVideoOnline(holder, consejos.url);
            }
            else {
                int idVideo = context.getResources().getIdentifier(consejos.url, "raw", context.getPackageName());
                String carpetaRecursos = "android.resource://" + context.getPackageName() + "/";
                Uri uri = Uri.parse(carpetaRecursos + idVideo);

                holder.videoLocal.setVideoURI(uri);

                MediaController mediaController = new MediaController(context);
                mediaController.setAnchorView(holder.videoLocal);
                holder.videoLocal.setMediaController(mediaController);
            }
        }
        else {
            int idImage = context.getResources().getIdentifier(consejos.image, "mipmap", context.getPackageName());
            holder.image.setImageResource(idImage);
        }

    }

    public void showVideoOnline(AdaptadorConsejos.MyViewHolder holder, String linkVideo){

        holder.videoOnline.setVisibility(View.VISIBLE);
        holder.videoLocal.setVisibility(View.GONE);
        holder.image.setVisibility(View.GONE);

        String iframeHtml = "<iframe width=\"100%\" height=\"100%\" src=\"" + linkVideo + "\" title=\"YouTube video player\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" " +
                "referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";

        holder.videoOnline.loadData(iframeHtml, "text/html", "utf-8");
        holder.videoOnline.getSettings().setJavaScriptEnabled(true);
        holder.videoOnline.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public int getItemCount() { return adviceList.size();}

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView   image;
        TextView    title;
        TextView    description;
        VideoView   videoLocal;
        WebView     videoOnline;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image       = itemView.findViewById(R.id.image);
            title       = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            videoLocal  = itemView.findViewById(R.id.videoLocal);
            videoOnline = itemView.findViewById(R.id.videoOnline);
        }
    }


}
