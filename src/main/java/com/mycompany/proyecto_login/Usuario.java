package com.mycompany.proyecto_login;

public class Usuario {

    private String usuario;
    private String password;
    private Rol rol;

    public Usuario(String usuario, String password, Rol rol) {
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public Rol getRol() {
        return rol;
    }
}
