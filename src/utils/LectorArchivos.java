package utils;
import Modelo.Reporte;

//leer archivos
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;

//usar listas
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class LectorArchivos {

    public static List<Reporte> leerReportesArchivo(String rutaArchivo){
        List<Reporte> listaReportes = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
            //Guardara temporalmente el texto de cada linea del archivo
            String linea;

            while((linea = br.readLine())!=null){
                //dividimos la linea en partes separatadas por comas
                String[] parte = linea.split(";");

                if(parte.length==3){
                    String nombreReporte = parte[0].trim();
                    String categoriaReporte = parte[1].trim();
                    //convetimos a double
                    double valorReporte =Double.parseDouble(parte[2].trim());
                    //convetimos a fecha
                    LocalDate fecha = LocalDate.parse(parte[3]);

                    Reporte reporte = new Reporte(nombreReporte,categoriaReporte,valorReporte,fecha);
                    listaReportes.add(reporte);

                }
            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al leer la ruta del archivo");
        }

        return listaReportes;
    }
}
