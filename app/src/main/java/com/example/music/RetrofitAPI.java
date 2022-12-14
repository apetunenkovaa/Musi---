package com.example.music;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("Musics")
    Call<Mask> createPost(@Body Mask dataModal);

    @PUT("Musics/")
    Call<Mask> createPut(@Body Mask dataModal, @Query("ID") int id);

    @DELETE("Musics")
    Call<Mask> createDelete(@Query("id") int id);
}
