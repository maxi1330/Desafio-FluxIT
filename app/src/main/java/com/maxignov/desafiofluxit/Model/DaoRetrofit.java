package com.maxignov.desafiofluxit.Model;

import com.maxignov.desafiofluxit.ListenerCustom;
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

    public void getPetFindByStatus(final ListenerCustom<List<Pet>> listenerController, String status){
        Call<List<Pet>> call = service.getPetListByStatus(status);
        call.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                List<Pet> petList = response.body();
                System.out.println("<---- RESPONSE ");      //TODO PASAR A LOG.D
                System.out.println("###### BODY ");
                System.out.println(response.body().toString());
                System.out.println("###### HEADERS ");
                System.out.println(response.headers().toString());
                System.out.println("<---- RESPONSE ");
                listenerController.finish(petList);
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                System.out.println("<---- FALLO ");
                System.out.println(t.toString());
                System.out.println("<---- FALLO ");
                listenerController.finish(null);
            }
        });
    }


    public void getPetFindById(final ListenerCustom<Pet> listenerController, String id){
        Call<Pet> retrofitListener = service.getPetId(id);
        retrofitListener.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                System.out.println("<---- RESPONSE ");
                System.out.println("<---- BODY ");
                System.out.println(response.body().toString());
                System.out.println("<---- HEADERS ");
                System.out.println(response.headers().toString());
                System.out.println("<---- RESPONSE ");
                listenerController.finish(response.body());
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                System.out.println("<---- FALLO ");
                System.out.println(t.toString());
                System.out.println("<---- FALLO ");
                listenerController.finish(null);
            }
        });
    }
}