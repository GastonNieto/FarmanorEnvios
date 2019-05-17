package com.genv3.gendelivery.ApiRetrofit;

import com.genv3.gendelivery.Objects.Cadetes;
import com.genv3.gendelivery.Objects.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Login {
    //metodod encargadoa de obetner toda la informacion
    //envia un request al servidor y devuelve una respuesta
    //creo una lista de mi modelo
    @GET("login.php")//indico operacion y de donde buscar en el servidor
    Call<List<Cadetes>> getcadetes(@Query("cdtUsr") String user, @Query("cdtPwd") String pass);
}
