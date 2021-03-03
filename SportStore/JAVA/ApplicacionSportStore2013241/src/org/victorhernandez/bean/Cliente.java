//@author: Victor Hernandez Meléndez.
package org.victorhernandez.bean;

//Creación de la clase Cliente
public class Cliente {
    
    //Atributos de la clase Cliente.
    private int CodigoCliente;
    private String NombreCliente;
    private String NitCliente;
    private String DireccionCliente;
    
    //Constructor vacío de la clase Cliente.
    public Cliente(){
        
    }
    
    //Costructor con los parámetros entrantes de la clase Cliente.
    public Cliente(int CodigoCliente, String NombreCliente, String NitCliente, String DireccionCliente) {
        this.CodigoCliente = CodigoCliente;
        this.NombreCliente = NombreCliente;
        this.NitCliente = NitCliente;
        this.DireccionCliente = DireccionCliente;
    }

    //Creación de los métodos get y set de los atributos.
    
    //Creación del método get y set de CodigoCliente.
    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }

    //Creación del método get y set de NombreCliente.
    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreVliente) {
        this.NombreCliente = NombreVliente;
    }

    //Creación del método get y set de NitCliente.
    public String getNitCliente() {
        return NitCliente;
    }

    public void setNitCliente(String NitCliente) {
        this.NitCliente = NitCliente;
    }

    //Creación del método get y set de DireccionCliente.
    public String getDireccionCliente() {
        return DireccionCliente;
    }

    public void setDireccionCliente(String DireccionCliente) {
        this.DireccionCliente = DireccionCliente;
    }
    
    //Método encargado de concatenar para el combobox.
    public String toString(){
        return getCodigoCliente()+ ". " +getNombreCliente();
    }
    
}
