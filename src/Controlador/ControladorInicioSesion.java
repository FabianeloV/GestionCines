package Controlador;

import Modelo.InicioSesion;
import Modelo.Rol;
import Modelo.Usuario;
import Vista.InicioSesionVentana;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorInicioSesion {
    private InicioSesionVentana vista;
    private InicioSesion modelo;
    private final String ARCHIVO_USUARIOS = "datosUsuario.dat";

    public ControladorInicioSesion(InicioSesionVentana vista) {
        this.vista = vista;
        this.modelo = new InicioSesion();
    }

    public Boolean validarLogin(Usuario usuarioActual) {
        try {
            // Obtener datos de la vista
            String usuario = vista.getUsuarioIngresado();
            String clave = vista.getClaveIngresada();

            // Validar credenciales usando el modelo
            InicioSesion.ResultadoValidacion resultado = modelo.validarCredenciales(ARCHIVO_USUARIOS, usuario, clave, usuarioActual);

            if (resultado.isExitoso()) {
                // Login exitoso
                vista.mostrarMensaje(resultado.getMensaje(), "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
                vista.limpiarCampos();
                vista.dispose(); // Cerrar ventana de login
                return true;

            } else {
                // Login fallido
                vista.mostrarMensaje(resultado.getMensaje(), "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                vista.limpiarCampos(); // Limpiar campos por seguridad
                return false;
            }

        } catch (Exception ex) {
            vista.mostrarMensaje("Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }
}
