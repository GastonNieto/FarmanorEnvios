package com.genv3.gendelivery.Presenter;

import com.genv3.gendelivery.Interface.ITomarPedidos;
import com.genv3.gendelivery.Model.TomarPedidosModel;

import java.math.BigInteger;

public class TomarPedidosPresenter implements ITomarPedidos.Presener {
    private ITomarPedidos.View view;
    private ITomarPedidos.Model model;

    public TomarPedidosPresenter(ITomarPedidos.View view) {
        this.view = view;
        model = new TomarPedidosModel(this);
    }

    @Override
    public void SolicitarPedidoLibre(Long pedido, BigInteger cadete, String estado) {
        if (view != null) {
            model.CargarPedido(pedido, cadete, estado);
        }
    }

    @Override
    public void onTomado(int a) {
        if (view != null) {
            view.onTomado(a);
        }
    }

    @Override
    public void onOcupado(int a) {
        if (view != null) {
            view.onOcupado(a);
        }
    }

    @Override
    public void onError(int a) {
        if (view != null) {
            view.onError(a);
        }
    }

    @Override
    public void onPedidoPendiente(int a) {
        if (view !=null){
            view.onPedidoPendiente(a);
        }
    }
}
