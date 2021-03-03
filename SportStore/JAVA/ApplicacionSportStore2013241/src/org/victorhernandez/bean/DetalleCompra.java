//@author: Victor Hernandez Meléndez.
package org.victorhernandez.bean;

// Creción de la clase DetalleCompra.
public class DetalleCompra {
    
    //Atributos de la clase DetalleCompra.
    private int CodigoDetalleCompra;
    private int NumeroDocumento;
    private double CostoProducto;
    private double CostoUnitario;
    private int Cantidad;
    private int CodigoProducto;
    private String DescripcionProducto;
    
    //Costructor vacío de la clase DetalleCompra.
    public DetalleCompra(){
        
    }

    //Costructor con los todos los parámetros de la clase DetalleCompra.
    public DetalleCompra(int CodigoDetalleCompra, int NumeroDocumento, double CostoProducto, double CostoUnitario, int Cantidad, int CodigoProducto, String DescripcionProducto) {
        this.CodigoDetalleCompra = CodigoDetalleCompra;
        this.NumeroDocumento = NumeroDocumento;
        this.CostoProducto = CostoProducto;
        this.CostoUnitario = CostoUnitario;
        this.Cantidad = Cantidad;
        this.CodigoProducto = CodigoProducto;
        this.DescripcionProducto = DescripcionProducto;
    }

    // Creación de métodos get y set para los atributos de la clase.

    public String getDescripcionProducto() {
        return DescripcionProducto;
    }

    public void setDescripcionProducto(String DescripcionProducto) {
        this.DescripcionProducto = DescripcionProducto;
    }
    
    public int getCodigoDetalleCompra() {
        return CodigoDetalleCompra;
    }

    public void setCodigoDetalleCompra(int CodigoDetalleCompra) {
        this.CodigoDetalleCompra = CodigoDetalleCompra;
    }

    public int getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(int NumeroDocumento) {
        this.NumeroDocumento = NumeroDocumento;
    }

    public double getCostoProducto() {
        return CostoProducto;
    }

    public void setCostoProducto(double CostoProducto) {
        this.CostoProducto = CostoProducto;
    }

    public double getCostoUnitario() {
        return CostoUnitario;
    }

    public void setCostoUnitario(double CostoUnitario) {
        this.CostoUnitario = CostoUnitario;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public int getCodigoProducto() {
        return CodigoProducto;
    }

    public void setCodigoProducto(int CodigoProducto) {
        this.CodigoProducto = CodigoProducto;
    }

    //Método utilizado para concatenar en el combobox.
    public String toString(){
        return getCodigoDetalleCompra()+ ".";
    }
  
}
