package Modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
    String nombre;
    String contrasena;
    Rol rol;

    public Usuario(String nombre, String contrasena, Rol rol) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Getters y setters
    public String getUsuario() {
        return nombre;
    }
    // Getters y setters
    public String getClave() {
        return contrasena;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
