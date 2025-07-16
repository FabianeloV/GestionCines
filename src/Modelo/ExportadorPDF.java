package Modelo;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportadorPDF {

    public void exportar(String nombreArchivo, String[] encabezados, List<Object[]> datos) {
        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo + ".pdf"));
            documento.open();

            // Título centrado
            Paragraph titulo = new Paragraph(nombreArchivo, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

            // Crear tabla con número de columnas igual al largo de encabezados
            PdfPTable tabla = new PdfPTable(encabezados.length);
            tabla.setWidthPercentage(100);

            // Estilo para encabezados: solo negrita y centrado (sin color)
            Font fuenteEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            // Agregar encabezados
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(encabezado, fuenteEncabezado));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celda);
            }

            // Agregar datos
            for (Object[] filaDatos : datos) {
                for (Object dato : filaDatos) {
                    tabla.addCell(dato != null ? dato.toString() : "");
                }
            }

            documento.add(tabla);
            documento.close();

            System.out.println("PDF creado correctamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            System.out.println("Error al crear PDF: " + e.getMessage());
        }
    }
}