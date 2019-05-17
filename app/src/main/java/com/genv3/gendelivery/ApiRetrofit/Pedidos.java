package com.genv3.gendelivery.ApiRetrofit;

import com.genv3.gendelivery.Objects.EntregasDisponibles;
import com.genv3.gendelivery.Objects.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Pedidos {
    //metodod encargadoa de obetner toda la informacion
    //envia un request al servidor y devuelve una respuesta
    //creo una lista de mi modelo
   @GET("PedidosDisponibles.php")//indico operacion y de donde buscar en el servidor
   Call<List<EntregasDisponibles>> getPost();
}
