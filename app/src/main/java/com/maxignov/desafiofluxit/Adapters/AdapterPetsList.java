package com.maxignov.desafiofluxit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxignov.desafiofluxit.Model.Pet;
import com.maxignov.desafiofluxit.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterPetsList extends RecyclerView.Adapter {
    private List<Pet> petList;
    private PetSelectedInterface petSelectedInterface;

    public AdapterPetsList(PetSelectedInterface petSelectedInterface) {
        this.petSelectedInterface = petSelectedInterface;
        petList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View cellInflated = layoutInflater.inflate(R.layout.cell_pets_recycler_view, viewGroup, false);
        PetsViewHolder viewHolder = new PetsViewHolder(cellInflated);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Pet pet = petList.get(i);
        PetsViewHolder petsViewHolder = (PetsViewHolder) viewHolder;
        petsViewHolder.loadPetsData(pet);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public void updatePetsList(List<Pet> petListUpdate) {
        petList.clear();
        petList.addAll(petListUpdate);
        notifyDataSetChanged();
    }

    public class PetsViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNameUpdate;
        private TextView textViewIdUpdate;

        public PetsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameUpdate = itemView.findViewById(R.id.textViewNameUpdate);
            textViewIdUpdate = itemView.findViewById(R.id.textViewIdUpdate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = petList.get(getAdapterPosition()).getId();
                    petSelectedInterface.petClick(id);
                }
            });
        }

        public void loadPetsData(Pet pet) {
            textViewNameUpdate.setText(pet.getName());
            textViewIdUpdate.setText(pet.getId());
        }
    }

    public interface PetSelectedInterface {
        void petClick(String id);
    }
}
