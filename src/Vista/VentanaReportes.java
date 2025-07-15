package Vista;

import javax.swing.*;
import java.awt.*;
import com.toedter.calendar.JDateChooser;

public class VentanaReportes extends JFrame {

    private JLabel lblTitulo;
    private JLabel lblReporte;
    private JLabel lblFormato;
    private JLabel lblFecha;
    private JLabel lblFechaInicio;
    private JLabel lblFechaFin;
    private JButton BotonBack;
    private JComboBox<String> comboTipoReporte;
    private JComboBox<String> comboFormatoReporte;
    private JDateChooser fechaInicio;
    private JDateChooser fechaFin;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JButton btnGenerar;


    public VentanaReportes() {
        setTitle("Generación de Reportes");
        setSize(610, 470);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel panelPrincipal = new JPanel(null);
        panelPrincipal.setBounds(0, 0, 610, 470);

        JLabel fondoIzquierdo = new JLabel(new ImageIcon(getClass().getResource("/Sources/Crea un hermoso degradado para tu diseño, generador de degradados CSS y PNG _ Gradients_app.jpeg")));
        fondoIzquierdo.setBounds(0, 0, 250, 470);

        BotonBack = crearBotonPanel("", "/Sources/contorno-fino-de-punta-de-flecha-a-la-izquierda.png",20,30);
        BotonBack.setBounds(10, 10, 50, 50);
        BotonBack.setOpaque(false);
        fondoIzquierdo.add(BotonBack);

        lblTitulo = new JLabel("Reportes Cines");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(60, 20, 140, 30);
        fondoIzquierdo.add(lblTitulo);

        lblReporte = new JLabel("Reporte:");
        lblReporte.setForeground(Color.WHITE);
        lblReporte.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblReporte.setBounds(80, 100, 100, 20);
        fondoIzquierdo.add(lblReporte);

        lblFormato = new JLabel("Formato:");
        lblFormato.setForeground(Color.WHITE);
        lblFormato.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFormato.setBounds(80, 190, 100, 20);
        fondoIzquierdo.add(lblFormato);

        lblFecha = new JLabel("Fecha:");
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFecha.setBounds(80, 290, 100, 20);
        fondoIzquierdo.add(lblFecha);

        panelPrincipal.add(fondoIzquierdo);

        JPanel panelDerecho = new JPanel(null);
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBounds(240, 0, 370, 470);

        comboTipoReporte = new JComboBox<>(new String[]{"Seleccionar", "Asistencia", "Ocupacion Salas", "Venta de tickets"});
        comboTipoReporte.setBounds(60, 100, 250, 25);
        panelDerecho.add(comboTipoReporte);

        comboFormatoReporte = new JComboBox<>(new String[]{"Seleccionar", "Excel", "PDF"});
        comboFormatoReporte.setBounds(60, 190, 250, 25);
        panelDerecho.add(comboFormatoReporte);

        lblFechaInicio = new JLabel("Fecha inicio:");
        lblFechaInicio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFechaInicio.setBounds(60, 250, 100, 20);
        panelDerecho.add(lblFechaInicio);

        lblFechaFin = new JLabel("Fecha fin:");
        lblFechaFin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFechaFin.setBounds(200, 250, 100, 20);
        panelDerecho.add(lblFechaFin);

        txtFechaInicio = new JTextField();
        fechaInicio = new JDateChooser();
        fechaInicio.setBounds(60, 290, 100, 25);
        panelDerecho.add(fechaInicio);

        fechaFin = new JDateChooser();
        fechaFin.setBounds(200, 290, 100, 25);
        panelDerecho.add(fechaFin);

        btnGenerar = new JButton("Generar");
        btnGenerar.setBounds(130, 340, 100, 30);
        panelDerecho.add(btnGenerar);

        panelPrincipal.add(panelDerecho);
        add(panelPrincipal);
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

    public JButton getBotonBack() {
        return BotonBack;
    }

    public JComboBox<String> getComboTipoReporte() {
        return comboTipoReporte;
    }

    public JComboBox<String> getComboFormatoReporte() {
        return comboFormatoReporte;
    }

    public JButton getBtnGenerar() {
        return btnGenerar;
    }

    public JDateChooser getFechaInicio() {
        return fechaInicio;
    }

    public JDateChooser getFechaFin() {
        return fechaFin;
    }
}
