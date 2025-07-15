package Modelo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportadorExcel {

    public void exportar(String nombreArchivo, String[] encabezados, List<Object[]> datos) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte");

        // Estilo para encabezados (negrita)
        CellStyle estiloEncabezado = workbook.createCellStyle();
        Font fuente = workbook.createFont();
        fuente.setBold(true);
        estiloEncabezado.setFont(fuente);

        // Crear fila encabezado
        Row filaEncabezado = sheet.createRow(0);
        for (int i = 0; i < encabezados.length; i++) {
            Cell celda = filaEncabezado.createCell(i);
            celda.setCellValue(encabezados[i]);
            celda.setCellStyle(estiloEncabezado);
        }

        // Rellenar datos
        int filaNum = 1;
        for (Object[] filaDatos : datos) {
            Row fila = sheet.createRow(filaNum++);
            for (int i = 0; i < filaDatos.length; i++) {
                if (filaDatos[i] != null) {
                    fila.createCell(i).setCellValue(filaDatos[i].toString());
                }
            }
        }

        // Ajustar ancho columnas
        for (int i = 0; i < encabezados.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Guardar archivo
        try (FileOutputStream fileOut = new FileOutputStream(nombreArchivo + ".xlsx")) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Archivo Excel '" + nombreArchivo + "' exportado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al exportar Excel: " + e.getMessage());
        }
    }
}
