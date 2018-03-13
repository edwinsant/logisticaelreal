package com.inventario.acreal.floatbutton.Models;

/**
 * Created by crodriguez on 25/07/2017.
 */

public class Picking {
    int id;
    String BODEGA, FECHA, TRANSPORTISTA,CODIGO_CORTO,ARTICULO, DESCRIPCION, NLINEA, LINEA, UBICACION, LOTE, UMT, UMS;
    String NCARGA, NPEDIDOS, COD_VERIFICACION, SECUENCIA, CANT_TOTAL_UMP, CANT_TOTAL_UMS, FACTOR, CANT_UMS, UNIDAD_SUELTAS;
    String estado, existencia, disponibles,COMP_FIRME_ORD_VTA, COMP_FLEX_OV_OT, COMP_FIRME_OT, Existencia;

    public Picking(){

    }


    /****************************get***********************************/
    public int getId() {
        return id;
    }
    public String getARTICULO() {
        return ARTICULO;
    }
    public String getBODEGA() {
        return BODEGA;
    }
    public String getCODIGO_CORTO() {
        return CODIGO_CORTO;
    }
    public String getDESCRIPCION() {
        return DESCRIPCION;
    }
    public String getFECHA() {
        return FECHA;
    }
    public String getLINEA() {
        return LINEA;
    }
    public String getNLINEA() {
        return NLINEA;
    }
    public String getTRANSPORTISTA() {
        return TRANSPORTISTA;
    }
    public String getLOTE() {
        return LOTE;
    }
    public String getUBICACION() {
        return UBICACION;
    }
    public String getCANT_TOTAL_UMP() {
        return CANT_TOTAL_UMP;
    }
    public String getCANT_TOTAL_UMS() {
        return CANT_TOTAL_UMS;
    }
    public String getCANT_UMS() {
        return CANT_UMS;
    }
    public String getCOD_VERIFICACION() {
        return COD_VERIFICACION;
    }
    public String getUMS() {
        return UMS;
    }
    public String getFACTOR() {
        return FACTOR;
    }
    public String getNCARGA() {
        return NCARGA;
    }
    public String getNPEDIDOS() {
        return NPEDIDOS;
    }
    public String getUMT() {
        return UMT;
    }
    public String getSECUENCIA() {
        return SECUENCIA;
    }
    public String getUNIDAD_SUELTAS() {
        return UNIDAD_SUELTAS;
    }
    public String getEstado() {
        return estado;
    }
    public String getExistencia() {
        return existencia;
    }
    public String getDisponibles() {
        return disponibles;
    }
    public String getCOMP_FLEX_OV_OT() {
        return COMP_FLEX_OV_OT;
    }
    public String getCOMP_FIRME_ORD_VTA() {
        return COMP_FIRME_ORD_VTA;
    }
    public String getCOMP_FIRME_OT() {
        return COMP_FIRME_OT;
    }

    /****************************set***********************************/
    public void setARTICULO(String ARTICULO) {
        this.ARTICULO = ARTICULO;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setBODEGA(String BODEGA) {
        this.BODEGA = BODEGA;
    }
    public void setCODIGO_CORTO(String CODIGO_CORTO) {
        this.CODIGO_CORTO = CODIGO_CORTO;
    }
    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }
    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }
    public void setLINEA(String LINEA) {
        this.LINEA = LINEA;
    }
    public void setLOTE(String LOTE) {
        this.LOTE = LOTE;
    }
    public void setNLINEA(String NLINEA) {
        this.NLINEA = NLINEA;
    }
    public void setTRANSPORTISTA(String TRANSPORTISTA) {
        this.TRANSPORTISTA = TRANSPORTISTA;
    }
    public void setUBICACION(String UBICACION) {
        this.UBICACION = UBICACION;
    }
    public void setNCARGA(String NCARGA) {
        this.NCARGA = NCARGA;
    }
    public void setUMS(String UMS) {
        this.UMS = UMS;
    }
    public void setCANT_TOTAL_UMP(String CANT_TOTAL_UMP) {
        this.CANT_TOTAL_UMP = CANT_TOTAL_UMP;
    }
    public void setCANT_TOTAL_UMS(String CANT_TOTAL_UMS) {
        this.CANT_TOTAL_UMS = CANT_TOTAL_UMS;
    }
    public void setCANT_UMS(String CANT_UMS) {
        this.CANT_UMS = CANT_UMS;
    }
    public void setUMT(String UMT) {
        this.UMT = UMT;
    }
    public void setCOD_VERIFICACION(String COD_VERIFICACION) {
        this.COD_VERIFICACION = COD_VERIFICACION;
    }
    public void setFACTOR(String FACTOR) {
        this.FACTOR = FACTOR;
    }
    public void setNPEDIDOS(String NPEDIDOS) {
        this.NPEDIDOS = NPEDIDOS;

    }
    public void setSECUENCIA(String SECUENCIA) {
        this.SECUENCIA = SECUENCIA;
    }
    public void setUNIDAD_SUELTAS(String UNIDAD_SUELTAS) {
        this.UNIDAD_SUELTAS = UNIDAD_SUELTAS;
    }
    public void setCOMP_FIRME_ORD_VTA(String COMP_FIRME_ORD_VTA) {
        this.COMP_FIRME_ORD_VTA = COMP_FIRME_ORD_VTA;
    }
    public void setCOMP_FIRME_OT(String COMP_FIRME_OT) {
        this.COMP_FIRME_OT = COMP_FIRME_OT;
    }
    public void setCOMP_FLEX_OV_OT(String COMP_FLEX_OV_OT) {
        this.COMP_FLEX_OV_OT = COMP_FLEX_OV_OT;
    }
    public void setDisponibles(String disponibles) {
        this.disponibles = disponibles;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }
}
