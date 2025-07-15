package Modelo;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportadorPDF {

    public static void exportar(List<Reporte> reportes, String nombreArchivo) {
        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            // Título
            Paragraph titulo = new Paragraph("Reporte de Cine", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

            // Tabla
            PdfPTable tabla = new PdfPTable(3); // 3 columnas: Tipo, Fecha, Valor
            tabla.setWidthPercentage(100);

            // Encabezados
            tabla.addCell("Tipo de Reporte");
            tabla.addCell("Fecha");
            tabla.addCell("Valor ($)");

            // Datos
            for (Reporte r : reportes) {
                tabla.addCell(r.getCategoriaReporte());
                tabla.addCell(r.getFecha().toString());
                tabla.addCell(String.format("%.2f", r.getValor()));
            }

            documento.add(tabla);

            documento.close();
            System.out.println("PDF creado correctamente.");
        } catch (DocumentException | IOException e) {
            System.out.println("Error al crear PDF: " + e.getMessage());
        }
    }
}
