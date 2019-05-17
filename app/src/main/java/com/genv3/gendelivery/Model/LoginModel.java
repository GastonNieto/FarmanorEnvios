package com.genv3.gendelivery.Model;

import android.content.Context;

import com.genv3.gendelivery.ApiRetrofit.Login;
import com.genv3.gendelivery.Interface.Ilogin;
import com.genv3.gendelivery.Objects.Cadetes;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.util.Preferens;
import com.genv3.gendelivery.util.StringClase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.Settings.System.getString;

public class LoginModel implements Ilogin.model {

    private Ilogin.presenter presenter;

    public LoginModel(Ilogin.presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void auntenticarPresenter(String user, final String pass, final Context c) {


        //creo una instancia de retrofit
        //ademas debo agregar un coverterfactory (Gson)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StringClase.getIPSERVIDOR())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //llamamos a la interfaz y paso por parametro la clase de la interfas .class
        Login apiRetrofit = retrofit.create(Login.class);
        // este call tiene que ser igual al de la interfaz
        // y lo refenrenciamos con el de la interfaz
        Call<List<Cadetes>> call = apiRetrofit.getcadetes(user, pass);
        //execute lo hace en el main thread no lo backcroud
        //enqueue lo hace en otro thread

        call.enqueue(new Callback<List<Cadetes>>() {
            @Override
            public void onResponse(Call<List<Cadetes>> call, Response<List<Cadetes>> response) {
                //si la respuesta no se pudo obtener ejemplo no esta autenticado para obetner la informacion
                if (!response.isSuccessful()) {
                    int respuesta = response.code();
                    return;
                }
                //Gson parsea los datos automaticamente y mediante response.body extraemos los datos en la list
                // textView.setText("Gol");
                List<Cadetes> listDatos = response.body();
                if (listDatos.size() == 0) {
                    presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);
                } else if (listDatos.get(0).getCdtUsr().equals("JSONVACIO")) {
                    presenter.onDatosIncorrectos(R.drawable.ic_twotone_clear);

                } else if (listDatos.get(0).getCdtEst().equals(2)) {
                    presenter.onUserBlock(R.drawable.ic_twotone_sinservicio);
                } else {
                    Cadetes cadetes = listDatos.get(0);
                    Preferens.setInteger(c, Preferens.getKeyGuardia(), cadetes.getCdtId());
                    presenter.autenticarPresenter(listDatos);
                }

            }

            @Override
            public void onFailure(Call<List<Cadetes>> call, Throwable t) {
                String failure = t.getMessage();
                presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);

            }
        });
    }
}

