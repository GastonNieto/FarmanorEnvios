package com.genv3.gendelivery.Presenter;

import com.genv3.gendelivery.Interface.IEntrega;
import com.genv3.gendelivery.Model.EntregaModel;
import com.genv3.gendelivery.Objects.EntregasTomadas;

import java.math.BigInteger;
import java.util.ArrayList;

public class EntregaPresenter implements IEntrega.Presenter {
    private IEntrega.View view;
    private IEntrega.Model model;

    public EntregaPresenter(IEntrega.View view) {
        this.view = view;
        model = new EntregaModel(this);
    }

    @Override
    public void SolicitarEntrega(Long a) {
        if (view != null) {
            model.ExtaerEntrega(a);
        }
    }

    @Override
    public void EnviarEntrega(ArrayList<EntregasTomadas> tomadas) {
        if (view != null) {
            view.CargarEntrega(tomadas);
        }
    }

    @Override
    public void ProcesarEntrega(BigInteger a, Long B, String latitud, String longitud) {
        if (view != null) {
            model.ProcesoDeEntrega(a, B, latitud, longitud);
        }
    }

    @Override
    public void ProcesarRechazo(BigInteger a, Long B) {
        if (view !=null){
            model.ProcesoDeRechazo(a, B);
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

    @Override
    public void OnEntrega(int a) {
        if (view != null) {
            view.OnEntrega(a);
        }
    }

    @Override
    public void OnEntregaError(int a) {
        view.OnEntregaError(a);
    }

    @Override
    public void OnRechazo(int a) {
        if (view != null) {
            view.OnRechazo(a);
        }
    }
}
