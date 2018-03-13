package com.inventario.acreal.floatbutton.Models;

/**
 * Created by dsiezar on 20/04/2017.
 */

public class User {
    String Usuario;
    int Activo;
    public User(){}

    /*************Get**************/
    public int getActivo() {
        return Activo;
    }

    public String getUsuario() {
        return Usuario;
    }
    /**********Set*************/
    public void setActivo(int activo) {
        Activo = activo;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
}
