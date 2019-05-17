package com.genv3.gendelivery.ApiRetrofit;

import com.genv3.gendelivery.Objects.EntregasTomadas;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Entrega {
    @GET("ExtraerEntrega.php")
    Call<List<EntregasTomadas>> getMiEntrega(@Query("cdtId") Long cdtId);
    @GET("RechazarEntrega.php")
    Call<List<EntregasTomadas>> rechazarEntrega(@Query("etgtId") BigInteger etgtId, @Query("cdtId") Long cdtId);
    @GET("EntregarEntrega.php")
    Call<List<EntregasTomadas>> entregarEntrega(@Query("etgtId") BigInteger etgtId, @Query("cdtId") Long cdtId, @Query("etgtLatitud") String etgtLatitud, @Query("etgtLongitud") String etgtLongitud);
}
