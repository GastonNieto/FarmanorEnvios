package com.genv3.gendelivery.Objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

public class EntregasTomadas implements Serializable {
    private BigInteger etgtId;
    private BigInteger etgdId;
    private ArrayList<EntregasDisponibles> entregasDisponibles;
    private BigInteger cdtId;
    private ArrayList<Cadetes> Cadete;
    private BigInteger tfsId;
    private ArrayList<Tarifas> tarifas;
    private Double etgtImporte;
    private String etgtFechaTomada;
    private String etgtFechaEntrega;
    private String etgtLatitud;
    private String etgtLongitud;
    private String etgtEstado;

    public BigInteger getTfsId() {
        return tfsId;
    }

    public void setTfsId(BigInteger tfsId) {
        this.tfsId = tfsId;
    }

    public ArrayList<Tarifas> getTarifas() {
        return tarifas;
    }

    public void setTarifas(ArrayList<Tarifas> tarifas) {
        this.tarifas = tarifas;
    }

    public Double getEtgtImporte() {
        return etgtImporte;
    }

    public void setEtgtImporte(Double etgtImporte) {
        this.etgtImporte = etgtImporte;
    }

    public BigInteger getEtgtId() {
        return etgtId;
    }

    public void setEtgtId(BigInteger etgtId) {
        this.etgtId = etgtId;
    }

    public BigInteger getEtgdId() {
        return etgdId;
    }

    public void setEtgdId(BigInteger etgdId) {
        this.etgdId = etgdId;
    }

    public ArrayList<EntregasDisponibles> getEntregasDisponibles() {
        return entregasDisponibles;
    }

    public void setEntregasDisponibles(ArrayList<EntregasDisponibles> entregasDisponibles) {
        this.entregasDisponibles = entregasDisponibles;
    }

    public BigInteger getCdtId() {
        return cdtId;
    }

    public void setCdtId(BigInteger cdtId) {
        this.cdtId = cdtId;
    }

    public ArrayList<Cadetes> getCadete() {
        return Cadete;
    }

    public void setCadete(ArrayList<Cadetes> cadete) {
        Cadete = cadete;
    }

    public String getEtgtFechaTomada() {
        return etgtFechaTomada;
    }

    public void setEtgtFechaTomada(String etgtFechaTomada) {
        this.etgtFechaTomada = etgtFechaTomada;
    }

    public String getEtgtFechaEntrega() {
        return etgtFechaEntrega;
    }

    public void setEtgtFechaEntrega(String etgtFechaEntrega) {
        this.etgtFechaEntrega = etgtFechaEntrega;
    }

    public String getEtgtLatitud() {
        return etgtLatitud;
    }

    public void setEtgtLatitud(String etgtLatitud) {
        this.etgtLatitud = etgtLatitud;
    }

    public String getEtgtLongitud() {
        return etgtLongitud;
    }

    public void setEtgtLongitud(String etgtLongitud) {
        this.etgtLongitud = etgtLongitud;
    }

    public String getEtgtEstado() {
        return etgtEstado;
    }

    public void setEtgtEstado(String etgtEstado) {
        this.etgtEstado = etgtEstado;
    }

    public EntregasTomadas() {
    }

}
