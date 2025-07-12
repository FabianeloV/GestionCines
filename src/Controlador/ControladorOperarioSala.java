package Controlador;

import Modelo.Sala;
import Modelo.SistemaSalas;
import Vista.VentanaOperarioSala;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorOperarioSala {
    private VentanaOperarioSala vista;
    private SistemaSalas modelo;
    private Sala salaSeleccionada;

    public ControladorOperarioSala() {
        this.vista = new VentanaOperarioSala();
        this.modelo = new SistemaSalas();
        inicializarEventos();
        cargarCiudades();
    }

    // Getter para que el ControladorPrincipal pueda acceder a la vista
    public VentanaOperarioSala getVista() {
        return vista;
    }

    // Getter para acceder al modelo si es necesario
    public SistemaSalas getModelo() {
        return modelo;
    }

    private void inicializarEventos() {
        // Evento para cambio de ciudad
        vista.getComboCiudad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarCambioCiudad();
            }
        });

        // Evento para cambio de sala
        vista.getComboSala().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarCambioSala();
            }
        });

        // Evento para el botón Actualizar
        vista.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstadoSala();
            }
        });
    }

    private void cargarCiudades() {
        JComboBox<String> comboCiudad = vista.getComboCiudad();
        comboCiudad.removeAllItems();
        comboCiudad.addItem("Seleccionar");

        for (String ciudad : modelo.getCiudades()) {
            comboCiudad.addItem(ciudad);
        }
    }

    private void manejarCambioCiudad() {
        String ciudadSeleccionada = (String) vista.getComboCiudad().getSelectedItem();
        JComboBox<String> comboSala = vista.getComboSala();

        comboSala.removeAllItems();
        comboSala.addItem("Seleccionar sala");

        if (ciudadSeleccionada != null && !ciudadSeleccionada.equals("Seleccionar")) {
            for (Sala sala : modelo.getSalasPorCiudad(ciudadSeleccionada)) {
                comboSala.addItem(sala.getNombre());
            }
        }

        // Limpiar información de sala
        limpiarInfoSala();
    }

    private void manejarCambioSala() {
        String ciudadSeleccionada = (String) vista.getComboCiudad().getSelectedItem();
        String salaSeleccionadaNombre = (String) vista.getComboSala().getSelectedItem();

        if (ciudadSeleccionada != null && !ciudadSeleccionada.equals("Seleccionar") &&
                salaSeleccionadaNombre != null && !salaSeleccionadaNombre.equals("Seleccionar sala")) {

            salaSeleccionada = modelo.getSala(ciudadSeleccionada, salaSeleccionadaNombre);

            if (salaSeleccionada != null) {
                vista.setInfoSala(
                        salaSeleccionada.getNombre(),
                        salaSeleccionada.getCapacidad(),
                        salaSeleccionada.isEnMantenimiento()
                );
            }
        } else {
            limpiarInfoSala();
        }
    }

    private void actualizarEstadoSala() {
        if (salaSeleccionada == null) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor seleccione una sala primero.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean enMantenimiento = vista.getRadioMantenimiento().isSelected();

        if (modelo.actualizarEstadoSala(
                salaSeleccionada.getCiudad(),
                salaSeleccionada.getNombre(),
                enMantenimiento)) {

            String mensaje = enMantenimiento ?
                    "Sala marcada como EN MANTENIMIENTO" :
                    "Sala marcada como DISPONIBLE";

            JOptionPane.showMessageDialog(vista,
                    mensaje,
                    "Actualización Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

            // Actualizar la vista
            vista.setInfoSala(
                    salaSeleccionada.getNombre(),
                    salaSeleccionada.getCapacidad(),
                    salaSeleccionada.isEnMantenimiento()
            );
        } else {
            JOptionPane.showMessageDialog(vista,
                    "Error al actualizar el estado de la sala.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarInfoSala() {
        vista.setInfoSala("", 0, false);
        salaSeleccionada = null;
    }

    // Método para iniciar la aplicación (solo para testing independiente)
    public static void main(String[] args) {

            ControladorOperarioSala controlador = new ControladorOperarioSala();
            controlador.getVista().setVisible(true);

    }
}