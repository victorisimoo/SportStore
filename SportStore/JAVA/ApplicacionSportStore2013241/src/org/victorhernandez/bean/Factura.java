//@author: Victor Hernández Meléndez.

package org.victorhernandez.bean;

//Creación de la clase Factura.
public class Factura {
    
    //Atributos de la clase Factura.
    private int NumeroFactura;
    private String FechaFactura;
    private String NitFactura;
    private String EstadoFactura;
    private int CodigoCliente;
    private String NombreCliente;
    private double TotalFactura;
    
    //Costructor vacío de la clase.
    public Factura(){
        
    }

    //Costructor con parámetros entrantes de la clase.
    public Factura(int NumeroFactura, String FechaFactura, String NitFactura, String EstadoFactura, int CodigoCliente, String NombreCliente, double TotalFactura) {
        this.NumeroFactura = NumeroFactura;
        this.FechaFactura = FechaFactura;
        this.NitFactura = NitFactura;
        this.EstadoFactura = EstadoFactura;
        this.CodigoCliente = CodigoCliente;
        this.NombreCliente = NombreCliente;
        this.TotalFactura = TotalFactura;
    }

    //Métodos get y set de la clase Factura.

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }
   
    public int getNumeroFactura() {
        return NumeroFactura;
    }

    public void setNumeroFactura(int NumeroFactura) {
        this.NumeroFactura = NumeroFactura;
    }

    public String getFechaFactura() {
        return FechaFactura;
    }

    public void setFechaFactura(String FechaFactura) {
        this.FechaFactura = FechaFactura;
    }
    
    public double getTotalFactura() {
        return TotalFactura;
    }

    public void setTotalFactura(double TotalFactura) {
        this.TotalFactura = TotalFactura;
    }

    public String getNitFactura() {
        return NitFactura;
    }

    public void setNitFactura(String NitFactura) {
        this.NitFactura = NitFactura;
    }

    public String getEstadoFactura() {
        return EstadoFactura;
    }

    public void setEstadoFactura(String EstadoFactura) {
        this.EstadoFactura = EstadoFactura;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }
    
    //Método para concatenar en el combobox.
    public String toString(){
        return getNumeroFactura()+ ". "+getNombreCliente();
    }
  
}
