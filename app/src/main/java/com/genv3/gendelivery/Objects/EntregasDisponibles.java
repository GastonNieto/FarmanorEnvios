package com.genv3.gendelivery.Objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Date;

public class EntregasDisponibles implements Serializable {
    private BigInteger etgdId;
    private BigInteger etgdIdSucursal;
    private ArrayList<Sucursales> Sucursal;
    private String etgdIdComprobante;
    private String etgdReceptor;
    private String etgdPedido;
    private String etgdDir;
    private BigInteger etgdTel;
    private String etgdFecha;
    private int etgdEstado;

    public ArrayList<Sucursales> getSucursal() {
        return Sucursal;
    }

    public void setSucursal(ArrayList<Sucursales> sucursal) {
        Sucursal = sucursal;
    }

    public BigInteger getEtgdIdSucursal() {
        return etgdIdSucursal;
    }

    public void setEtgdIdSucursal(BigInteger etgdIdSucursal) {
        this.etgdIdSucursal = etgdIdSucursal;
    }

    public EntregasDisponibles() {
    }

    public BigInteger getEtgdId() {
        return etgdId;
    }

    public void setEtgdId(BigInteger etgdId) {
        this.etgdId = etgdId;
    }

    public String getEtgdIdComprobante() {
        return etgdIdComprobante;
    }

    public void setEtgdIdComprobante(String etgdIdComprobante) {
        this.etgdIdComprobante = etgdIdComprobante;
    }

    public String getEtgdReceptor() {
        return etgdReceptor;
    }

    public void setEtgdReceptor(String etgdReceptor) {
        this.etgdReceptor = etgdReceptor;
    }

    public String getEtgdPedido() {
        return etgdPedido;
    }

    public void setEtgdPedido(String etgdPedido) {
        this.etgdPedido = etgdPedido;
    }

    public String getEtgdDir() {
        return etgdDir;
    }

    public void setEtgdDir(String etgdDir) {
        this.etgdDir = etgdDir;
    }

    public BigInteger getEtgdTel() {
        return etgdTel;
    }

    public void setEtgdTel(BigInteger etgdTel) {
        this.etgdTel = etgdTel;
    }

    public String getEtgdFecha() {
        return etgdFecha;
    }

    public void setEtgdFecha(String etgdFecha) {
        this.etgdFecha = etgdFecha;
    }

    public int getEtgdEstado() {
        return etgdEstado;
    }

    public void setEtgdEstado(int etgdEstado) {
        this.etgdEstado = etgdEstado;
    }
}
