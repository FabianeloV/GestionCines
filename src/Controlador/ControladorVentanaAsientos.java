package Controlador;

import Modelo.Asiento;
import Modelo.Sala;
import Vista.ElegirAsientosVentana;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorVentanaAsientos implements ActionListener {

    private ElegirAsientosVentana ventanaAsientos;
    private ControladorConfirmacionPagos confirmacionPagos;
    private Sala sala;
    private int cantidadBoletos;
    private ControladorPrincipal controladorPrincipal;

    public ControladorVentanaAsientos() {
        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        ventanaAsientos = new ElegirAsientosVentana();
        confirmacionPagos = new ControladorConfirmacionPagos();
    }

    private void configurarEventos() {

        ventanaAsientos.getBtnConfirmar().addActionListener(this);
        ventanaAsientos.getBtnBack().addActionListener(this);
        ventanaAsientos.setControladorAsientos(this);

        if (confirmacionPagos != null && confirmacionPagos.getVentana() != null) {
            confirmacionPagos.getVentana().getBtnBack().addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (sala != null && sala.getAsiento(e.getActionCommand()) != null) {
            manejarSeleccionAsiento(e.getActionCommand());
            return;
        }

        if (e.getSource() == ventanaAsientos.getBtnConfirmar()) {
            procesarConfirmacionAsientos();
        }

        //botones de back
        if (e.getSource() == ventanaAsientos.getBtnBack()) {
            procesarVolverDesdeAsientos();
        }


        if (confirmacionPagos != null && confirmacionPagos.getVentana() != null &&
                e.getSource() == confirmacionPagos.getVentana().getBtnBack()) {
            procesarVolverDesdeConfirmacion();
        }
    }

    private void manejarSeleccionAsiento(String idAsiento) {
        if (sala == null) return;

        Asiento asiento = sala.getAsiento(idAsiento);

        if (asiento == null || asiento.isOcupado() || sala.isEnMantenimiento()) {
            return;
        }

        List<Asiento> asientosSeleccionados = ventanaAsientos.getAsientosSeleccionados();

        if (!asiento.isSeleccionado() && asientosSeleccionados.size() < cantidadBoletos) {

            asiento.setSeleccionado(true);
            ventanaAsientos.marcarAsientoComoSeleccionado(asiento);
        } else if (asiento.isSeleccionado()) {

            asiento.setSeleccionado(false);
            ventanaAsientos.marcarAsientoComoDisponible(asiento);
        }

        ventanaAsientos.actualizarContadorAsientos(ventanaAsientos.getAsientosSeleccionados().size(), cantidadBoletos);
    }

    private void procesarConfirmacionAsientos() {

        confirmacionPagos.setControladorPrincipal(controladorPrincipal);

        List<Asiento> asientosSeleccionados = ventanaAsientos.getAsientosSeleccionados();
        confirmacionPagos.prepararConfirmacion(asientosSeleccionados, sala);

        navegarA(ventanaAsientos, confirmacionPagos.getVentana());
    }

    private void procesarVolverDesdeAsientos() {
        reiniciarSeleccion();
        controladorPrincipal.volveraDashboard(ventanaAsientos);
    }

    private void procesarVolverDesdeConfirmacion() {
        navegarA(confirmacionPagos.getVentana(), ventanaAsientos);
    }

    private void navegarA(JFrame origen, JFrame destino) {
        if (origen != null) {
            origen.setVisible(false);
        }
        if (destino != null) {
            destino.setVisible(true);
        }
    }

    private void reiniciarSeleccion() {
        if (ventanaAsientos != null) {
            ventanaAsientos.reiniciarSeleccionAsientos();
        }
        if (sala != null) {
            for (Asiento asiento : sala.getAsientos()) {
                asiento.setSeleccionado(false);
            }
        }
    }

    public void configurarSala(Sala salaSeleccionada, int cantidadBoletos) {
        this.sala = salaSeleccionada;
        this.cantidadBoletos = cantidadBoletos;

        if (ventanaAsientos != null) {
            ventanaAsientos.configurarSala(sala, cantidadBoletos);
        }
    }

    public void setControladorPrincipal(ControladorPrincipal controlador) {
        this.controladorPrincipal = controlador;

        if (confirmacionPagos != null) {
            confirmacionPagos.setControladorPrincipal(controlador);
        }
    }

    public void mostrarVentanaAsientos(Sala sala, int cantidadBoletos) {
        configurarSala(sala, cantidadBoletos);
        reiniciarSeleccion();
        ventanaAsientos.setVisible(true);
    }

    public ElegirAsientosVentana getVentanaAsientos() {
        return ventanaAsientos;
    }

    public ControladorConfirmacionPagos getConfirmacionPagos() {
        return confirmacionPagos;
    }

    public Sala getSala() {
        return sala;
    }

    public int getCantidadBoletos() {
        return cantidadBoletos;
    }
}