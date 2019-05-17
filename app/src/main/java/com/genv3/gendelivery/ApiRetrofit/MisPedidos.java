package com.genv3.gendelivery.ApiRetrofit;

import com.genv3.gendelivery.Objects.EntregasTomadas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MisPedidos {
    @GET("ExtaerMisPedidos.php")
    Call<List<EntregasTomadas>> getMisPedidos(@Query("cdtId") Long cdtId);

    @GET("FechaUnica.php")
    Call<List<EntregasTomadas>> getMisPedidosFechaUnica (@Query("cdtId") Long cdtId, @Query("etgtFechaEntrega") String etgtFechaEntrega);

    @GET("FechaEntreRango.php")
    Call<List<EntregasTomadas>> getMisPedidosFechaRango (@Query("cdtId") Long cdtId, @Query("inicial") String inicial, @Query("final") String ffinal );
}
