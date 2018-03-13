package com.inventario.acreal.floatbutton.Models;

/**
 * Created by crodriguez on 07/04/2017.
 */

public class Product {
    String Producto, Cantidad, UM, Lote, UbicacionO, UbicacionD, Seguridad;

    public Product(){}

    public Product(String producto, String ubicacionO) {
        Producto = producto;
        UbicacionO = ubicacionO;
    }

    //*************************Get***************************************//
    public String getProducto(){return Producto;}
    public String getCantidad(){return Cantidad;}
    public String getUM(){return UM;}
    public String getLote(){return Lote;}
    public String getUbicacionO(){return UbicacionO;}
    public String getUbicacionD(){return UbicacionD;}
    public String getSeguridad(){return Seguridad;}
    //*************************Set***************************************//
    public void setProducto(String producto){
        this.Producto = producto;
    }
    public void setCantidad(String cantidad){
        this.Cantidad = cantidad;
    }
    public void setUM(String um){
        this.UM = um;
    }
    public void setLote(String lote){
        this.Lote = lote;
    }
    public void setUbicacionO(String ubicacionO){
        this.UbicacionO = ubicacionO;
    }
    public void setUbicacionD(String ubicacionD){this.UbicacionD = ubicacionD;}
    public void setSeguridad(String seguridad){this.Seguridad = seguridad;}
}
