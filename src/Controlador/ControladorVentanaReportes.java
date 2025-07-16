package Controlador;

import Modelo.ExportadorExcel;
import Modelo.ExportadorPDF;
import Modelo.GestorPelicula;
import Modelo.Pelicula;
import Modelo.GestorVentas;
import Modelo.Venta;
import Modelo.GestorAsistencia;
import Modelo.Asistencia;
import Modelo.SistemaSalas;
import Vista.VentanaReportes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.stream.Collectors;

public class ControladorVentanaReportes implements ActionListener {

    private VentanaReportes ventanaReportes;
    private GestorPelicula gestorPelicula;
    private GestorVentas gestorVentas;
    private GestorAsistencia gestorAsistencia;
    private SistemaSalas sistemaSalas;
    private ExportadorPDF exportadorPDF;
    private ExportadorExcel exportadorExcel;


    public ControladorVentanaReportes(VentanaReportes ventanaReportes) {
        this.ventanaReportes = ventanaReportes;
        this.gestorPelicula = new GestorPelicula();
        this.gestorVentas = new GestorVentas();
        this.gestorAsistencia = new GestorAsistencia();
        this.sistemaSalas = new SistemaSalas();
        this.exportadorPDF = new ExportadorPDF();
        this.exportadorExcel = new ExportadorExcel();

        // Cargar datos iniciales
        gestorPelicula.actualizarLista();
        gestorVentas.leerVentasDesdeArchivo();
        gestorAsistencia.leerAsistenciasDesdeArchivo();

        // Configurar fechas por defecto en los JDateChooser
        configurarFechasPorDefecto();

        this.ventanaReportes.getBtnGenerar().addActionListener(this);
    }

    /**
     * Configura fechas por defecto en los JDateChooser
     */
    private void configurarFechasPorDefecto() {
        Calendar cal = Calendar.getInstance();

        // Fecha fin: hoy
        Date fechaHoy = cal.getTime();
        ventanaReportes.getFechaFin().setDate(fechaHoy);

        // Fecha inicio: hace 30 días
        cal.add(Calendar.DAY_OF_MONTH, -30);
        Date fechaInicio = cal.getTime();
        ventanaReportes.getFechaInicio().setDate(fechaInicio);

        // Configurar formato de fecha en los JDateChooser
        ventanaReportes.getFechaInicio().setDateFormatString("dd/MM/yyyy");
        ventanaReportes.getFechaFin().setDateFormatString("dd/MM/yyyy");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaReportes.getBtnGenerar()) {
            generarReporte();
        }
    }

    private void generarReporte() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        // Obtener fechas de los JDateChooser
        Date fechaInicio = ventanaReportes.getFechaInicio().getDate();
        Date fechaFin = ventanaReportes.getFechaFin().getDate();

        // Validaciones mejoradas para JDateChooser
        if (!validarFechas(fechaInicio, fechaFin)) {
            return;
        }

        // Ajustar fechas para incluir todo el día
        fechaInicio = ajustarFechaInicio(fechaInicio);
        fechaFin = ajustarFechaFin(fechaFin);

        String tipoReporte = ventanaReportes.getComboTipoReporte().getSelectedItem().toString();
        String formato = ventanaReportes.getComboFormatoReporte().getSelectedItem().toString();

        if (tipoReporte.equals("Seleccionar") || formato.equals("Seleccionar")) {
            mostrarError("Por favor seleccione un tipo y formato de reporte.");
            return;
        }

        try {
            switch (tipoReporte) {
                case "Cartelera":
                    generarReporteCartelera(formato, fechaInicio, fechaFin);
                    break;
                case "Asistencia":
                    generarReporteAsistencia(formato, fechaInicio, fechaFin);
                    break;
                case "Ocupacion Salas":
                    generarReporteOcupacionSalas(formato, fechaInicio, fechaFin);
                    break;
                case "Venta de tickets":
                    generarReporteVentas(formato, fechaInicio, fechaFin);
                    break;
                default:
                    mostrarError("Tipo de reporte no implementado.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarError("Error al generar el reporte: " + ex.getMessage());
        }
    }

    /**
     * Valida las fechas seleccionadas en los JDateChooser
     */
    private boolean validarFechas(Date fechaInicio, Date fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            mostrarError("Ambas fechas son obligatorias. Por favor seleccione las fechas en el calendario.");
            return false;
        }

        if (fechaInicio.after(fechaFin)) {
            mostrarError("La fecha de inicio debe ser anterior a la fecha fin.");
            return false;
        }

        // Validar que las fechas no sean futuras
        Date hoy = new Date();
        if (fechaInicio.after(hoy) || fechaFin.after(hoy)) {
            mostrarError("No se pueden seleccionar fechas futuras.");
            return false;
        }

        // Validar rango máximo (por ejemplo, no más de 1 año)
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaInicio);
        cal.add(Calendar.YEAR, 1);

        if (fechaFin.after(cal.getTime())) {
            mostrarError("El rango de fechas no puede ser mayor a 1 año.");
            return false;
        }

        return true;
    }

    /**
     * Ajusta la fecha de inicio para incluir desde las 00:00:00
     */
    private Date ajustarFechaInicio(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Ajusta la fecha fin para incluir hasta las 23:59:59
     */
    private Date ajustarFechaFin(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    private void generarReporteCartelera(String formato, Date fechaInicio, Date fechaFin) {
        ArrayList<Pelicula> peliculas = gestorPelicula.getPeliculas();

        if (peliculas.isEmpty()) {
            mostrarError("No hay películas registradas para generar el reporte.");
            return;
        }

        String[] encabezados = {"Nombre", "Descripción", "Duración", "Género"};
        List<Object[]> datosReporte = peliculas.stream()
                .map(p -> new Object[]{
                        p.getNombre(),
                        p.getDescripcion(),
                        p.getDuracion(),
                        p.getGenero()
                })
                .collect(Collectors.toList());

        String rangoFechas = formatearRangoFechas(fechaInicio, fechaFin);
        exportarReporte("Reporte Cartelera", encabezados, datosReporte, formato, rangoFechas);
    }

    private void generarReporteAsistencia(String formato, Date fechaInicio, Date fechaFin) {
        List<Asistencia> asistencias = gestorAsistencia.getAsistencias().stream()
                .filter(a -> !a.getFecha().before(fechaInicio) && !a.getFecha().after(fechaFin))
                .collect(Collectors.toList());

        if (asistencias.isEmpty()) {
            mostrarError("No hay datos de asistencia en el rango de fechas seleccionado: " +
                    formatearRangoFechas(fechaInicio, fechaFin));
            return;
        }

        String[] encabezados = {"Cliente", "Película", "Fecha", "Hora"};
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm");

        List<Object[]> datosReporte = asistencias.stream()
                .map(a -> new Object[]{
                        a.getCliente().getNombre(),
                        a.getPelicula().getNombre(),
                        fechaFormat.format(a.getFecha()),
                        horaFormat.format(a.getFecha())
                })
                .collect(Collectors.toList());

        String rangoFechas = formatearRangoFechas(fechaInicio, fechaFin);
        exportarReporte("Reporte Asistencia", encabezados, datosReporte, formato, rangoFechas);
    }

    private void generarReporteOcupacionSalas(String formato, Date fechaInicio, Date fechaFin) {
        // Este reporte necesitaría datos de ocupación que no están en las clases mostradas
        // Implementación básica como ejemplo
        String[] encabezados = {"Sala", "Ciudad", "Capacidad", "Porcentaje Ocupación"};
        List<Object[]> datosReporte = new ArrayList<>();

        // Ejemplo con datos ficticios - en una implementación real necesitarías estos datos
        datosReporte.add(new Object[]{"Sala 1", "Cuenca", 50, "75%"});
        datosReporte.add(new Object[]{"Sala 2", "Cuenca", 60, "60%"});

        String rangoFechas = formatearRangoFechas(fechaInicio, fechaFin);
        exportarReporte("Reporte Ocupación Salas", encabezados, datosReporte, formato, rangoFechas);
    }

    private void generarReporteVentas(String formato, Date fechaInicio, Date fechaFin) {
        List<Venta> ventas = gestorVentas.getVentas().stream()
                .filter(v -> !v.getFecha().before(fechaInicio) && !v.getFecha().after(fechaFin))
                .collect(Collectors.toList());

        if (ventas.isEmpty()) {
            mostrarError("No hay datos de ventas en el rango de fechas seleccionado: " +
                    formatearRangoFechas(fechaInicio, fechaFin));
            return;
        }

        String[] encabezados = {"Ticket", "Película", "Cliente", "Fecha", "Monto"};
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");

        List<Object[]> datosReporte = ventas.stream()
                .map(v -> new Object[]{
                        v.getNumeroTicket(),
                        v.getPelicula().getNombre(),
                        v.getCliente().getNombre(),
                        fechaFormat.format(v.getFecha()),
                        String.format("$%.2f", v.getMonto())
                })
                .collect(Collectors.toList());

        String rangoFechas = formatearRangoFechas(fechaInicio, fechaFin);
        exportarReporte("Reporte Ventas", encabezados, datosReporte, formato, rangoFechas);
    }

    /**
     * Formatea el rango de fechas para mostrar en mensajes
     */
    private String formatearRangoFechas(Date fechaInicio, Date fechaFin) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fechaInicio) + " - " + formato.format(fechaFin);
    }

    private void exportarReporte(String nombreReporte, String[] encabezados, List<Object[]> datos,
                                 String formato, String rangoFechas) {
        try {
            String nombreArchivo = nombreReporte + " " +
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            if (formato.equalsIgnoreCase("PDF")) {
                exportadorPDF.exportar(nombreArchivo, encabezados, datos);
                mostrarMensaje("Reporte PDF generado correctamente: " + nombreArchivo + ".pdf\n" +
                        "Rango de fechas: " + rangoFechas + "\n" +
                        "Total de registros: " + datos.size());
            } else if (formato.equalsIgnoreCase("Excel")) {
                exportadorExcel.exportar(nombreArchivo, encabezados, datos);
                mostrarMensaje("Reporte Excel generado correctamente: " + nombreArchivo + ".xlsx\n" +
                        "Rango de fechas: " + rangoFechas + "\n" +
                        "Total de registros: " + datos.size());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error al exportar el reporte: " + ex.getMessage(), ex);
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(ventanaReportes, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(ventanaReportes, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Método para limpiar/resetear los campos de fecha
     */
    public void limpiarFechas() {
        ventanaReportes.getFechaInicio().setDate(null);
        ventanaReportes.getFechaFin().setDate(null);
    }

    /**
     * Método para establecer fechas predeterminadas
     */
    public void establecerFechasUltimoMes() {
        Calendar cal = Calendar.getInstance();
        Date fechaFin = cal.getTime();

        cal.add(Calendar.MONTH, -1);
        Date fechaInicio = cal.getTime();

        ventanaReportes.getFechaInicio().setDate(fechaInicio);
        ventanaReportes.getFechaFin().setDate(fechaFin);
    }

    public VentanaReportes getVentanaReportes() {
        return ventanaReportes;
    }
}