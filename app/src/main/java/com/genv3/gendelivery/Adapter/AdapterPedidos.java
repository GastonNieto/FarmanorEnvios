package com.genv3.gendelivery.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genv3.gendelivery.ApiRetrofit.ComprobrarPedidos;
import com.genv3.gendelivery.ApiRetrofit.Pedidos;
import com.genv3.gendelivery.Objects.EntregasDisponibles;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.View.Activtys.TomarPedidosView;
import com.genv3.gendelivery.View.Fragments.PedidosFragmen;
import com.genv3.gendelivery.util.FormatDate;
import com.genv3.gendelivery.util.IRespuesta;
import com.genv3.gendelivery.util.StringClase;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.ViewHolderDatos> {
    private ArrayList<EntregasDisponibles> listDatos;
    private Context context2;

    public AdapterPedidos(ArrayList<EntregasDisponibles> listDatos, Context context) {
        this.context2 = context;
        this.listDatos = listDatos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterPedidos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_pedidoslibres, parent, false);
        return new AdapterPedidos.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPedidos.ViewHolderDatos holder, int position) {
        holder.tvsucnombre.setText(listDatos.get(position).getSucursal().get(0).getSclNom());
        holder.tvpeddireccion.setText(listDatos.get(position).getEtgdDir());
        holder.tvpednom.setText(listDatos.get(position).getEtgdReceptor());
        holder.tvpedfech.setText(FormatDate.formateador(listDatos.get(position).getEtgdFecha()));

    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvsucnombre, tvpeddireccion, tvpednom, tvpedfech;
        MaterialButton btnver;
        Context context;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tvpednom = itemView.findViewById(R.id.tvPedNom);
            tvsucnombre = itemView.findViewById(R.id.tvSucNombre);
            tvpeddireccion = itemView.findViewById(R.id.tvPedDireccion);
            tvpedfech = itemView.findViewById(R.id.tvPedFech);
            btnver = itemView.findViewById(R.id.btnVer);
            btnver.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            final int position = getAdapterPosition();
            final EntregasDisponibles disponibles = listDatos.get(position);
            if (v.getId() == btnver.getId()) {
                comprobrarPedido(disponibles.getEtgdId(), new IRespuesta() {
                    @Override
                    public void onResponse(String success) {
                        if (success != "FAIL") {
                            if (success == "OK") {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("objED", disponibles);
                                Intent i = new Intent(PedidosFragmen.UPDATE);
                                i.putExtra(PedidosFragmen.UPDATE, bundle);
                                LocalBroadcastManager.getInstance(context2).sendBroadcast(i);

                            } else {
                                Intent i = new Intent(PedidosFragmen.UPDATE);
                                i.putExtra(PedidosFragmen.UPDATE, PedidosFragmen.REFRESH);
                                LocalBroadcastManager.getInstance(context2).sendBroadcast(i);
                            }
                        } else {
                            Intent i = new Intent(PedidosFragmen.UPDATE);
                            i.putExtra(PedidosFragmen.UPDATE, PedidosFragmen.FAIL);
                            LocalBroadcastManager.getInstance(context2).sendBroadcast(i);
                        }
                    }

                });


            }
        }

        public void comprobrarPedido(BigInteger i, final IRespuesta iRespuesta) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            //creo una instancia de retrofit
            //ademas debo agregar un coverterfactory (Gson)
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(StringClase.getIPSERVIDOR())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            //llamamos a la interfaz y paso por parametro la clase de la interfas .class
            ComprobrarPedidos apiRetrofit = retrofit.create(ComprobrarPedidos.class);
            // este call tiene que ser igual al de la interfaz
            // y lo refenrenciamos con el de la interfaz
            Call<List<EntregasDisponibles>> call = apiRetrofit.getDisponible(i);
            //execute lo hace en el main thread no lo backcroud
            //enqueue lo hace en otro thread

            call.enqueue(new Callback<List<EntregasDisponibles>>() {
                @Override
                public void onResponse(Call<List<EntregasDisponibles>> call, Response<List<EntregasDisponibles>> response) {
                    String a = "hola";
                    //si la respuesta no se pudo obtener ejemplo no esta autenticado para obetner la informacion
                    if (!response.isSuccessful()) {
                        int respuesta = response.code();
                        //textView.setText(String.valueOf(respuesta));

                        return;
                    }
                    //Gson parsea los datos automaticamente y mediante response.body extraemos los datos en la list
                    List<EntregasDisponibles> listDatos = response.body();
                    if (listDatos.get(0).getEtgdEstado() == 1) {
                        // Toast.makeText(context, "GAAL", Toast.LENGTH_LONG).show();
                        iRespuesta.onResponse("OK");

                    } else {
                        iRespuesta.onResponse("NOK");
                    }
                }

                @Override
                public void onFailure(Call<List<EntregasDisponibles>> call, Throwable t) {

                    iRespuesta.onResponse("FAIL");
                    String failure = t.getMessage().toString();

                }
            });

        }


    }
}
