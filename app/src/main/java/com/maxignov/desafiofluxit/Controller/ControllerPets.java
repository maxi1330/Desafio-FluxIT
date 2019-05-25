package com.maxignov.desafiofluxit.Controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.maxignov.desafiofluxit.Utils.ListenerCustom;
import com.maxignov.desafiofluxit.Model.DaoModel.DaoRetrofit;
import com.maxignov.desafiofluxit.Model.PojoModel.Pet;
import com.maxignov.desafiofluxit.R;

import java.util.List;

public class ControllerPets {
    private Context context;

    public ControllerPets(Context context) {
        this.context = context;
    }

    //Solicita las mascotas por estado
    public void requestPetFindByStatus(final ListenerCustom<List<Pet>> listenerView, String status) {
        if(internetAvailable()){
            DaoRetrofit daoRetrofit = new DaoRetrofit();
            ListenerCustom<List<Pet>> listenerController = new ListenerCustom<List<Pet>>() {
                @Override
                public void finish(List<Pet> response) {
                    listenerView.finish(response);
                }
            };
            daoRetrofit.getPetFindByStatus(listenerController,status);
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.messageNoConnectionAvailable), Toast.LENGTH_SHORT).show();
        }
    }

    //Solicita la mascota por ID
    public void requestPetFindById(final ListenerCustom<Pet> listenerView, String id) {
        if(internetAvailable()) {
            DaoRetrofit daoRetrofit = new DaoRetrofit();
            ListenerCustom<Pet> listenerController = new ListenerCustom<Pet>() {
                @Override
                public void finish(Pet response) {
                    listenerView.finish(response);
                }
            };
            daoRetrofit.getPetFindById(listenerController, String.valueOf(id));
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.messageNoConnectionAvailable), Toast.LENGTH_SHORT).show();
        }
    }

    //Verifica que haya conexion a internet
    private boolean internetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
