package com.genv3.gendelivery.Interface;

import com.genv3.gendelivery.Objects.EntregasTomadas;

import java.math.BigInteger;
import java.util.ArrayList;

public interface IEntrega {
    interface View {
        void CargarEntrega(ArrayList<EntregasTomadas> tomadas);
        void OnErrorCharge(int a);
        void OnSucessCharge();
        void OnClear(int a);
        void OnEntrega (int a);
        void OnEntregaError(int a);
        void OnRechazo (int a);
    }
    interface Presenter{
        void SolicitarEntrega(Long a);
        void EnviarEntrega (ArrayList<EntregasTomadas> tomadas);
        void ProcesarEntrega(BigInteger a, Long B,String latitud, String longitud);
        void ProcesarRechazo(BigInteger a, Long B);
        void OnErrorCharge(int a);
        void OnSucessCharge();
        void OnClear(int a);
        void OnEntrega (int a);
        void OnEntregaError(int a);
        void OnRechazo (int a);
    }

    interface Model{
        void ExtaerEntrega(Long a);
        void ProcesoDeEntrega(BigInteger a, Long B,String latitud, String longitud);
        void ProcesoDeRechazo(BigInteger a, Long B);
    }
}
