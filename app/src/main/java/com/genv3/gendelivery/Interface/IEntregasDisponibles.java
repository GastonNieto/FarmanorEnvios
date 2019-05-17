package com.genv3.gendelivery.Interface;

import com.genv3.gendelivery.Adapter.AdapterPedidos;
import com.genv3.gendelivery.Objects.EntregasDisponibles;

import java.util.ArrayList;

public interface IEntregasDisponibles {
    interface View{
        void cargarRecycler( ArrayList<EntregasDisponibles> entregasDisponibles);
        void OnErrorCharge(int a);
        void OnSucessCharge();
        void OnClear(int a);
    }
    interface Presenter{
        void SolicitarPedidos();
        void RecibirPedidos( ArrayList<EntregasDisponibles> entregasDisponibles);
        void OnErrorCharge(int a);
        void OnSucessCharge();
        void OnClear(int a);
    }
    interface Model{
        void ExtraerPedidos();
    }
}
