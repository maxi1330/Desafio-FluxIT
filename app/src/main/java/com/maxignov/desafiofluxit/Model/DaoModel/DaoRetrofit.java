package com.maxignov.desafiofluxit.Model.DaoModel;

import android.util.Log;

import com.maxignov.desafiofluxit.Utils.ListenerCustom;
import com.maxignov.desafiofluxit.Model.PojoModel.Pet;

import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaoRetrofit {

    private Retrofit retrofit;
    private Service service;

    public DaoRetrofit(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://petstore.swagger.io/v2/")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.client(httpClient.build()).build();
        service = retrofit.create(Service.class);
    }

    //Llamamo al servicio y solicito las mascotas por estado
    public void getPetFindByStatus(final ListenerCustom<List<Pet>> listenerController, String status){
        Call<List<Pet>> call = service.getPetListByStatus(status);
        call.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                List<Pet> petList = response.body();
                Log.d("Retrofit BODY","<<---- RESPONSE " + response.code());
                Log.d("Retrofit BODY",response.body().toString());
                Log.d("Retrofit HEADERS",response.headers().toString());
                Log.d("Retrofit BODY","<<---- RESPONSE ");
                listenerController.finish(petList);
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Log.e("Retrofit FALLO", t.getMessage());
                listenerController.finish(null);
            }
        });
    }

    //Llamamo al servicio y solicito las mascotas por ID
    public void getPetFindById(final ListenerCustom<Pet> listenerController, String id){
        Call<Pet> retrofitListener = service.getPetId(id);
        retrofitListener.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                Log.d("Retrofit BODY","<<---- RESPONSE " + response.code());
                Log.d("Retrofit BODY",response.body().toString());
                Log.d("Retrofit HEADERS",response.headers().toString());
                Log.d("Retrofit BODY","<<---- RESPONSE ");
                listenerController.finish(response.body());
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Log.e("Retrofit FALLO", t.getMessage());
                listenerController.finish(null);
            }
        });
    }
}