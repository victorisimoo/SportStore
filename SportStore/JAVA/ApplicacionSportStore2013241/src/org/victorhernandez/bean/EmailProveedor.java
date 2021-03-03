//@author: Victor Hernández Meléndez
package org.victorhernandez.bean;

//Creación de clase EmailProveedor.
public class EmailProveedor {
  
    //Atributos de la clase EmailProveedor
    private int CodigoEmailProveedor;
    private String DescripcionEmail;
    private String EmailProveedor;
    private int CodigoProveedor;
    private String RazonSocial;
    
    //Costructor vacío de la clase EmailProveedor.
    public EmailProveedor(){
        
    }

    //Costructor con atributos entrantes.
    public EmailProveedor(int CodigoEmailProveedor, String DescripcionEmail, String EmailProveedor, int CodigoProveedor, String RazonSocial) {
        this.CodigoEmailProveedor = CodigoEmailProveedor;
        this.DescripcionEmail = DescripcionEmail;
        this.EmailProveedor = EmailProveedor;
        this.CodigoProveedor = CodigoProveedor;
        this.RazonSocial = RazonSocial;
    }

    // Métodos get y set de los atributos de la clase.

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }
     
    public int getCodigoEmailProveedor() {
        return CodigoEmailProveedor;
    }

    public void setCodigoEmailProveedor(int CodigoEmailProveedor) {
        this.CodigoEmailProveedor = CodigoEmailProveedor;
    }

    public String getDescripcionEmail() {
        return DescripcionEmail;
    }

    public void setDescripcionEmail(String DescripcionEmail) {
        this.DescripcionEmail = DescripcionEmail;
    }

    public String getEmailProveedor() {
        return EmailProveedor;
    }

    public void setEmailProveedor(String EmailProveedor) {
        this.EmailProveedor = EmailProveedor;
    }

    public int getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(int CodigoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
    }
    
    //Método para concatenar en el combobox.
    public String toString(){
        return getCodigoEmailProveedor()+ ".";
    }
}
