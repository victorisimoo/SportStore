//@author: Victor Hernández Meléndez.

package org.victorhernandez.bean;

//Creación de la clase EmailCliente
public class EmailCliente {
    
    //Atributos de la clase EmailCliente.
    private int CodigoEmailCliente;
    private String EmailCliente;
    private String DescripcionEmailCliente;
    private int CodigoCliente;
    private String NombreCliente;
    
    //Costructor vacío de la clase EmailCliente.
    public EmailCliente(){
        
    }

    //Costructor de la clase EmailClientes con todos los atributos entrantes.
    public EmailCliente(int CodigoEmailCliente, String EmailCliente, String DescripcionEmailCliente, int CodigoCliente, String NombreCliente) {
        this.CodigoEmailCliente = CodigoEmailCliente;
        this.EmailCliente = EmailCliente;
        this.DescripcionEmailCliente = DescripcionEmailCliente;
        this.CodigoCliente = CodigoCliente;
        this.NombreCliente = NombreCliente;
    }

    //Métodos get y set para los atributos de la clase.

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }
    
    public int getCodigoEmailCliente() {
        return CodigoEmailCliente;
    }

    public void setCodigoEmailCliente(int CodigoEmailCliente) {
        this.CodigoEmailCliente = CodigoEmailCliente;
    }

    public String getEmailCliente() {
        return EmailCliente;
    }

    public void setEmailCliente(String EmailCliente) {
        this.EmailCliente = EmailCliente;
    }

    public String getDescripcionEmailCliente() {
        return DescripcionEmailCliente;
    }

    public void setDescripcionEmailCliente(String DescripcionEmailCliente) {
        this.DescripcionEmailCliente = DescripcionEmailCliente;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }
    
    //Método para concatenar datos en el combobox.
    public String toString(){
        return getCodigoEmailCliente()+ ".";
    }
    
    
}
