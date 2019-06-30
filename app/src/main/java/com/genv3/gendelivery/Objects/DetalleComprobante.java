package com.genv3.gendelivery.Objects;

import java.io.Serializable;

public class DetalleComprobante implements Serializable {
    private String des_articulo;
    private String cantidad;

    public DetalleComprobante() {
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDes_articulo() {
        return des_articulo;
    }

    public void setDes_articulo(String des_articulo) {
        this.des_articulo = des_articulo;
    }
}
