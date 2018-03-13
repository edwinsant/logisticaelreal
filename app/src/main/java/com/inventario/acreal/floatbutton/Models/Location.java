package com.inventario.acreal.floatbutton.Models;

/**
 * Created by crodriguez on 07/04/2017.
 */

public class Location {
    String UbucaionO, UbicacionD, Seguridad;
    public Location(){}

    //*************get*****************************//
    public String getUbucaionO(){return UbucaionO;}
    public String getUbicacionD(){return UbicacionD;}
    public String getSeguridad(){return Seguridad;}

    //***************************set*************************//
    public void setUbucaionO(String ubicacionO){
        this.UbucaionO = ubicacionO;
    }
    public void setUbicacionD(String ubicacionD){
        this.UbicacionD = ubicacionD;
    }
    public void setSeguridad(String seguridad){
        this.Seguridad = seguridad;
    }
}

