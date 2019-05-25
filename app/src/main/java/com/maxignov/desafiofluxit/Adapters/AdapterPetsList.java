package com.maxignov.desafiofluxit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxignov.desafiofluxit.Model.PojoModel.Pet;
import com.maxignov.desafiofluxit.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterPetsList extends RecyclerView.Adapter {
    private List<Pet> petList;
    private PetSelectedInterface petSelectedInterface;
    private Context context;

    public AdapterPetsList(PetSelectedInterface petSelectedInterface, Context context) {
        this.petSelectedInterface = petSelectedInterface;
        petList = new ArrayList<>();
        this.context = context;
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


    //Borro la lista y la actualizo con la nueva pasada por parametro
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
            itemView.setOnClickListener(onClickListener);
        }

        //Cargo los datos en la celda si no son nulos ni vacios
        public void loadPetsData(Pet pet) {
            if(pet.getName() != null && !pet.getName().isEmpty()){
                textViewNameUpdate.setText(pet.getName());
            }else {
                textViewNameUpdate.setText(context.getResources().getString(R.string.unnamed));
            }
            if(pet.getId() != null && !pet.getId().isEmpty()){
                textViewIdUpdate.setText(pet.getId());
            }else {
                textViewIdUpdate.setText(context.getResources().getString(R.string.withoutId));
            }
        }

        //Listener de la celda
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = petList.get(getAdapterPosition()).getId();
                petSelectedInterface.petClick(id);
            }
        };
    }

    public interface PetSelectedInterface {
        void petClick(String id);
    }
}
