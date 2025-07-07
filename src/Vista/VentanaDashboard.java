package Vista;

import javax.swing.*;
import java.awt.*;

public class VentanaDashboard extends JFrame {

    private JButton botonCartelera;
    private JButton botonTickets;
    private JButton botonControlSalas;
    private JButton botonAgregarUsuario;
    private JButton botonReportes;
    private JButton botonLogout;

    public VentanaDashboard() {
        initComponents();
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(750, 540);
        setLocationRelativeTo(null);

        // Fondo personalizado (PanelConFondo) que cubre todo el JFrame
        PanelConFondo fondo = new PanelConFondo("/Sources/Crea un hermoso degradado para tu diseño, generador de degradados CSS y PNG _ Gradients_app.jpeg");
        fondo.setLayout(null);  // layout absoluto para manejar posiciones
        setContentPane(fondo);  // fondo es el contenedor principal

        // Barra superior blanca (panel para logout)
        JPanel barraSuperior = new JPanel(null);
        barraSuperior.setBackground(Color.BLACK);
        barraSuperior.setBounds(0, 0, 750, 55);  // ancho igual al JFrame, altura fija 40px

        // Botón logout dentro de la barra superior
        botonLogout = crearBotonPanel("Cuenta", "/Sources/cuenta.png",700,5);
        botonLogout.setBounds(655, 0, 75, 60); // posicion en la esquina derecha, algo de margen arriba y derecha
        barraSuperior.add(botonLogout);

        // Añadimos la barra superior al fondo
        fondo.add(barraSuperior);

        // Panel lateral negro con botones
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(Color.BLACK);
        panelLateral.setBounds(0, 55, 50, 500);  // empieza justo debajo de la barra superior
        panelLateral.setLayout(new GridLayout(4, 1, 0, 20));

        botonReportes = crearBotonPanel("", "/Sources/reportes.png",700,5);
        botonReportes.setOpaque(false);

        botonAgregarUsuario = crearBotonPanel("", "/Sources/agregar-usuario_home.png",700,5);
        botonAgregarUsuario.setOpaque(false);


        panelLateral.add(botonReportes);
        panelLateral.add(botonAgregarUsuario);
        fondo.add(panelLateral);

        // Título y logo superior, ajustados para quedar debajo de la barra superior
        JLabel fondoSuperior = new JLabel("CineAndo", JLabel.CENTER);
        fondoSuperior.setFont(new Font("Stencil", Font.BOLD | Font.ITALIC, 40));
        fondoSuperior.setForeground(Color.WHITE);
        fondoSuperior.setBounds(200, 80, 300, 60); // baja un poco para no tapar la barra superior
        fondo.add(fondoSuperior);

        JLabel iconoTitulo = new JLabel(new ImageIcon(getClass().getResource("/Sources/viendo-una-pelicula.png")));
        iconoTitulo.setBounds(500, 50, 128, 128);
        fondo.add(iconoTitulo);

        // Botones centrales de navegación
        botonCartelera = crearBotonPanel("Cartelera", "/Sources/claquetaCartelera.png", 540, 250);
        botonTickets = crearBotonPanel("Tickets", "/Sources/billete.png", 100, 250);
        botonControlSalas = crearBotonPanel("Control de salas", "/Sources/cineSalas.png", 320, 250);

        fondo.add(botonCartelera);
        fondo.add(botonTickets);
        fondo.add(botonControlSalas);
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

    public JButton getBotonCartelera() {
        return botonCartelera;
    }

    public JButton getBotonTickets() {
        return botonTickets;
    }

    public JButton getBotonControlSalas() {
        return botonControlSalas;
    }

    public JButton getBotonAgregarUsuario() {
        return botonAgregarUsuario;
    }

    public JButton getBotonReportes() {
        return botonReportes;
    }

    public JButton getBotonLogout() {
        return botonLogout;
    }

    public static void main(String[] args) {
        VentanaDashboard ventanaDashboard = new VentanaDashboard();
        ventanaDashboard.setVisible(true);
    }

}
