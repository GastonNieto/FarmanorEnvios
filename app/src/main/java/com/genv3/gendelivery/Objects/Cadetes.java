package com.genv3.gendelivery.Objects;

import java.io.Serializable;
import java.math.BigInteger;

public class Cadetes implements Serializable {
    private Long cdtId;
    private String cdtNom;
    private String cdtApe;
    private Integer cdtDni;
    private BigInteger cdtTel;
    private String cdtDir;
    private String cdtUsr;
    private String cdtPwd;
    private Integer cdtEst;

    public Cadetes() {
    }

    public Long getCdtId() {
        return cdtId;
    }

    public void setCdtId(Long cdtId) {
        this.cdtId = cdtId;
    }

    public String getCdtNom() {
        return cdtNom;
    }

    public void setCdtNom(String cdtNom) {
        this.cdtNom = cdtNom;
    }

    public String getCdtApe() {
        return cdtApe;
    }

    public void setCdtApe(String cdtApe) {
        this.cdtApe = cdtApe;
    }

    public Integer getCdtDni() {
        return cdtDni;
    }

    public void setCdtDni(Integer cdtDni) {
        this.cdtDni = cdtDni;
    }

    public BigInteger getCdtTel() {
        return cdtTel;
    }

    public void setCdtTel(BigInteger cdtTel) {
        this.cdtTel = cdtTel;
    }

    public String getCdtDir() {
        return cdtDir;
    }

    public void setCdtDir(String cdtDir) {
        this.cdtDir = cdtDir;
    }

    public String getCdtUsr() {
        return cdtUsr;
    }

    public void setCdtUsr(String cdtUsr) {
        this.cdtUsr = cdtUsr;
    }

    public String getCdtPwd() {
        return cdtPwd;
    }

    public void setCdtPwd(String cdtPwd) {
        this.cdtPwd = cdtPwd;
    }

    public Integer getCdtEst() {
        return cdtEst;
    }

    public void setCdtEst(Integer cdtEst) {
        this.cdtEst = cdtEst;
    }
}
