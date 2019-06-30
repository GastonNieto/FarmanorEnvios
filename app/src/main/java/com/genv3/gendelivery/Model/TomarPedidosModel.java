package com.genv3.gendelivery.Model;

import com.genv3.gendelivery.ApiRetrofit.TomarPedido;
import com.genv3.gendelivery.Interface.ITomarPedidos;
import com.genv3.gendelivery.Objects.EntregasTomadas;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.util.StringClase;

import java.math.BigInteger;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TomarPedidosModel implements ITomarPedidos.Model {
    private ITomarPedidos.Presener presener;

    public TomarPedidosModel(ITomarPedidos.Presener presener) {
        this.presener = presener;
    }

    @Override
    public void CargarPedido(Long pedido, BigInteger cadete, String estado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StringClase.getIPSERVIDOR())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TomarPedido tomarPedido = retrofit.create(TomarPedido.class);
        Call<List<EntregasTomadas>> call = tomarPedido.getpedido(cadete, pedido, estado);
        call.enqueue(new Callback<List<EntregasTomadas>>() {
            @Override
            public void onResponse(Call<List<EntregasTomadas>> call, Response<List<EntregasTomadas>> response) {
                if (!response.isSuccessful()) {
                    presener.onError(R.drawable.ic_twotone_sinservicio);

                    return;
                }
                List<EntregasTomadas> entregasTomadasList = response.body();
                if (entregasTomadasList.size() == 0) {
                    presener.onOcupado(R.drawable.ic_twotone_clear);
                } else if (entregasTomadasList.get(0).getEtgtLatitud().equals("JSONVACIO")) {
                    presener.onError(R.drawable.ic_twotone_sinservicio);
                } else if(entregasTomadasList.get(0).getEtgtLatitud().equals("DENEGARPEDIDO")) {
                    presener.onPedidoPendiente(R.drawable.ic_twotone_clear);
                }else {
                    presener.onTomado(R.drawable.ic_twotone_correcto);

                }
            }

            @Override
            public void onFailure(Call<List<EntregasTomadas>> call, Throwable t) {

                presener.onError(R.drawable.ic_twotone_sinservicio);
            }
        });
    }
}
