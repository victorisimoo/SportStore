//@author: Victor Hernández Meléndez.
package org.victorhernandez.bean;

//Creación de la clase DetalleFactura.
public class DetalleFactura {
    
    //Atributos de la clase DetalleFactura.
    private int CodigoDetalleFactura;
    private double Precio;
    private int Cantidad;
    private int CodigoProducto;
    private int NumeroFactura; 
    private String DescripcionProducto;
    
    //Costructor vacío de la clase DetalleFactura.
    public DetalleFactura(){
        
    }

    //Costructor con los parámetros que entrarán en la clase DetalleFactura.
    public DetalleFactura(int CodigoDetalleFactura, double Precio, int Cantidad, int CodigoProducto, int NumeroFactura, String DescripcionProducto) {
        this.CodigoDetalleFactura = CodigoDetalleFactura;
        this.Precio = Precio;
        this.Cantidad = Cantidad;
        this.CodigoProducto = CodigoProducto;
        this.NumeroFactura = NumeroFactura;
        this.DescripcionProducto = DescripcionProducto;
    }

    //Creación de métodos get y set para los atributos de la clase.

    public String getDescripcionProducto() {
        return DescripcionProducto;
    }

    public void setDescripcionProducto(String DescripcionProducto) {
        this.DescripcionProducto = DescripcionProducto;
    }
    
    public int getCodigoDetalleFactura() {
        return CodigoDetalleFactura;
    }

    public void setCodigoDetalleFactura(int CodigoDetalleFactura) {
        this.CodigoDetalleFactura = CodigoDetalleFactura;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
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

    public int getNumeroFactura() {
        return NumeroFactura;
    }

    public void setNumeroFactura(int NumeroFactura) {
        this.NumeroFactura = NumeroFactura;
    }
    
    //Método para concatenar los datos en el combobox.
    public String toString(){
        return getNumeroFactura()+ ".";
    }
    
}
