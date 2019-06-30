package com.genv3.gendelivery.Model;

import com.genv3.gendelivery.ApiRetrofit.MisPedidos;
import com.genv3.gendelivery.Interface.IMisPedidos;
import com.genv3.gendelivery.Objects.EntregasDisponibles;
import com.genv3.gendelivery.Objects.EntregasTomadas;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.util.StringClase;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MisPedidosModel implements IMisPedidos.Model {
    ArrayList<EntregasTomadas> tomadas = new ArrayList<>();

    private IMisPedidos.Presenter presenter;

    public MisPedidosModel(IMisPedidos.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void extraerMisPedidos(Long cadete, String estado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StringClase.getIPSERVIDOR())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MisPedidos misPedidos = retrofit.create(MisPedidos.class);
        Call<List<EntregasTomadas>> call = misPedidos.getMisPedidos(cadete);
        call.enqueue(new Callback<List<EntregasTomadas>>() {
            @Override
            public void onResponse(Call<List<EntregasTomadas>> call, Response<List<EntregasTomadas>> response) {
                Double ganancia = 0.0;
                if (!response.isSuccessful()) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

                    return;
                }
                tomadas.clear();

                List<EntregasTomadas> entregasTomadasList = response.body();
                for (EntregasTomadas posts : entregasTomadasList) {
                    ganancia = ganancia + posts.getEtgtImporte();
                    tomadas.add(posts);
                }
                Math.floor(ganancia);
                if (tomadas.size()==0) {
                    presenter.OnClear(R.drawable.ic_twotone_clear);
                    tomadas.clear();
                    presenter.RecibirMisPedidos(tomadas,  Math.floor(ganancia* 100.0) / 100.0);
                } else {
                    presenter.RecibirMisPedidos(tomadas,  Math.floor(ganancia* 100.0) / 100.0);
                    presenter.OnSucessCharge();

                }
            }

            @Override
            public void onFailure(Call<List<EntregasTomadas>> call, Throwable t) {
                String failure = t.getMessage().toString();
                presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

            }
        });
    }

    @Override
    public void extraerFechaUnica(Long cadete, String estado, String fechaunica) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StringClase.getIPSERVIDOR())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MisPedidos misPedidos = retrofit.create(MisPedidos.class);
        Call<List<EntregasTomadas>> call = misPedidos.getMisPedidosFechaUnica(cadete,fechaunica);
        call.enqueue(new Callback<List<EntregasTomadas>>() {
            @Override
            public void onResponse(Call<List<EntregasTomadas>> call, Response<List<EntregasTomadas>> response) {
                if (!response.isSuccessful()) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

                    return;
                }
                Double ganancia = 0.0;
                tomadas.clear();
                List<EntregasTomadas> entregasTomadasList = response.body();
                for (EntregasTomadas posts : entregasTomadasList) {
                    ganancia = ganancia + posts.getEtgtImporte();
                    tomadas.add(posts);
                }
                if (tomadas.size()==0) {
                    presenter.OnClear(R.drawable.ic_twotone_clear);
                    tomadas.clear();
                    presenter.RecibirMisPedidos(tomadas,  Math.floor(ganancia* 100.0) / 100.0);
                } else {
                    presenter.RecibirMisPedidos(tomadas,  Math.floor(ganancia* 100.0) / 100.0);
                    presenter.OnSucessCharge();

                }
            }

            @Override
            public void onFailure(Call<List<EntregasTomadas>> call, Throwable t) {
                String failure = t.getMessage().toString();
                presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

            }
        });
    }

    @Override
    public void extraerFechaRango(Long cadete, String estado, String fechainicial, String fechafinal) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StringClase.getIPSERVIDOR())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MisPedidos misPedidos = retrofit.create(MisPedidos.class);
        Call<List<EntregasTomadas>> call = misPedidos.getMisPedidosFechaRango(cadete,fechainicial+" 00:00:00",fechafinal+" 23:59:59");
        call.enqueue(new Callback<List<EntregasTomadas>>() {
            @Override
            public void onResponse(Call<List<EntregasTomadas>> call, Response<List<EntregasTomadas>> response) {
                if (!response.isSuccessful()) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

                    return;
                }
                Double ganancia = 0.0;
                tomadas.clear();
                List<EntregasTomadas> entregasTomadasList = response.body();
                for (EntregasTomadas posts : entregasTomadasList) {
                    ganancia = ganancia + posts.getEtgtImporte();
                    tomadas.add(posts);
                }
                if (tomadas.size()==0) {
                    presenter.OnClear(R.drawable.ic_twotone_clear);
                    tomadas.clear();
                    presenter.RecibirMisPedidos(tomadas,  Math.floor(ganancia* 100.0) / 100.0);

                } else {
                    presenter.RecibirMisPedidos(tomadas,  Math.floor(ganancia* 100.0) / 100.0);
                    presenter.OnSucessCharge();

                }
            }

            @Override
            public void onFailure(Call<List<EntregasTomadas>> call, Throwable t) {
                String failure = t.getMessage().toString();
                presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

            }
        });
    }
}
