package com.maxignov.desafiofluxit.Views.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maxignov.desafiofluxit.Constants;
import com.maxignov.desafiofluxit.Model.Pet;
import com.maxignov.desafiofluxit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPetDetail extends Fragment {
    private TextView textViewName;
    private TextView textViewId;


    public FragmentPetDetail() {
        // Required empty public constructor
    }

    public static FragmentPetDetail buildFragmentPetDetail(Pet pet){
        FragmentPetDetail fragmentPetDetail = new FragmentPetDetail();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_PET_ID, pet.getId());
        bundle.putString(Constants.KEY_PET_NAME, pet.getName());
        bundle.putString(Constants.KEY_PET_STATUS, pet.getStatus());
        fragmentPetDetail.setArguments(bundle);
        return fragmentPetDetail;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pet_detail, container, false);
        initViews(v);
        Bundle bundle = getArguments();
        if(bundle != null){
            String name = bundle.getString(Constants.KEY_PET_NAME);
            if(name != null && !name.equals("")){
                textViewName.setText(name);
            }else {
                textViewName.setText(getContext().getResources().getString(R.string.unnamed));
            }
            String id = bundle.getString(Constants.KEY_PET_ID);
            if(id != null && !id.equals("")){
                textViewId.setText(id);
            }else {
                textViewName.setText(getContext().getResources().getString(R.string.withoutId));
            }
        }

        return v;
    }

    private void initViews(View view){
        textViewName = view.findViewById(R.id.textViewName);
        textViewId = view.findViewById(R.id.textViewId);
    }

}
