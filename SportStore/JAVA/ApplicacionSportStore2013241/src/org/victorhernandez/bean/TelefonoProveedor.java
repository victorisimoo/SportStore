//@author: Victor Hernández Meléndez

package org.victorhernandez.bean;

//Creación de clase TelefonoProveedor.
public class TelefonoProveedor {
    
    //Atributos de la clase TelefonoProveedor.
    private int CodigoTelefonoProveedor;
    private String NumeroTelefono;
    private String DescripcionTelefono;
    private int CodigoProveedor;
    private String RazonSocial;
    
    //Costructor vacío de la clase TelefonoProveedor.
    public TelefonoProveedor(){
        
    }

    //Costructor con los parámetros entrantes de la clase TelefonoProveedor.
    public TelefonoProveedor(int CodigoTelefonoProveedor, String NumeroTelefono, String DescripcionTelefono, int CodigoProveedor, String RazonSocial) {
        this.CodigoTelefonoProveedor = CodigoTelefonoProveedor;
        this.NumeroTelefono = NumeroTelefono;
        this.DescripcionTelefono = DescripcionTelefono;
        this.CodigoProveedor = CodigoProveedor;
        this.RazonSocial = RazonSocial;
    }

    //Métodos get y set de la clase TelefonoProveedor.
    
    public int getCodigoTelefonoProveedor() {
        return CodigoTelefonoProveedor;
    }

    public void setCodigoTelefonoProveedor(int CodigoTelefonoProveedor) {
        this.CodigoTelefonoProveedor = CodigoTelefonoProveedor;
    }

    public String getNumeroTelefono() {
        return NumeroTelefono;
    }

    public void setNumeroTelefono(String NumeroTelefono) {
        this.NumeroTelefono = NumeroTelefono;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }
    
    public String getDescripcionTelefono() {
        return DescripcionTelefono;
    }

    public void setDescripcionTelefono(String DescripcionTelefono) {
        this.DescripcionTelefono = DescripcionTelefono;
    }

    public int getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(int CodigoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
    }
    
    //Método par concatenar el combobox.
    public String toString(){
        return getCodigoTelefonoProveedor()+ ".";
    }
    
    
}
