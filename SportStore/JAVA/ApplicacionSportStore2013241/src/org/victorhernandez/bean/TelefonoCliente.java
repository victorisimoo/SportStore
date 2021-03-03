//@author: Victor Hernández Meléndez.

package org.victorhernandez.bean;

//Creación de la clase TelefonoCliente.
public class TelefonoCliente {
    
    //Atributos de la clase TelefonoCliente.
    private int CodigoTelefonoCliente;
    private String DescripcionTelefonoCliente;
    private String NumeroCliente;
    private int CodigoCliente;
    private String NombreCliente;

    //Costructor vacío de la clase TelefonoCliente.
    public TelefonoCliente(){
        
    }
    
    //Costructor con los parámetros entrantes de la clase TelefonoCliente.
    public TelefonoCliente(int CodigoTelefonoCliente, String DescripcionTelefonoCliente, String NumeroCliente, int CodigoCliente, String NombreCliente) {
        this.CodigoTelefonoCliente = CodigoTelefonoCliente;
        this.DescripcionTelefonoCliente = DescripcionTelefonoCliente;
        this.NumeroCliente = NumeroCliente;
        this.CodigoCliente = CodigoCliente;
        this.NombreCliente = NombreCliente;
    }

    //Métodos get y set de la clase TelefonoCliente. 

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }
    
    public int getCodigoTelefonoCliente() {
        return CodigoTelefonoCliente;
    }

    public void setCodigoTelefonoCliente(int CodigoTelefonoCliente) {
        this.CodigoTelefonoCliente = CodigoTelefonoCliente;
    }

    public String getDescripcionTelefonoCliente() {
        return DescripcionTelefonoCliente;
    }

    public void setDescripcionTelefonoCliente(String DescripcionTelefonoCliente) {
        this.DescripcionTelefonoCliente = DescripcionTelefonoCliente;
    }

    public String getNumeroCliente() {
        return NumeroCliente;
    }

    public void setNumeroCliente(String NumeroCliente) {
        this.NumeroCliente = NumeroCliente;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    } 
    
    //Método encargado de concatenar en el combobox.
    public String toString(){
        return getCodigoCliente()+ ".";
    }
}
