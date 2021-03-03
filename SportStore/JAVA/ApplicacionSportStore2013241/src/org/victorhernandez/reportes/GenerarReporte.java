//@author: Victor Noé Hernández Meléndez.

package org.victorhernandez.reportes;

import net.sf.jasperreports.engine.util.JRLoader;// Carga el reporte.
import net.sf.jasperreports.engine.JasperReport;//Crea los informes para poderlos enviar a la impresora, etc.
import net.sf.jasperreports.engine.JasperPrint; //Imprime los datos.
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer; //Para poder mostrar el reporte del lado de NetBeans.
import java.util.Map;
import java.io.InputStream;
import org.victorhernandez.db.Conexion;


public class GenerarReporte {
    public static void mostrarReporte(String nombreReporte, String titulo, Map parametros){
        InputStream reporte = GenerarReporte.class.getResourceAsStream(nombreReporte);
        try{
            JasperReport reporteMaestro = (JasperReport) JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteMaestro, parametros, Conexion.getInstancia().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
}
