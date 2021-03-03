//@author: Victor Hernández Meléndez.

package org.victorhernandez.bean;

//Creación de la clase Compra.
public class Compra {
    
    //Atributos de la clase Compra.
    private int NumeroDocumento;
    private String DescripcionCompra;
    private String FechaCompra;
    private int CodigoProveedor;
    private String RazonSocial;
    private double TotalCompra;

    //Constructor vacío de la clase Compra.
    public Compra(){
        
    }
    
    //Costructor de la clase Compra con los atributos entrantes. 
    public Compra(int NumeroDocumento, String DescripcionCompra, String FechaCompra, int CodigoProveedor, String RazonSocial, double TotalCompra) {
        this.NumeroDocumento = NumeroDocumento;
        this.DescripcionCompra = DescripcionCompra;
        this.FechaCompra = FechaCompra;
        this.CodigoProveedor = CodigoProveedor;
        this.RazonSocial = RazonSocial;
        this.TotalCompra = TotalCompra;
    }

    //Creación de los métodos get y set.
    
    //Métodos get y set de NumeroDocumento.
    public int getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(int NumeroDocumento) {
        this.NumeroDocumento = NumeroDocumento;
    }

    //Métodos get y set de DescripcionCompra.
    public String getDescripcionCompra() {
        return DescripcionCompra;
    }

    public void setDescripcionCompra(String DescripcionCompra) {
        this.DescripcionCompra = DescripcionCompra;
    }

    //Métodos get y set de FechaCompra.
    public String getFechaCompra() {
        return FechaCompra;
    }

    public void setFechaCompra(String FechaCompra) {
        this.FechaCompra = FechaCompra;
    }

    //Métodos get y set de CodigoProveedor.
    public int getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(int CodigoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }
    
    public double getTotalCompra() {
        return TotalCompra;
    }

    public void setTotalCompra(double TotalCompra) {
        this.TotalCompra = TotalCompra;
    }
    
    //Método para concatenar en el combobox.
    public String toString(){
        return getNumeroDocumento()+ ". "+getDescripcionCompra();
    }
  
}
