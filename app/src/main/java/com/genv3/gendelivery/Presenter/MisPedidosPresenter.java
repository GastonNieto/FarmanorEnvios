package com.genv3.gendelivery.Presenter;

import com.genv3.gendelivery.Interface.IMisPedidos;
import com.genv3.gendelivery.Model.MisPedidosModel;
import com.genv3.gendelivery.Objects.EntregasTomadas;

import java.math.BigInteger;
import java.util.ArrayList;

public class MisPedidosPresenter implements IMisPedidos.Presenter {
    private IMisPedidos.View view;
    private IMisPedidos.Model model;

    public MisPedidosPresenter(IMisPedidos.View view) {
        this.view = view;
        model = new MisPedidosModel(this);
    }

    @Override
    public void RecibirMisPedidos(ArrayList<EntregasTomadas> entregasTomadas) {
        if (view != null) {
            view.CargarMisPedidos(entregasTomadas);
        }
    }

    @Override
    public void SolicitarMisPedidos(Long cadete, String estado) {
        if (view != null) {
            model.extraerMisPedidos(cadete, estado);
        }
    }

    @Override
    public void SolicitarFechaUnica(Long cadete, String estado, String fechaunica) {
        if (view != null) {
            model.extraerFechaUnica(cadete, estado, fechaunica);
        }
    }

    @Override
    public void SolicitarFechaRango(Long cadete, String estado, String fechainicial, String fechafinal) {
        if (view != null) {
            model.extraerFechaRango(cadete, estado, fechainicial, fechafinal);
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
