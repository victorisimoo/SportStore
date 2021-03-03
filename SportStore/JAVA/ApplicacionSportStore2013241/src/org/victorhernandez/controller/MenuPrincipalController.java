//@author: Victor Hernández Meléndez.

package org.victorhernandez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.victorhernandez.system.Principal;
import javafx.fxml.Initializable;

public class MenuPrincipalController implements Initializable {
    
    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaCategorias (){
        escenarioPrincipal.ventanaCategorias();
    }
    
    public void ventanaMarcas (){
        escenarioPrincipal.ventanaMarcas();
    }
    
    public void ventanaTallas (){
        escenarioPrincipal.ventanaTallas();
    }
    
    public void ventanaProveedores(){
        escenarioPrincipal.ventanaProveedores();
    }
    
    public void ventanaClientes(){
        escenarioPrincipal.ventanaClientes();
    }
    
    public void ventanaFacturas(){
        escenarioPrincipal.ventanaFacturas();
    }
    
    public void ventanaInformacion(){
        escenarioPrincipal.ventanaInformacion();
    }
    
    public void ventanaProductos(){
        escenarioPrincipal.ventanaProductos();
    }
    
    public void ventanaCompras(){
        escenarioPrincipal.ventanaCompras();
    } 
    
    public void ventanaEmailClientes(){
        escenarioPrincipal.ventanaEmailClientes();
    }
    
    public void ventanaTelefonoClientes(){
        escenarioPrincipal.ventanaTelefonoClientes();
    }
    
    public void ventanaEmailProveedores(){
        escenarioPrincipal.ventanaEmailProveedores();
    }
    
    public void ventanaTelefonoProveedores(){
        escenarioPrincipal.ventanaTelefonoProveedores();
    }
    
    public void ventanaDetalleFacturas(){
        escenarioPrincipal.ventanaDetalleFacturas();
    }
    
    public void ventanaDetalleCompra(){
        escenarioPrincipal.ventanaDetalleCompra();
    }
    
    public void ventanaUsuarios(){
        escenarioPrincipal.ventanaUsuarios();
    }
    
    public void generarReporte(){
        escenarioPrincipal.generarReporte();
    }
}
