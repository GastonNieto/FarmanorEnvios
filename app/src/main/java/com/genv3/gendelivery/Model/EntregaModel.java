package com.genv3.gendelivery.Model;

import com.genv3.gendelivery.ApiRetrofit.Entrega;
import com.genv3.gendelivery.Interface.IEntrega;
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

public class EntregaModel implements IEntrega.Model {
    IEntrega.Presenter presenter;
    ArrayList<EntregasTomadas> entregasTomadas = new ArrayList<>();

    public EntregaModel(IEntrega.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void ExtaerEntrega(Long a) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StringClase.getIPSERVIDOR())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Entrega entrega = retrofit.create(Entrega.class);
        Call<List<EntregasTomadas>> call = entrega.getMiEntrega(a);
        call.enqueue(new Callback<List<EntregasTomadas>>() {
            @Override
            public void onResponse(Call<List<EntregasTomadas>> call, Response<List<EntregasTomadas>> response) {
                if (!response.isSuccessful()) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

                    return;
                }
                List<EntregasTomadas> entregasTomadasList = response.body();
                for (EntregasTomadas tomadas : entregasTomadasList) {
                    entregasTomadas.add(tomadas);
                }
                if (entregasTomadas.size() == 0) {
                    presenter.OnClear(R.drawable.ic_twotone_clear);
                    entregasTomadas.clear();
                } else if (entregasTomadas.get(0).getEtgtEstado().equals("JSONVACIO")) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

                } else {
                    presenter.EnviarEntrega(entregasTomadas);
                    presenter.OnSucessCharge();
                }
            }

            @Override
            public void onFailure(Call<List<EntregasTomadas>> call, Throwable t) {
                presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

            }
        });
    }

    @Override
    public void ProcesoDeEntrega(BigInteger a, Long B, String latitud, String longitud) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StringClase.getIPSERVIDOR())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Entrega entrega = retrofit.create(Entrega.class);
        Call<List<EntregasTomadas>> call = entrega.entregarEntrega(a, B, latitud, longitud);
        call.enqueue(new Callback<List<EntregasTomadas>>() {
            @Override
            public void onResponse(Call<List<EntregasTomadas>> call, Response<List<EntregasTomadas>> response) {
                if (!response.isSuccessful()) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

                    return;
                }
                ArrayList<EntregasTomadas> entregasTomadas2 = new ArrayList<>();
                List<EntregasTomadas> entregasTomadasList = response.body();
                for (EntregasTomadas tomadas : entregasTomadasList) {
                    entregasTomadas2.add(tomadas);
                }
                if (entregasTomadas2.get(0).getEtgtEstado().equals("JSONVACIO")) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);
                } else if (entregasTomadas2.size() == 0) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_clear);
                } else {
                    presenter.OnEntrega(R.drawable.ic_twotone_correcto);
                    entregasTomadas.clear();


                }
            }


            @Override
            public void onFailure(Call<List<EntregasTomadas>> call, Throwable t) {
                presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

            }
        });
    }

    @Override
    public void ProcesoDeRechazo(BigInteger a, Long B) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StringClase.getIPSERVIDOR())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Entrega entrega = retrofit.create(Entrega.class);
        Call<List<EntregasTomadas>> call = entrega.rechazarEntrega(a, B);
        call.enqueue(new Callback<List<EntregasTomadas>>() {
            @Override
            public void onResponse(Call<List<EntregasTomadas>> call, Response<List<EntregasTomadas>> response) {
                if (!response.isSuccessful()) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

                    return;
                }
                ArrayList<EntregasTomadas> entregasTomadas2 = new ArrayList<>();
                List<EntregasTomadas> entregasTomadasList = response.body();
                for (EntregasTomadas tomadas : entregasTomadasList) {
                    entregasTomadas2.add(tomadas);
                }
                if (entregasTomadas2.get(0).getEtgtEstado().equals("JSONVACIO")) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);
                } else if (entregasTomadas2.size() == 0) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_clear);

                } else {
                    presenter.OnRechazo(R.drawable.ic_twotone_correcto);
                    entregasTomadas.clear();
                }
            }

            @Override
            public void onFailure(Call<List<EntregasTomadas>> call, Throwable t) {
                presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

            }
        });
    }
}
