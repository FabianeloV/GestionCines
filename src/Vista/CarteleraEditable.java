package Vista;

import javax.swing.*;
import java.awt.*;

public class CarteleraEditable extends JFrame {
    private JPanel Principal1;
    private JScrollPane PanelScroll;
    private JPanel PanelMain;
    private JPanel PanelTitulo;
    private JButton añadirPeliculaButton;
    private JButton borrarPeliculaButton;
    private JButton editarButton;
    private JPanel BarraInferior;


    public JPanel getPanelMain() {
        return PanelMain;
    }

    public JButton getAñadirPeliculaButton() {
        return añadirPeliculaButton;
    }

    public JButton getBorrarPeliculaButton() {
        return borrarPeliculaButton;
    }

    public JButton getEditarButton() {
        return editarButton;
    }

    public CarteleraEditable() {

        setContentPane(Principal1);
        Principal1.setLayout(new BorderLayout());

        Principal1.add(PanelTitulo, BorderLayout.NORTH);

        Principal1.add(PanelScroll, BorderLayout.CENTER);

        Principal1.add(BarraInferior, BorderLayout.SOUTH);

        PanelScroll.setViewportView(PanelMain);
    }


}
