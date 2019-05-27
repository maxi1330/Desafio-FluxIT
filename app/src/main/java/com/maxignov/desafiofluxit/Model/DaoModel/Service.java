package com.maxignov.desafiofluxit.Model.DaoModel;

import com.maxignov.desafiofluxit.Model.PojoModel.Pet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    //Modifico headers para que llegue un json
    @Headers({"Accept: application/json"})

    @GET("pet/findByStatus?")
    Call<List<Pet>> getPetListByStatus(@Query("status") String status);

    @GET("pet/{petId}")
    Call<Pet> getPetId(@Path("petId")String petId);
}