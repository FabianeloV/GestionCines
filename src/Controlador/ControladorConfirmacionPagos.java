package Controlador;

import Modelo.Asiento;
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

    public ControladorConfirmacionPagos() {
        ventana = new ConfirmacionVentaVentana();
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
            boolean exitoso = actualizarAsientosEnSala();

            if (exitoso) {
                mostrarConfirmacion("¡Pago confirmado exitosamente!\n" +
                        "Asientos reservados: " + obtenerNumerosAsientos() + "\n" +
                        "Sala: " + salaSeleccionada.getNombre() + "\n");

                ventana.dispose();
                //volvemos al dashboard despues de venta
                controladorPrincipal.volveraDashboard(ventana);

            } else {
                mostrarError("Error al procesar la reserva. Algunos asientos pueden no estar disponibles.");
            }

        } catch (Exception ex) {
            mostrarError("Error inesperado al confirmar el pago: " + ex.getMessage());
        }
    }

    private boolean actualizarAsientosEnSala() {
        // verificar que la sala no esté en mantenimiento
        if (salaSeleccionada.isEnMantenimiento()) {
            mostrarError("La sala seleccionada está en mantenimiento");
            return false;
        }

        for (Asiento asiento : asientosSeleccionados) {
            // verificar que el asiento esté disponible antes de ocuparlo
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

        // los asientos disponibles se actualizan automáticamente con getAsientosDisponibles()
        return true;
    }

    private String obtenerNumerosAsientos() {
        if (asientosSeleccionados == null || asientosSeleccionados.isEmpty()) {
            return "Ninguno";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < asientosSeleccionados.size(); i++) {
            Asiento asiento = asientosSeleccionados.get(i);
            sb.append(asiento.getId()); // Usar getId() que existe en tu clase
            if (i < asientosSeleccionados.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarConfirmacion(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
    }

    // Getters y Setters
    public ConfirmacionVentaVentana getVentana() {
        return ventana;
    }

    public void setControladorPrincipal(ControladorPrincipal controlador) {
        this.controladorPrincipal = controlador;
    }

    public void prepararConfirmacion(List<Asiento> asientosSeleccionados, Sala sala) {
        this.asientosSeleccionados = asientosSeleccionados;
        this.salaSeleccionada = sala;

        // Mostrar información en la ventana si el método existe
        // Si tu ConfirmacionVentaVentana tiene métodos para mostrar información, úsalos aquí
        // Por ejemplo:
        // ventana.setTextoAsientos(obtenerNumerosAsientos());
        // ventana.setTextoSala(sala.getNombre());

        System.out.println("Preparando confirmación para " + asientosSeleccionados.size() +
                " asientos en sala: " + sala.getNombre());
    }
}