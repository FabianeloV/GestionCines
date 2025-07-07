package Vista;

import javax.swing.*;
import java.awt.*;

public class ConfirmacionVentaVentana extends JFrame {

    private JTextField txtNombre, txtCedula, txtCorreo;
    private JLabel lblTotalPagar;
    private JButton btnConfirmar, btnBack;
    private PanelConFondo panelFondo;

    public ConfirmacionVentaVentana() {
        setTitle("Confirmación de Venta");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        panelFondo = new PanelConFondo("/Sources/Crea un hermoso degradado para tu diseño, generador de degradados CSS y PNG _ Gradients_app.jpeg");
        panelFondo.setLayout(null);
        setContentPane(panelFondo);

        // Título
        JLabel lblTitulo = new JLabel("Confirmar Compra");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(190, 20, 300, 30);
        panelFondo.add(lblTitulo);

        // Botón Volver
        btnBack = crearBotonPanel("", "/Sources/contorno-fino-de-punta-de-flecha-a-la-izquierda.png");
        btnBack.setBounds(10, 10, 50, 50);
        btnBack.setOpaque(false);
        panelFondo.add(btnBack);

        // Etiquetas y campos
        JLabel lblNombre = crearLabel("Nombre completo:");
        lblNombre.setBounds(100, 80, 150, 25);
        panelFondo.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(260, 80, 220, 25);
        panelFondo.add(txtNombre);

        JLabel lblCedula = crearLabel("Cédula / ID:");
        lblCedula.setBounds(100, 120, 150, 25);
        panelFondo.add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(260, 120, 220, 25);
        panelFondo.add(txtCedula);

        JLabel lblCorreo = crearLabel("Correo electrónico:");
        lblCorreo.setBounds(100, 160, 150, 25);
        panelFondo.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(260, 160, 220, 25);
        panelFondo.add(txtCorreo);

        JLabel lblTotal = crearLabel("Total a pagar:");
        lblTotal.setBounds(100, 210, 150, 25);
        panelFondo.add(lblTotal);

        lblTotalPagar = new JLabel("$0.00");
        lblTotalPagar.setBounds(260, 210, 220, 25);
        lblTotalPagar.setForeground(Color.YELLOW);
        lblTotalPagar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelFondo.add(lblTotalPagar);

        // Botón confirmar
        btnConfirmar = new JButton("Confirmar Compra");
        btnConfirmar.setBounds(200, 280, 180, 35);
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelFondo.add(btnConfirmar);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private JButton crearBotonPanel(String texto, String iconoRuta) {
        JButton boton = new JButton("<html><center>" + texto + "</center></html>", new ImageIcon(getClass().getResource(iconoRuta)));
        boton.setHorizontalTextPosition(JButton.CENTER);
        boton.setVerticalTextPosition(JButton.BOTTOM);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(204, 204, 204));
        return boton;
    }

    // Getters para el controlador
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtCedula() {
        return txtCedula;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JLabel getLblTotalPagar() {
        return lblTotalPagar;
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfirmacionVentaVentana().setVisible(true));
    }
}
