package com.inventario.acreal.floatbutton.Models;

/**
 * Created by crodriguez on 07/04/2017.
 */

public class Move {

    String No_Tarea;
    String Usuario;
    String Bodega;
    String UbicacionO;
    String Lote;
    String Articulo;
    String Descripcion;
    String UMPallet;
    String Cantidad_UMP;
    String UMT;
    String Cantidad_UMT;
    String Factor_Conv_UMT;
    String UbicacionD;
    String Seguridad;
    String Fechavencimiento;
    String Cod_cip;

    public String getCod_cip() {
        return Cod_cip;
    }

    public void setCod_cip(String cod_cip) {
        Cod_cip = cod_cip;
    }


    public Move(){
    }


    //////////////Get//////////////////
    public String getUsuario(){return Usuario;}
    public String getBodega(){return Bodega;}
    public String getUbicacionO(){return UbicacionO;}
    public String getUbicacionD(){return UbicacionD;}
    public String getNo_Tarea(){return No_Tarea;}
    public String getSeguridad(){return Seguridad;}
    public String getCantidad_UMT(){return Cantidad_UMT;}
    public String getLote(){return Lote;}
    public String getArticulo(){return Articulo;}
    public String getFactor_Conv_UMT(){return Factor_Conv_UMT;}
    public String getUMPallet(){return UMPallet;}
    public String getFechavencimiento(){return Fechavencimiento;}
    public String getDescripcion(){return Descripcion;}
    public String getCantidad_UMP(){return Cantidad_UMP;}
    public String getUMT(){return UMT;}
    ///////////////Set/////////////////
    public void setUsuario(String usuario){
        this.Usuario = usuario;
    }
    public void setBodega(String bodega){
        this.Bodega = bodega;
    }
    public void setUbicacionO(String ubicacionO){
        this.UbicacionO = ubicacionO;
    }
    public void setUbicacionD(String ubicacionD){
        this.UbicacionD = ubicacionD;
    }
    public void setNo_Tarea(String no_Tarea){
        this.No_Tarea = no_Tarea;
    }
    public void setSeguridad(String seguridad){
        this.Seguridad = seguridad;
    }
    public void setCantidad_UMT(String cantidad_UMT){
        this.Cantidad_UMT = cantidad_UMT;
    }
    public void setUMT(String umt){this.UMT = umt;}
    public void setLote(String lote){
        this.Lote = lote;
    }
    public void setArticulo(String articulo){
        this.Articulo = articulo;
    }
    public void setCantidad_UMP(String cantidad_ump){this.Cantidad_UMP = cantidad_ump;}
    public void setFactor_Conv_UMT(String factor_Conv_UMT){
        this.Factor_Conv_UMT = factor_Conv_UMT;
    }
    public void setUMPallet(String UMPallet){
        this.UMPallet = UMPallet;
    }
    public void setFechavencimiento(String fechavencimiento){
        this.Fechavencimiento = fechavencimiento;
    }
    public void setDescripcion(String descripcion){this.Descripcion = descripcion;}
}
