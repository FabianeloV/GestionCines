package Vista;

import Modelo.Asiento;
import Modelo.Sala;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ElegirAsientosVentana extends JFrame {

    private PanelConFondo panelFondo;
    private JPanel panelAsientos;
    private JButton btnConfirmar, btnBack;
    private JLabel lblContador;
    private Map<String, JToggleButton> botonesAsientos = new HashMap<>();
    private List<Asiento> asientosSeleccionados = new ArrayList<>();
    private Sala salaActual;
    private int cantidadBoletos;
    private int filas = 6;
    private int columnas = 8;
    private ActionListener controladorAsientos;

    public ElegirAsientosVentana() {
        setTitle("Seleccionar Asientos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        panelFondo = new PanelConFondo("/Sources/Crea un hermoso degradado para tu diseño, generador de degradados CSS y PNG _ Gradients_app.jpeg");
        panelFondo.setLayout(null);
        setContentPane(panelFondo);

        configurarInterfaz();
    }

    private void configurarInterfaz() {
        // Botón back
        btnBack = crearBotonPanel("");
        btnBack.setBounds(20, 10, 50, 50);
        btnBack.setOpaque(false);
        panelFondo.add(btnBack);

        JLabel lblTitulo = new JLabel("Selecciona tus asientos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(100, 20, 400, 30);
        panelFondo.add(lblTitulo);

        // contador de asientos
        lblContador = new JLabel("Asientos seleccionados: 0/0");
        lblContador.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblContador.setForeground(Color.WHITE);
        lblContador.setBounds(500, 20, 250, 30);
        panelFondo.add(lblContador);

        // pantalla
        JLabel lblPantalla = new JLabel("PANTALLA", JLabel.CENTER);
        lblPantalla.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPantalla.setForeground(Color.WHITE);
        lblPantalla.setOpaque(true);
        lblPantalla.setBackground(new Color(150, 150, 150));
        lblPantalla.setBounds(150, 70, 500, 25);
        panelFondo.add(lblPantalla);

        // panel de asientos
        panelAsientos = new JPanel(new GridLayout(filas, columnas, 5, 5));
        panelAsientos.setOpaque(false);
        JScrollPane scroll = new JScrollPane(panelAsientos);
        scroll.setBounds(150, 110, 500, 300);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        panelFondo.add(scroll);

        // Leyenda
        crearLeyenda();

        // Botón confirmar
        btnConfirmar = new JButton("Confirmar Selección");
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirmar.setBounds(320, 480, 150, 35);
        btnConfirmar.setEnabled(false);
        panelFondo.add(btnConfirmar);
    }

    private void crearLeyenda() {
        JPanel panelLeyenda = new JPanel(new FlowLayout());
        panelLeyenda.setOpaque(false);
        panelLeyenda.setBounds(150, 420, 500, 50);

        // Disponible
        JButton btnDisponible = new JButton("Disponible");
        btnDisponible.setBackground(new Color(144, 238, 144));
        btnDisponible.setEnabled(false);
        btnDisponible.setPreferredSize(new Dimension(80, 30));

        // Seleccionado
        JButton btnSeleccionado = new JButton("Seleccionado");
        btnSeleccionado.setBackground(Color.YELLOW);
        btnSeleccionado.setEnabled(false);
        btnSeleccionado.setPreferredSize(new Dimension(100, 30));

        // Ocupado
        JButton btnOcupado = new JButton("Ocupado");
        btnOcupado.setBackground(new Color(220, 20, 60));
        btnOcupado.setEnabled(false);
        btnOcupado.setPreferredSize(new Dimension(80, 30));

        panelLeyenda.add(btnDisponible);
        panelLeyenda.add(btnSeleccionado);
        panelLeyenda.add(btnOcupado);

        panelFondo.add(panelLeyenda);
    }

    public void configurarSala(Sala sala, int cantidadBoletos) {
        this.salaActual = sala;
        this.cantidadBoletos = cantidadBoletos;

        actualizarContadorAsientos(0, cantidadBoletos);

        crearAsientos();
    }

    private void crearAsientos() {
        panelAsientos.removeAll();
        botonesAsientos.clear();
        asientosSeleccionados.clear();

        if (salaActual != null) {
            List<Asiento> asientosSala = salaActual.getAsientos();

            for (int fila = 0; fila < filas; fila++) {
                for (int col = 0; col < columnas; col++) {
                    String id = (char) ('A' + fila) + String.valueOf(col + 1);

                    // Buscar el asiento correspondiente en la sala
                    Asiento asiento = asientosSala.stream()
                            .filter(a -> a.getId().equals(id))
                            .findFirst()
                            .orElse(new Asiento(id));

                    JToggleButton botonAsiento = crearBotonAsiento(asiento);
                    botonesAsientos.put(id, botonAsiento);
                    panelAsientos.add(botonAsiento);
                }
            }
        } else {
            // Crear asientos por defecto si no hay sala configurada
            for (int fila = 0; fila < filas; fila++) {
                for (int col = 0; col < columnas; col++) {
                    String id = (char) ('A' + fila) + String.valueOf(col + 1);
                    Asiento asiento = new Asiento(id);

                    // Simular algunos asientos ocupados
                    if (Math.random() < 0.15) {
                        asiento.setOcupado(true);
                    }

                    JToggleButton botonAsiento = crearBotonAsiento(asiento);
                    botonesAsientos.put(id, botonAsiento);
                    panelAsientos.add(botonAsiento);
                }
            }
        }

        panelAsientos.revalidate();
        panelAsientos.repaint();
    }

    private JToggleButton crearBotonAsiento(Asiento asiento) {
        JToggleButton boton = new JToggleButton(asiento.getId());
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        boton.setPreferredSize(new Dimension(50, 40));

        if (asiento.isOcupado()) {
            boton.setEnabled(false);
            boton.setBackground(new Color(220, 20, 60)); // Rojo
        } else {
            boton.setBackground(new Color(144, 238, 144)); // Verde claro
            boton.addActionListener(e -> {
                if (controladorAsientos != null) {
                    // Crear un ActionEvent personalizado con el ID del asiento
                    ActionEvent evento = new ActionEvent(boton, ActionEvent.ACTION_PERFORMED, asiento.getId());
                    controladorAsientos.actionPerformed(evento);
                }
            });
        }

        return boton;
    }

    public void marcarAsientoComoSeleccionado(Asiento asiento) {
        JToggleButton boton = botonesAsientos.get(asiento.getId());
        if (boton != null) {
            boton.setSelected(true);
            boton.setBackground(Color.YELLOW);
            if (!asientosSeleccionados.contains(asiento)) {
                asientosSeleccionados.add(asiento);
            }
        }
    }

    public void marcarAsientoComoDisponible(Asiento asiento) {
        JToggleButton boton = botonesAsientos.get(asiento.getId());
        if (boton != null) {
            boton.setSelected(false);
            boton.setBackground(new Color(144, 238, 144));
            asientosSeleccionados.remove(asiento);
        }
    }

    public void actualizarContadorAsientos(int seleccionados, int total) {
        lblContador.setText("Asientos seleccionados: " + seleccionados + "/" + total);
        btnConfirmar.setEnabled(seleccionados == total && seleccionados > 0);
    }

    public void reiniciarSeleccionAsientos() {
        asientosSeleccionados.clear();
        for (JToggleButton boton : botonesAsientos.values()) {
            if (boton.isEnabled()) {
                boton.setSelected(false);
                boton.setBackground(new Color(144, 238, 144));
            }
        }
        actualizarContadorAsientos(0, cantidadBoletos);
    }

    private JButton crearBotonPanel(String texto) {
        JButton boton = new JButton("<html><center>" + texto + "</center></html>");

        boton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Sources/contorno-fino-de-punta-de-flecha-a-la-izquierda.png"))));

        boton.setHorizontalTextPosition(JButton.CENTER);
        boton.setVerticalTextPosition(JButton.BOTTOM);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(204, 204, 204));
        return boton;
    }

    public void setControladorAsientos(ActionListener controlador) {
        this.controladorAsientos = controlador;
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    public List<Asiento> getAsientosSeleccionados() {
        return new ArrayList<>(asientosSeleccionados);
    }

    public Sala getSalaActual() {
        return salaActual;
    }

    public int getCantidadBoletos() {
        return cantidadBoletos;
    }

}