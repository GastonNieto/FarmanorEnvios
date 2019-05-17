package com.genv3.gendelivery.ApiRetrofit;

import com.genv3.gendelivery.Objects.EntregasTomadas;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TomarPedido {

    @GET("TomarPedido.php")
    Call<List<EntregasTomadas>>getpedido(@Query("etgdId") BigInteger etgdId, @Query("cdtId") Long cdtId, @Query("etgtEstado") String etgtEstado);

}
