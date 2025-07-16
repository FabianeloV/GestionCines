package Controlador;

import Vista.SeleccionPelicula;
import Modelo.Sala;
import Modelo.SistemaSalas;
import Modelo.Pelicula;

import javax.swing.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ControladorSeleccionPelicula implements ActionListener {

    private SeleccionPelicula vista;
    private ControladorVentanaAsientos controladorAsientos;
    private ControladorPrincipal controladorPrincipal;
    private SistemaSalas sistemaSalas;
    private Sala salaSeleccionada;
    private Pelicula peliculaSeleccionada;

    public ControladorSeleccionPelicula() {
        inicializarComponentes();
        configurarEventos();
        inicializarCiudades();
    }

    private void inicializarComponentes() {
        vista = new SeleccionPelicula();
        sistemaSalas = new SistemaSalas();
        // NO crear controladorAsientos aquí - se pasa desde el principal
    }

    private void configurarEventos() {
        vista.getBtnContinuar().addActionListener(this);
        vista.getBtnBack().addActionListener(this);
        vista.getCbCiudad().addActionListener(this);
        vista.getCbTipoSala().addActionListener(this);

        // Validaciones de campos
        vista.getTfFecha().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarFecha();
            }
        });

        vista.getTfHora().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarHora();
            }
        });
    }

    private void inicializarCiudades() {
        vista.getCbCiudad().removeAllItems();
        vista.getCbCiudad().addItem("Seleccionar");

        for (String ciudad : sistemaSalas.getCiudades()) {
            vista.getCbCiudad().addItem(ciudad);
        }

        vista.getCbTipoSala().removeAllItems();
        vista.getCbTipoSala().addItem("Seleccionar Sala");
        vista.getCbTipoSala().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnContinuar()) {
            procesarContinuar();
        }
        else if (e.getSource() == vista.getBtnBack()) {
            procesarVolver();
        }
        else if (e.getSource() == vista.getCbCiudad()) {
            procesarCambioCiudad();
        }
        else if (e.getSource() == vista.getCbTipoSala()) {
            procesarCambioSala();
        }
    }

    private void procesarContinuar() {
        if (!validarSeleccion()) {
            return;
        }

        int cantidadBoletos = vista.getCantidadBoletos();
        controladorAsientos.mostrarVentanaAsientos(salaSeleccionada, cantidadBoletos);

        vista.setVisible(false);
    }

    private void procesarVolver() {
        if (controladorPrincipal != null) {
            controladorPrincipal.volveraDashboard(vista);
        }
    }

    private void procesarCambioCiudad() {
        String ciudadSeleccionada = (String) vista.getCbCiudad().getSelectedItem();
        actualizarSalasPorCiudad(ciudadSeleccionada);
    }

    private void procesarCambioSala() {
        String ciudadSeleccionada = (String) vista.getCbCiudad().getSelectedItem();
        String infoSala = (String) vista.getCbTipoSala().getSelectedItem();
        actualizarSalaSeleccionada(ciudadSeleccionada, infoSala);
    }

    private void actualizarSalasPorCiudad(String ciudad) {
        vista.getCbTipoSala().removeAllItems();
        vista.getCbTipoSala().addItem("Seleccionar Sala");

        if (ciudad == null || ciudad.equals("Seleccionar")) {
            vista.getCbTipoSala().setEnabled(false);
            salaSeleccionada = null;
            return;
        }

        List<Sala> salasDisponibles = sistemaSalas.getSalasDisponibles(ciudad);

        if (salasDisponibles.isEmpty()) {
            vista.getCbTipoSala().addItem("No hay salas disponibles");
            vista.getCbTipoSala().setEnabled(false);
            salaSeleccionada = null;
        } else {
            for (Sala sala : salasDisponibles) {
                String infoSala = String.format("%s",
                        sala.getNombre());
                vista.getCbTipoSala().addItem(infoSala);
            }
            vista.getCbTipoSala().setEnabled(true);
        }
    }

    private void actualizarSalaSeleccionada(String ciudad, String infoSala) {
        if (ciudad == null || ciudad.equals("Seleccionar") ||
                infoSala == null || infoSala.equals("Seleccionar Sala")) {
            salaSeleccionada = null;
            return;
        }

        String nombreSala = infoSala.split(" \\(")[0];
        salaSeleccionada = sistemaSalas.getSala(ciudad, nombreSala);

        if (salaSeleccionada != null) {
            int maxBoletos = Math.min(10, salaSeleccionada.getAsientosDisponibles());
            vista.actualizarMaximoBoletos(maxBoletos);
        }
    }

    private boolean validarSeleccion() {
        if (vista.getCbCiudad().getSelectedItem().equals("Seleccionar")) {
            mostrarError("Por favor seleccione una ciudad");
            return false;
        }

        if (vista.getCbTipoSala().getSelectedItem().equals("Seleccionar Sala")) {
            mostrarError("Por favor seleccione una sala");
            return false;
        }

        if (salaSeleccionada == null) {
            mostrarError("No se pudo obtener información de la sala");
            return false;
        }

        if (vista.getFecha().getDate()== null) {
            mostrarError("Por favor ingrese la fecha");
            return false;
        }

        if (vista.getTfHora().getText().trim().isEmpty()) {
            mostrarError("Por favor ingrese la hora");
            return false;
        }

        if (vista.getCbFormato().getSelectedItem().equals("Formatos")) {
            mostrarError("Por favor seleccione un formato");
            return false;
        }

        if (vista.getCbIdiomas().getSelectedItem().equals("Idiomas")) {
            mostrarError("Por favor seleccione un idioma");
            return false;
        }

        return true;
    }

    private void validarFecha() {
        String fecha = vista.getTfFecha().getText().trim();
        if (!fecha.isEmpty() && !esFechaValida(fecha)) {
            mostrarError("Ingrese una fecha válida (dd/MM/yyyy)");
            vista.getTfFecha().requestFocus();
        }
    }

    private void validarHora() {
        String hora = vista.getTfHora().getText().trim();
        if (!hora.isEmpty() && !esHoraValida(hora)) {
            mostrarError("Ingrese una hora válida (HH:mm)");
            vista.getTfHora().requestFocus();
        }
    }

    private boolean esFechaValida(String fechaTexto) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(fechaTexto);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean esHoraValida(String horaTexto) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            sdf.setLenient(false);
            sdf.parse(horaTexto);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setControladorAsientos(ControladorVentanaAsientos controlador) {
        this.controladorAsientos = controlador;
    }

    public void setControladorPrincipal(ControladorPrincipal controlador) {
        this.controladorPrincipal = controlador;
    }

    public void mostrarPelicula(Pelicula pelicula) {
        this.peliculaSeleccionada = pelicula;
        vista.mostrarPelicula(pelicula);
    }

    // Getters
    public SeleccionPelicula getVista() {
        return vista;
    }

    public Sala getSalaSeleccionada() {
        return salaSeleccionada;
    }

    public Pelicula getPeliculaSeleccionada() {
        return peliculaSeleccionada;
    }
}