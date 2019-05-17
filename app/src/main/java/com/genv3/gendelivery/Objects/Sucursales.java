package com.genv3.gendelivery.Objects;

import java.io.Serializable;
import java.math.BigInteger;

public class Sucursales implements Serializable {
    private BigInteger sclId;
    private String sclNom;
    private String sclDir;
    private BigInteger sclTel;

    public Sucursales() {
    }

    public BigInteger getSclId() {
        return sclId;
    }

    public void setSclId(BigInteger sclId) {
        this.sclId = sclId;
    }

    public String getSclNom() {
        return sclNom;
    }

    public void setSclNom(String sclNom) {
        this.sclNom = sclNom;
    }

    public String getSclDir() {
        return sclDir;
    }

    public void setSclDir(String sclDir) {
        this.sclDir = sclDir;
    }

    public BigInteger getSclTel() {
        return sclTel;
    }

    public void setSclTel(BigInteger sclTel) {
        this.sclTel = sclTel;
    }
}
