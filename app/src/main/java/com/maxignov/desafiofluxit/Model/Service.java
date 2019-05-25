package com.maxignov.desafiofluxit.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @Headers({"Accept: application/json" })

    @GET("pet/findByStatus?")
    Call<List<Pet>> getPetListByStatus(@Query("status") String status);

    @GET("pet/{petId}")
    Call<Pet> getPetId(@Path("petId")String petId);
}