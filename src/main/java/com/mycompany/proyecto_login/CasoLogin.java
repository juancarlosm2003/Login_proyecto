/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_login;

/**
 *
 * @author aleja
 */
public class CasoLogin {
    private Usuario usuario;
    private int num;

    public CasoLogin(Usuario usuario, int num) {
        this.usuario = usuario;
        this.num = num;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    
}
