package com.genv3.gendelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genv3.gendelivery.Objects.EntregasTomadas;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.View.Activtys.EntregaDetalleView;
import com.genv3.gendelivery.util.FormatDate;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterMisPedidos extends RecyclerView.Adapter<AdapterMisPedidos.ViewHolderDatos> {
    private ArrayList<EntregasTomadas> tomadas;
    private Context context;

    public AdapterMisPedidos(ArrayList<EntregasTomadas> tomadas, Context context) {
        this.tomadas = tomadas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_mispedidos, parent, false);

        return new AdapterMisPedidos.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.tvsucnombre.setText(tomadas.get(position).getEntregasDisponibles().get(0).getSucursal().get(0).getSclNom());
        holder.tvpeddireccion.setText(tomadas.get(position).getEntregasDisponibles().get(0).getEtgdDir());
        holder.tvpednom.setText(tomadas.get(position).getEntregasDisponibles().get(0).getEtgdReceptor());
        holder.tvpedfech.setText(FormatDate.formateador(tomadas.get(position).getEtgtFechaEntrega()));

    }

    @Override
    public int getItemCount() {
        return tomadas.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvsucnombre, tvpeddireccion, tvpednom, tvpedfech;
        View vw;
        MaterialButton btnver;
        Context context;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tvpednom = itemView.findViewById(R.id.tvMisPedNom);
            tvsucnombre = itemView.findViewById(R.id.tvMisSucNombre);
            tvpeddireccion = itemView.findViewById(R.id.tvMisPedDireccion);
            tvpedfech = itemView.findViewById(R.id.tvMisPedFech);
            btnver = itemView.findViewById(R.id.btnAccion);
            vw = itemView.findViewById(R.id.vwEntrega);
            btnver.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            final EntregasTomadas entregasTomadas = tomadas.get(position);
            if (v.getId() == btnver.getId()) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("objET",entregasTomadas);
                Intent intent = new Intent(context, EntregaDetalleView.class);
                intent.putExtra("DetalleEntrega",bundle);
                context.startActivity(intent);

            }
        }
    }
}
