package com.mycompany.proyecto_login;

import java.util.HashMap;
import java.util.Map;

public class UserStore {

    private Map<String, Usuario> usuarios = new HashMap<>();

    public UserStore() {
        // Usuario de prueba por defecto
        usuarios.put("admin", new Usuario("admin", "1234", Rol.ADMIN));
    }

    public synchronized boolean existeUsuario(String user) {
        return usuarios.containsKey(user.toLowerCase());
    }

    public synchronized boolean crearUsuario(String user, String pass, Rol rol) {
        String key = user.toLowerCase();
        if (usuarios.containsKey(key)) {
            return false;
        }
        usuarios.put(key, new Usuario(user, pass, rol));
        return true;
    }

    public synchronized Usuario validarLogin(String user, String pass) {
        Usuario u = usuarios.get(user.toLowerCase());
        if (u != null && u.getPassword().equals(pass)) {
            return u;
        }
        return null;
    }
}
