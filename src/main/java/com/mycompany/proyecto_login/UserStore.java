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

    public synchronized CasoLogin validarLogin(String user, String pass) {
        Usuario u = usuarios.get(user.toLowerCase());
        CasoLogin caso = new CasoLogin(u, 1);
        if (u != null && u.getPassword().equals(pass)) {
            return caso;
        }
        else if(u != null && u.getPassword().equals("++++")){
            caso.setNum(2);
            return caso;
        }else{
             return null;
        }
       
    }
    
  public boolean actualizarPassword(String username, String nuevaPass) {
    String key = username.toLowerCase();
    Usuario u = usuarios.get(key);
    if (u != null) {
        u.setPassword(nuevaPass);
        return true;
    }
    return false;
}


    
}
