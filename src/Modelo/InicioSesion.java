package Modelo;


import java.io.*;
import java.util.LinkedList;

public class InicioSesion {
    // Función para cargar datos del archivo
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

    // Función para validar credenciales
    public ResultadoValidacion validarCredenciales(String nombreArchivo, String usuarioIngresado, String claveIngresada) {
        try {
            // Validar que los campos no estén vacíos
            if (usuarioIngresado == null || usuarioIngresado.trim().isEmpty() ||
                    claveIngresada == null || claveIngresada.isEmpty()) {
                return new ResultadoValidacion(false, "Por favor complete todos los campos");
            }

            // Cargar la lista de usuarios del archivo
            LinkedList<Usuario> listaUsuarios = cargarDatos(nombreArchivo);

            // Validar si el usuario existe y la contraseña corresponde
            for (Usuario usuario : listaUsuarios) {
                if (usuario.getUsuario().equals(usuarioIngresado.trim())) {
                    if (usuario.getClave().equals(claveIngresada)) {
                        return new ResultadoValidacion(true, "Inicio de sesión exitoso");
                    } else {
                        return new ResultadoValidacion(false, "Contraseña incorrecta");
                    }
                }
            }

            // Si llegamos aquí, el usuario no existe
            return new ResultadoValidacion(false, "Usuario no encontrado");

        } catch (Exception ex) {
            return new ResultadoValidacion(false, "Error durante la validación: " + ex.getMessage());
        }
    }

    // Clase para encapsular el resultado de la validación
    public static class ResultadoValidacion {
        private boolean exitoso;
        private String mensaje;

        public ResultadoValidacion(boolean exitoso, String mensaje) {
            this.exitoso = exitoso;
            this.mensaje = mensaje;
        }

        public boolean isExitoso() {
            return exitoso;
        }

        public String getMensaje() {
            return mensaje;
        }
    }
}