package com.genv3.gendelivery.Interface;

import java.math.BigInteger;

public interface ITomarPedidos {
    interface View{
        void onTomado(int a);
        void onOcupado(int a);
        void onError(int a);
        void onPedidoPendiente(int a);
    }
    interface Presener{
        void SolicitarPedidoLibre (Long pedido, BigInteger cadete ,String estado);
        void onTomado(int a);
        void onOcupado(int a);
        void onError(int a);
        void onPedidoPendiente(int a);

    }
    interface Model{
        void CargarPedido(Long pedido, BigInteger cadete ,String estado);
    }
}
