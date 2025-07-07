package Vista;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ElegirAsientosVentana extends JFrame {

    private PanelConFondo panelFondo;
    private JPanel panelAsientos;
    private JButton btnConfirmar, btnBack;
    private Set<String> asientosSeleccionados = new HashSet<>();
    private int filas = 6;
    private int columnas = 8;

    public ElegirAsientosVentana() {
        setTitle("Seleccionar Asientos");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        panelFondo = new PanelConFondo("/Sources/Crea un hermoso degradado para tu diseño, generador de degradados CSS y PNG _ Gradients_app.jpeg");
        panelFondo.setLayout(null);
        setContentPane(panelFondo);

        // Botón back
        btnBack = crearBotonPanel("", "/Sources/contorno-fino-de-punta-de-flecha-a-la-izquierda.png");
        btnBack.setBounds(20, 10, 50, 50);
        btnBack.setOpaque(false);
        panelFondo.add(btnBack);

        JLabel lblTitulo = new JLabel("Selecciona tus asientos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(100, 20, 400, 30);
        panelFondo.add(lblTitulo);

        JLabel lblPantalla = new JLabel("PANTALLA", JLabel.CENTER);
        lblPantalla.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPantalla.setForeground(Color.WHITE);
        lblPantalla.setOpaque(true);
        lblPantalla.setBackground(new Color(150, 150, 150));
        lblPantalla.setBounds(150, 70, 500, 25);
        panelFondo.add(lblPantalla);

        panelAsientos = new JPanel(new GridLayout(filas, columnas, 10, 10));
        panelAsientos.setOpaque(false);
        JScrollPane scroll = new JScrollPane(panelAsientos);
        scroll.setBounds(150, 110, 500, 250);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        panelFondo.add(scroll);

        crearAsientos();

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirmar.setBounds(320, 380, 150, 35);
        panelFondo.add(btnConfirmar);
    }

    private void crearAsientos() {
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                String id = (char) ('A' + fila) + String.valueOf(col + 1);
                JToggleButton asiento = new JToggleButton(id);
                asiento.setBackground(new Color(144, 238, 144)); // Verde claro
                asiento.setFocusPainted(false);
                asiento.setFont(new Font("Segoe UI", Font.BOLD, 12));

                asiento.addActionListener(e -> {
                    if (!asiento.isEnabled()) return;

                    if (asiento.isSelected()) {
                        asiento.setBackground(Color.YELLOW);
                        asientosSeleccionados.add(id);
                    } else {
                        asiento.setBackground(new Color(144, 238, 144));
                        asientosSeleccionados.remove(id);
                    }
                });

                // Simular asiento ocupado aleatorio
                if (Math.random() < 0.15) {
                    asiento.setEnabled(false);
                    asiento.setBackground(new Color(220, 20, 60)); // Rojo
                }

                panelAsientos.add(asiento);
            }
        }
    }

    private JButton crearBotonPanel(String texto, String iconoRuta) {
        JButton boton = new JButton("<html><center>" + texto + "</center></html>", new ImageIcon(getClass().getResource(iconoRuta)));
        boton.setHorizontalTextPosition(JButton.CENTER);
        boton.setVerticalTextPosition(JButton.BOTTOM);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(204, 204, 204));
        return boton;
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    public Set<String> getAsientosSeleccionados() {
        return asientosSeleccionados;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ElegirAsientosVentana().setVisible(true);
        });
    }
}
