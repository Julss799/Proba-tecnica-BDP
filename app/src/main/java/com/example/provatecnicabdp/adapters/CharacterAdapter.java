package com.example.provatecnicabdp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.provatecnicabdp.R;
import com.example.provatecnicabdp.datos.Actor;
import com.example.provatecnicabdp.datos.Character;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private List<Character> characters;

    public CharacterAdapter(List<Character> characters) {
        this.characters = characters;
    }

    // Crea una nova instancia de ViewHolder inflant el layout corresponent
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character, parent, false);
        return new ViewHolder(view);
    }

    // Vincula les dades del personatge amb els elements de la interficie
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = characters.get(position);
        Actor actor = character.getActor();

        holder.txtCharacterName.setText(character.getName());
        holder.txtActorName.setText(actor.getName());
        holder.txtActorDescription.setText(actor.getDescription());
        holder.txtActorBirth.setText(actor.getBirthDate());

        // Carrega la imatge de l'actor utilitzant la llibreria Glide
        Glide.with(holder.itemView.getContext())
                .load(actor.getFoto())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgActorPhoto);
    }

    // Retorna el nombre d'elements de la llista de personatges
    @Override
    public int getItemCount() {
        return characters.size();
    }

    // Classe ViewHolder que conte les referencies als elements de la interficie d'un item de la llista
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgActorPhoto;
        public TextView txtCharacterName;
        public TextView txtActorName;
        public TextView txtActorDescription;
        public TextView txtActorBirth;

        public ViewHolder(View itemView) {
            super(itemView);
            imgActorPhoto = itemView.findViewById(R.id.imgActorPhoto);
            txtCharacterName = itemView.findViewById(R.id.txtCharacterName);
            txtActorName = itemView.findViewById(R.id.txtActorName);
            txtActorDescription = itemView.findViewById(R.id.txtActorDescription);
            txtActorBirth = itemView.findViewById(R.id.txtActorBirth);
        }
    }
}
