package Controlador;

import Modelo.Asiento;
import Modelo.Cliente;
import Modelo.GestorCliente;
import Modelo.Sala;
import Vista.ConfirmacionVentaVentana;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorConfirmacionPagos implements ActionListener {

    private ConfirmacionVentaVentana ventana;
    private List<Asiento> asientosSeleccionados;
    private Sala salaSeleccionada;
    private ControladorPrincipal controladorPrincipal;
    private GestorCliente gestorClientes;

    public ControladorConfirmacionPagos() {
        ventana = new ConfirmacionVentaVentana();
        try {
            gestorClientes = new GestorCliente();
        } catch (Exception e) {
            System.err.println("Error al inicializar gestor de clientes: " + e.getMessage());
            gestorClientes = null;
        }
        configurarEventos();
    }

    private void configurarEventos() {
        if (ventana.getBtnConfirmar() != null) {
            ventana.getBtnConfirmar().addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventana.getBtnConfirmar()) {
            procesarConfirmacionPago();
        }
    }

    private void procesarConfirmacionPago() {
        try {
            // Validar que todos los objetos necesarios estén inicializados
            if (ventana == null) {
                mostrarError("Error: Ventana no inicializada.");
                return;
            }

            if (asientosSeleccionados == null || asientosSeleccionados.isEmpty()) {
                mostrarError("Error: No hay asientos seleccionados.");
                return;
            }

            if (salaSeleccionada == null) {
                mostrarError("Error: No hay sala seleccionada.");
                return;
            }

            if (gestorClientes == null) {
                mostrarError("Error: Sistema de clientes no disponible.");
                return;
            }

            // Validar datos del cliente
            if (!validarDatosCliente()) {
                return;
            }

            // Procesar reserva de asientos
            boolean exitoso = actualizarAsientosEnSala();

            if (exitoso) {
                // Procesar la compra del cliente
                procesarCompraCliente();

                mostrarConfirmacion("¡Pago confirmado exitosamente!\n" +
                        "Asientos reservados: " + obtenerNumerosAsientos() + "\n" +
                        "Sala: " + salaSeleccionada.getNombre() + "\n" +
                        "Total pagado: $" + String.format("%.2f", calcularTotal()));

                ventana.dispose();

                // Volver al dashboard si el controlador principal está disponible
                if (controladorPrincipal != null) {
                    controladorPrincipal.volveraDashboard(ventana);
                }
            } else {
                mostrarError("Error al procesar la reserva. Algunos asientos pueden no estar disponibles.");
            }

        } catch (NullPointerException npe) {
            mostrarError("Error de referencia nula: " + (npe.getMessage() != null ? npe.getMessage() : "Objeto no inicializado"));
            npe.printStackTrace(); // Para debugging
        } catch (Exception ex) {
            mostrarError("Error inesperado al confirmar el pago: " +
                    (ex.getMessage() != null ? ex.getMessage() : "Error desconocido"));
            ex.printStackTrace(); // Para debugging
        }
    }

    private boolean validarDatosCliente() {
        try {
            if (ventana.getTxtNombre() == null || ventana.getTxtCedula() == null || ventana.getTxtCorreo() == null) {
                mostrarError("Error: Campos de texto no inicializados.");
                return false;
            }

            String nombre = ventana.getTxtNombre().getText();
            String cedula = ventana.getTxtCedula().getText();
            String correo = ventana.getTxtCorreo().getText();

            // Validar que los valores no sean null
            if (nombre == null) nombre = "";
            if (cedula == null) cedula = "";
            if (correo == null) correo = "";

            // Trim para eliminar espacios
            nombre = nombre.trim();
            cedula = cedula.trim();
            correo = correo.trim();

            if (nombre.isEmpty()) {
                mostrarError("El nombre no puede estar vacío.");
                return false;
            }

            if (cedula.isEmpty()) {
                mostrarError("La cédula no puede estar vacía.");
                return false;
            }

            if (correo.isEmpty()) {
                mostrarError("El correo no puede estar vacío.");
                return false;
            }

            // Validación básica de correo
            if (!correo.contains("@") || !correo.contains(".")) {
                mostrarError("El correo electrónico no tiene un formato válido.");
                return false;
            }

            return true;
        } catch (Exception e) {
            mostrarError("Error al validar datos del cliente: " + e.getMessage());
            return false;
        }
    }

    private void procesarCompraCliente() {
        try {
            String nombre = ventana.getTxtNombre().getText().trim();
            String cedula = ventana.getTxtCedula().getText().trim();
            String correo = ventana.getTxtCorreo().getText().trim();
            Float montoCompra = calcularTotal();

            gestorClientes.procesarCompra(nombre, cedula, correo, montoCompra);
        } catch (Exception e) {
            System.err.println("Error al procesar compra del cliente: " + e.getMessage());
            // No mostrar error al usuario ya que la reserva se completó
        }
    }

    private Float calcularTotal() {
        if (asientosSeleccionados != null && !asientosSeleccionados.isEmpty()) {
            return asientosSeleccionados.size() * 10.0F;
        }
        return 0.0F;
    }

    private void calcularYMostrarTotal() {
        try {
            double totalPagar = calcularTotal();
            if (ventana != null && ventana.getLblTotalPagar() != null) {
                ventana.getLblTotalPagar().setText("$" + String.format("%.2f", totalPagar));
            }
        } catch (Exception e) {
            System.err.println("Error al calcular total: " + e.getMessage());
        }
    }

    private boolean actualizarAsientosEnSala() {
        try {
            // Verificar que la sala no esté en mantenimiento
            if (salaSeleccionada.isEnMantenimiento()) {
                mostrarError("La sala seleccionada está en mantenimiento");
                return false;
            }

            for (Asiento asiento : asientosSeleccionados) {
                if (asiento == null) {
                    mostrarError("Error: Asiento no válido encontrado.");
                    return false;
                }

                // Verificar que el asiento esté disponible antes de ocuparlo
                if (!asiento.isOcupado()) {
                    boolean reservado = salaSeleccionada.reservarAsiento(asiento.getId());
                    if (reservado) {
                        asiento.setSeleccionado(false);
                    } else {
                        return false;
                    }
                } else {
                    // Si algún asiento ya no está disponible, devolver false
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar asientos: " + e.getMessage());
            return false;
        }
    }

    private String obtenerNumerosAsientos() {
        try {
            if (asientosSeleccionados == null || asientosSeleccionados.isEmpty()) {
                return "Ninguno";
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < asientosSeleccionados.size(); i++) {
                Asiento asiento = asientosSeleccionados.get(i);
                if (asiento != null) {
                    sb.append(asiento.getId());
                    if (i < asientosSeleccionados.size() - 1) {
                        sb.append(", ");
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Error al obtener números de asientos: " + e.getMessage());
            return "Error al obtener asientos";
        }
    }

    private void mostrarError(String mensaje) {
        try {
            if (ventana != null) {
                JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.err.println("Error: " + mensaje);
            }
        } catch (Exception e) {
            System.err.println("Error al mostrar mensaje: " + mensaje);
        }
    }

    private void mostrarConfirmacion(String mensaje) {
        try {
            if (ventana != null) {
                JOptionPane.showMessageDialog(ventana, mensaje, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Confirmación: " + mensaje);
            }
        } catch (Exception e) {
            System.err.println("Error al mostrar confirmación: " + mensaje);
        }
    }

    // Getters y Setters
    public ConfirmacionVentaVentana getVentana() {
        return ventana;
    }

    public void setControladorPrincipal(ControladorPrincipal controlador) {
        this.controladorPrincipal = controlador;
    }

    public void prepararConfirmacion(List<Asiento> asientosSeleccionados, Sala sala) {
        try {
            this.asientosSeleccionados = asientosSeleccionados;
            this.salaSeleccionada = sala;

            // Validar que los parámetros no sean null
            if (asientosSeleccionados == null) {
                System.err.println("Warning: Lista de asientos es null");
                return;
            }

            if (sala == null) {
                System.err.println("Warning: Sala es null");
                return;
            }

            // Calcular y mostrar el total
            calcularYMostrarTotal();

            System.out.println("Preparando confirmación para " + asientosSeleccionados.size() +
                    " asientos en sala: " + sala.getNombre());
        } catch (Exception e) {
            System.err.println("Error al preparar confirmación: " + e.getMessage());
        }
    }

    // Método adicional para consultar información de un cliente
    public String consultarInfoCliente(String cedula) {
        try {
            if (gestorClientes != null) {
                return gestorClientes.obtenerInfoCliente(cedula);
            }
            return "Sistema de clientes no disponible";
        } catch (Exception e) {
            return "Error al consultar cliente: " + e.getMessage();
        }
    }
}