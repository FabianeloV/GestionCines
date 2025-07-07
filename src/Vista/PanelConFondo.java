package Vista;

import javax.swing.*;
import java.awt.*;

public class PanelConFondo extends JPanel {
    private Image imagenFondo;

    public PanelConFondo(String rutaImagen) {
        ImageIcon icon = new ImageIcon(getClass().getResource(rutaImagen));
        imagenFondo = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja la imagen escalada para llenar todo el panel
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
