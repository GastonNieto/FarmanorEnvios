package com.genv3.gendelivery.Model;

import com.genv3.gendelivery.Adapter.AdapterPedidos;
import com.genv3.gendelivery.ApiRetrofit.Pedidos;
import com.genv3.gendelivery.Interface.IEntregasDisponibles;
import com.genv3.gendelivery.Objects.EntregasDisponibles;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.util.StringClase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PedidosModel implements IEntregasDisponibles.Model {
    ArrayList<EntregasDisponibles> entregasDisponibles = new ArrayList<>();

    private IEntregasDisponibles.Presenter presenter;

    public PedidosModel(IEntregasDisponibles.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void ExtraerPedidos() {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        //creo una instancia de retrofit
        //ademas debo agregar un coverterfactory (Gson)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StringClase.getIPSERVIDOR())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //llamamos a la interfaz y paso por parametro la clase de la interfas .class
        Pedidos apiRetrofit = retrofit.create(Pedidos.class);
        // este call tiene que ser igual al de la interfaz
        // y lo refenrenciamos con el de la interfaz
        Call<List<EntregasDisponibles>> call = apiRetrofit.getPost();
        //execute lo hace en el main thread no lo backcroud
        //enqueue lo hace en otro thread

        call.enqueue(new Callback<List<EntregasDisponibles>>() {
            @Override
            public void onResponse(Call<List<EntregasDisponibles>> call, Response<List<EntregasDisponibles>> response) {
                entregasDisponibles.clear();
                //si la respuesta no se pudo obtener ejemplo no esta autenticado para obetner la informacion
                if (!response.isSuccessful()) {
                    int respuesta = response.code();
                    //textView.setText(String.valueOf(respuesta));
                    return;
                }
                //Gson parsea los datos automaticamente y mediante response.body extraemos los datos en la list
                List<EntregasDisponibles> listDatos = response.body();
                for (EntregasDisponibles posts : listDatos) {
                    entregasDisponibles.add(posts);
                }
                if (entregasDisponibles.get(0).getEtgdEstado() == 999){
                        presenter.OnClear(R.drawable.ic_twotone_clear);
                        entregasDisponibles.clear();
                        presenter.RecibirPedidos(entregasDisponibles);
                }else {
                    presenter.RecibirPedidos(entregasDisponibles);
                    presenter.OnSucessCharge();
                }

             /*   AdapterPedidos adapterRecycler = new AdapterPedidos(entregasDisponibles);
                rvpedidos.setAdapter(adapterRecycler);
                adapterRecycler.notifyDataSetChanged();*/
            }

            @Override
            public void onFailure(Call<List<EntregasDisponibles>> call, Throwable t) {


                String failure = t.getMessage().toString();
                presenter.OnErrorCharge(R.drawable.ic_twotone_sinservicio);
                //textView.setText(failure);
                // Toast.makeText(this,failure,Toast.LENGTH_LONG).show();
            }
        });
    }

}

