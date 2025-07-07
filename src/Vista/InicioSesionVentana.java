package Vista;

import javax.swing.*;
import java.awt.*;

public class InicioSesionVentana extends JFrame {

    private JLabel fondo, logo, iconoUsuario, iconoClave, titulo;
    private JTextField campoUsuario;
    private JPasswordField campoClave;
    private JButton botonAceptar;

    public InicioSesionVentana() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Inicio de Sesión");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        fondo = new JLabel(new ImageIcon(getClass().getResource("/Sources/Crea un hermoso degradado para tu diseño, generador de degradados CSS y PNG _ Gradients_app.jpeg")));
        fondo.setBounds(0, 0, 600, 400);


        // componentes del titulo
        titulo = new JLabel("Inicio de Sesion");
        titulo.setBounds(150, 30, 300, 30);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo);

        // Icono usuario
        iconoUsuario = new JLabel(new ImageIcon(getClass().getResource("/Sources/User.png")));
        iconoUsuario.setBounds(125, 115, 50, 50);
        add(iconoUsuario);

        // Campo de texto usuario
        campoUsuario = new JTextField();
        campoUsuario.setBounds(180, 120, 250, 30);
        add(campoUsuario);

        // Icono clave
        iconoClave = new JLabel(new ImageIcon(getClass().getResource("/Sources/Lock.png")));
        iconoClave.setBounds(125, 165, 50, 50);
        add(iconoClave);

        // Campo de contraseña
        campoClave = new JPasswordField();
        campoClave.setBounds(180, 180, 250, 30);
        add(campoClave);

        // Botón aceptar
        botonAceptar = new JButton("ACEPTAR");
        botonAceptar.setBounds(240, 240, 100, 35);
        add(botonAceptar);

        add(fondo);
    }

    public JButton getBotonAceptar() {
        return botonAceptar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InicioSesionVentana().setVisible(true);
        });
    }
}
