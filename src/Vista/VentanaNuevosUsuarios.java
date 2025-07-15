package Vista;

import Modelo.Rol;

import javax.swing.*;
import java.awt.*;

public class VentanaNuevosUsuarios extends JFrame {

    private JPanel panelPrincipal;
    private JLabel lblIconoUsuario;
    private JLabel lblTitulo;
    private JComboBox<String> comboTipoUsuario;
    private JLabel lblContraseña;
    private JTextField campoUsuario;
    private JLabel lblUsuario;
    private JPasswordField campoContraseña;
    private JButton btnCrearUsuario;
    private JButton BotonBack;
    private JLabel lblTipoUsuario;
    private JLabel lblFondo;

    public VentanaNuevosUsuarios() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Agregar Nuevo Usuario");
        setSize(550, 430);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0, 0, 550, 410);

        lblIconoUsuario = new JLabel(new ImageIcon(getClass().getResource("/Sources/agregar-usuario.png")));
        lblIconoUsuario.setBounds(210, 70, 130, 130);
        panelPrincipal.add(lblIconoUsuario);

        lblTitulo = new JLabel("Agregar Usuarios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(170, 30, 250, 30);
        panelPrincipal.add(lblTitulo);

        comboTipoUsuario = new JComboBox<>(new String[]{"Seleccionar", "Taquillero", "Operador", "Administrador"});
        comboTipoUsuario.setBounds(240, 220, 180, 25);
        panelPrincipal.add(comboTipoUsuario);

        lblTipoUsuario = new JLabel("Tipo de usuario:");
        lblTipoUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTipoUsuario.setForeground(Color.WHITE);
        lblTipoUsuario.setBounds(110, 220, 130, 25);
        panelPrincipal.add(lblTipoUsuario);

        lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(160, 260, 70, 25);
        panelPrincipal.add(lblUsuario);

        campoUsuario = new JTextField();
        campoUsuario.setBounds(240, 260, 180, 25);
        panelPrincipal.add(campoUsuario);

        lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblContraseña.setForeground(Color.WHITE);
        lblContraseña.setBounds(140, 300, 100, 25);
        panelPrincipal.add(lblContraseña);

        campoContraseña = new JPasswordField();
        campoContraseña.setBounds(240, 300, 180, 25);
        panelPrincipal.add(campoContraseña);

        btnCrearUsuario = new JButton("Crear");
        btnCrearUsuario.setBounds(230, 350, 100, 30);
        panelPrincipal.add(btnCrearUsuario);

        BotonBack = crearBotonPanel("", "/Sources/contorno-fino-de-punta-de-flecha-a-la-izquierda.png",20,30);
        BotonBack.setBounds(20, 20, 50, 50);
        BotonBack.setOpaque(false);
        panelPrincipal.add(BotonBack);

        lblFondo = new JLabel(new ImageIcon(getClass().getResource("/Sources/Crea un hermoso degradado para tu diseño, generador de degradados CSS y PNG _ Gradients_app.jpeg")));
        lblFondo.setBounds(0, 0, 550, 410);
        panelPrincipal.add(lblFondo);

        add(panelPrincipal);
    }

    // Getters para el controlador
    public JButton getBtnCrearUsuario() { return btnCrearUsuario; }
    public JButton getBotonBack() { return BotonBack; }

    public String getUsuarioIngresado() {
        return campoUsuario.getText().trim();
    }

    public String getClaveIngresada() {
        return campoContraseña.getText().trim();
    }

    public Rol getRol() {
        String rol = comboTipoUsuario.getSelectedItem().toString();

        switch (rol) {
            case "Operador":
                return Rol.Operador;
                case "Taquillero":
                    return Rol.Taquilla;
            default:
                return Rol.Admin;
        }
    }

    // Método para mostrar mensajes desde el controlador
    public void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipoMensaje);
    }

    // Métodos para limpiar los campos
    public void limpiarCampos() {
        campoUsuario.setText("");
        campoContraseña.setText("");
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaNuevosUsuarios ventana = new VentanaNuevosUsuarios();
            ventana.setVisible(true);
        });
    }

    private JButton crearBotonPanel(String texto, String iconoRuta, int x, int y) {
        JButton boton = new JButton("<html><center>" + texto + "</center></html>", new ImageIcon(getClass().getResource(iconoRuta)));
        boton.setBounds(x, y, 160, 180);
        boton.setHorizontalTextPosition(JButton.CENTER);
        boton.setVerticalTextPosition(JButton.BOTTOM);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(204, 204, 204));
        return boton;
    }

}

