package Modelo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportadorExcel {

    public static void exportar(List<Reporte> reportes, String nombreArchivo) {
        Workbook workbook = new XSSFWorkbook(); // Crea el libro de Excel
        Sheet sheet = workbook.createSheet("Reporte Cine"); // Hoja con el nombre "Reporte Cine"

        // Estilo para encabezados (negrita)
        CellStyle estiloEncabezado = workbook.createCellStyle();
        Font fuente = workbook.createFont();
        fuente.setBold(true);
        estiloEncabezado.setFont(fuente);

        // Crear fila de encabezado
        Row encabezado = sheet.createRow(0);
        encabezado.createCell(0).setCellValue("Tipo de Reporte");
        encabezado.createCell(1).setCellValue("Fecha");
        encabezado.createCell(2).setCellValue("Valor ($)");

        for (Cell cell : encabezado) {
            cell.setCellStyle(estiloEncabezado);
        }

        // Llenar datos de cada reporte
        int fila = 1; // comenzamos en la fila 1 (después del encabezado)
        for (Reporte r : reportes) {
            Row row = sheet.createRow(fila++);
            row.createCell(0).setCellValue(r.getCategoriaReporte());
            row.createCell(1).setCellValue(r.getFecha().toString());
            row.createCell(2).setCellValue(r.getValor());
        }

        // Ajustar ancho de columnas automáticamente
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // Guardar el archivo en el disco
        try (FileOutputStream fileOut = new FileOutputStream(nombreArchivo)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Archivo Excel '" + nombreArchivo + "' exportado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al exportar Excel: " + e.getMessage());
        }
    }
}
