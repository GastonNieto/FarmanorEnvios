package com.genv3.gendelivery.Presenter;

import com.genv3.gendelivery.Interface.IEntregasDisponibles;
import com.genv3.gendelivery.Model.PedidosModel;
import com.genv3.gendelivery.Objects.EntregasDisponibles;

import java.util.ArrayList;

public class PedidosPresenter implements IEntregasDisponibles.Presenter {
    private IEntregasDisponibles.View view;
    private IEntregasDisponibles.Model model;

    public PedidosPresenter(IEntregasDisponibles.View view) {
        this.view = view;
        model = new PedidosModel(this);
    }

    @Override
    public void SolicitarPedidos() {
        if (view != null) {
            model.ExtraerPedidos();
        }
    }

    @Override
    public void RecibirPedidos(ArrayList<EntregasDisponibles> entregasDisponibles) {
        if (view != null) {
            view.cargarRecycler(entregasDisponibles);
        }
    }

    @Override
    public void OnErrorCharge(int a) {
        if (view != null) {
            view.OnErrorCharge(a);
        }
    }

    @Override
    public void OnSucessCharge() {
        if (view != null) {
            view.OnSucessCharge();
        }
    }

    @Override
    public void OnClear(int a) {
        if (view != null) {
            view.OnClear(a);
        }
    }
}
