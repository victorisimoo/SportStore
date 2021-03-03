// @author: Victor Noé Hernández Meléndez.
package org.victorhernandez.bean;

//Creación de la clase Proveedor.
public class Proveedor {
    
    //Atributos de la clase Proveedor.
    private int CodigoProveedor;
    private String RazonSocial;
    private String NitProveedor;
    private String DireccionProveedor;
    private String PaginaWeb;
    private String ContactoPrincipal;
    
    //Costructor vacío de la clase Proveedor.
    public Proveedor(){
        
    }

    //Costructor con los parámetros entrantes a la clase.
    public Proveedor(int CodigoProveedor, String RazonSocial, String NitProveedor, String DireccionProveedor, String PaginaWeb, String ContactoPrincipal) {
        this.CodigoProveedor = CodigoProveedor;
        this.RazonSocial = RazonSocial;
        this.NitProveedor = NitProveedor;
        this.DireccionProveedor = DireccionProveedor;
        this.PaginaWeb = PaginaWeb;
        this.ContactoPrincipal = ContactoPrincipal;
    }

    //Métodos get y set de los atributos de la clase.
    
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

    public String getNitProveedor() {
        return NitProveedor;
    }

    public void setNitProveedor(String NitProveedor) {
        this.NitProveedor = NitProveedor;
    }

    public String getDireccionProveedor() {
        return DireccionProveedor;
    }

    public void setDireccionProveedor(String DireccionProveedor) {
        this.DireccionProveedor = DireccionProveedor;
    }

    public String getPaginaWeb() {
        return PaginaWeb;
    }

    public void setPaginaWeb(String PaginaWeb) {
        this.PaginaWeb = PaginaWeb;
    }

    public String getContactoPrincipal() {
        return ContactoPrincipal;
    }

    public void setContactoPrincipal(String ContactoPrincipal) {
        this.ContactoPrincipal = ContactoPrincipal;
    }  
    
    //Método encargado de concatenar en el combobox.
    public String toString(){
        return getCodigoProveedor()+". " +getRazonSocial();
    }
}
