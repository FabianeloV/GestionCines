package Controlador;

import Modelo.Rol;
import Modelo.Usuario;
import Vista.VentanaNuevosUsuarios;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;

public class ControladorCrearUsuarios {
    VentanaNuevosUsuarios vista;
    private final String ARCHIVO_USUARIOS = "datosUsuario.dat";

    public ControladorCrearUsuarios(VentanaNuevosUsuarios vista) {
        this.vista = vista;
    }

    public LinkedList<Usuario> cargarDatos(String nombreArchivo) {
        LinkedList<Usuario> listaUsuarios = new LinkedList<>();

        try {
            ObjectInputStream archivo = new ObjectInputStream(new FileInputStream(nombreArchivo));
            listaUsuarios = (LinkedList<Usuario>) archivo.readObject();
            archivo.close();

        } catch (FileNotFoundException ex) {
            // Si el archivo no existe, retorna una lista vacía
            System.out.println("Archivo no encontrado, creando nueva lista");
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException("Error al cargar datos: " + ex.getMessage());
        }

        return listaUsuarios;
    }

    // Método para registrar un nuevo usuario (opcional)
    public void registrarUsuario() {
        try {
            String usuario = vista.getUsuarioIngresado();
            String clave = vista.getClaveIngresada();
            Rol rol = vista.getRol();

            if (usuario.isEmpty() || clave.isEmpty()) {
                vista.mostrarMensaje("Por favor complete todos los campos", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            grabarDatos(ARCHIVO_USUARIOS, usuario, clave, rol);
            vista.mostrarMensaje("Usuario registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampos();

        } catch (Exception ex) {
            vista.mostrarMensaje("Error al registrar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Función para grabar datos de usuario
    public void grabarDatos(String nombreArchivo, String usuario, String clave, Rol rol) {
        try {
            // Cargar datos existentes o crear nueva lista
            LinkedList<Usuario> listaUsuarios = cargarDatos(nombreArchivo);

            // Crear objeto para almacenar los datos
            Usuario datosUsuario = new Usuario(usuario, clave, rol);

            // Agregar el nuevo usuario a la lista
            listaUsuarios.add(datosUsuario);

            // Crear el stream de salida
            ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream(nombreArchivo));

            // Escribir la lista completa al archivo
            archivo.writeObject(listaUsuarios);
            archivo.flush();
            archivo.close();

        } catch (IOException ex) {
            throw new RuntimeException("Error al grabar datos: " + ex.getMessage());
        }
    }
}
