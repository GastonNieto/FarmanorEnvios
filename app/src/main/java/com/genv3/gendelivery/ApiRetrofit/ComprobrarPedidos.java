package com.genv3.gendelivery.ApiRetrofit;

import com.genv3.gendelivery.Objects.Cadetes;
import com.genv3.gendelivery.Objects.EntregasDisponibles;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ComprobrarPedidos {
    @GET("comprobrar.php")//indico operacion y de donde buscar en el servidor
    Call<List<EntregasDisponibles>> getDisponible(@Query("etgdId") BigInteger user);
}
