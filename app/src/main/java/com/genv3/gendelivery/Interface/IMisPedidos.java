package com.genv3.gendelivery.Interface;

import com.genv3.gendelivery.Objects.EntregasTomadas;

import java.math.BigInteger;
import java.util.ArrayList;

public interface IMisPedidos {
    interface View {
        void CargarMisPedidos(ArrayList<EntregasTomadas> entregasTomadas);

        void OnErrorCharge(int a);

        void OnSucessCharge();

        void OnClear(int a);
    }

    interface Presenter {
        void RecibirMisPedidos(ArrayList<EntregasTomadas> entregasTomadas);

        void SolicitarMisPedidos(Long cadete, String estado);

        void SolicitarFechaUnica(Long cadete, String estado, String fechaunica);

        void SolicitarFechaRango(Long cadete, String estado, String fechainicial, String fechafinal);

        void OnErrorCharge(int a);

        void OnSucessCharge();

        void OnClear(int a);
    }

    interface Model {
        void extraerMisPedidos(Long cadete, String estado);

        void extraerFechaUnica(Long cadete, String estado, String fechaunica);

        void extraerFechaRango(Long cadete, String estado, String fechainicial, String fechafinal);
    }
}
