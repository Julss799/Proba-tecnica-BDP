package com.example.provatecnicabdp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.provatecnicabdp.LlistaCines;
import com.example.provatecnicabdp.R;
import com.example.provatecnicabdp.datos.Cinema;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder> {

    private List<Cinema> cinemas;
    private Context context;

    public CinemaAdapter(List<Cinema> cinemas, Context context) {
        this.cinemas = cinemas;
        this.context = context;
    }

    // Crea una nova instancia de ViewHolder inflant el layout corresponent
    @NonNull
    @Override
    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema, parent, false);
        return new CinemaViewHolder(view);
    }

    // Vincula les dades del cinema amb els elements de la interficie
    @Override
    public void onBindViewHolder(@NonNull CinemaViewHolder holder, int position) {
        Cinema cinema = cinemas.get(position);
        holder.textViewCinemaName.setText(cinema.getName());
        holder.textViewCinemaAddress.setText(cinema.getAddress());

        // Configura l'accio que es realitzara quan es faci clic en un cinema de la llista
        holder.itemView.setOnClickListener(v -> ((LlistaCines) context).showMoviesForCinema(cinema));
    }

    // Retorna el nombre d'elements de la llista de cinemes
    @Override
    public int getItemCount() {
        return cinemas.size();
    }

    // Classe ViewHolder que conte les referencies als elements de la interficie d'un item de la llista
    public static class CinemaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCinemaName;
        TextView textViewCinemaAddress;

        public CinemaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCinemaName = itemView.findViewById(R.id.textViewCinemaName);
            textViewCinemaAddress = itemView.findViewById(R.id.textViewCinemaAddress);
        }
    }
}
