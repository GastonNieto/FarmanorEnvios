package com.genv3.gendelivery.Objects;

import java.io.Serializable;
import java.math.BigInteger;

public class Tarifas implements Serializable {
    private BigInteger tfsId;
    private String tfsNombre;
    private Double tfsImporte;
    private int tfsEstado;

    public Tarifas() {
    }

    public BigInteger getTfsId() {
        return tfsId;
    }

    public void setTfsId(BigInteger tfsId) {
        this.tfsId = tfsId;
    }

    public String getTfsNombre() {
        return tfsNombre;
    }

    public void setTfsNombre(String tfsNombre) {
        this.tfsNombre = tfsNombre;
    }

    public Double getTfsImporte() {
        return tfsImporte;
    }

    public void setTfsImporte(Double tfsImporte) {
        this.tfsImporte = tfsImporte;
    }

    public int getTfsEstado() {
        return tfsEstado;
    }

    public void setTfsEstado(int tfsEstado) {
        this.tfsEstado = tfsEstado;
    }
}
