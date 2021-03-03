
package org.victorhernandez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.victorhernandez.db.Conexion;
import org.victorhernandez.reportes.GenerarReporte;
import org.victorhernandez.system.Principal;

public class LoginController implements Initializable{
    
    private Principal escenarioPrincipal;
    private ObservableList<String> TipoUsuarios = FXCollections.observableArrayList("Root","Administrador","Normal");
    private boolean compuerta;
    
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox cmbTipoUsuario;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        cmbTipoUsuario.setItems(TipoUsuarios);
    }
    
    public boolean comprobarRegistro(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarUsuario()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                if(resultado.getString("Usuario").equals(txtUsuario.getText())){
                    compuerta = true;
                    if((resultado.getString("ContrasenaUsuario").equals(txtPassword.getText()) && compuerta)){
                        compuerta = true;
                        if((resultado.getString("TipoUsuario").equals(cmbTipoUsuario.getSelectionModel().getSelectedItem()) && compuerta)){
                            compuerta = true;
                            break;
                        
                        }else if(resultado.getString("TipoUsuario") != cmbTipoUsuario.getSelectionModel().getSelectedItem()){
                            compuerta = false;
                            JOptionPane.showMessageDialog(null, "Tipo de usuario incorrecto");
                        } 
                    }else if(resultado.getString("ContrasenaUsuario") != txtPassword.getText()){
                        compuerta = false;
                      JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return compuerta;
    }
    
    public void loginPrograma(){
        if(comprobarRegistro()){
            escenarioPrincipal.menuPrincipal();
        }else{
            //JOptionPane.showMessageDialog(null, "Intente ingresar de nuevo, hay algún problema con su usuario");
        }
    }
    
    public void nuevo(){
        escenarioPrincipal.ventanaNueva();
    }
    
    public void limpiarControles(){
        txtUsuario.setText("");
        txtPassword.setText("");
        cmbTipoUsuario.setValue("");
    
    }
    
    public void ventanaUsuarios(){
        escenarioPrincipal.ventanaUsuarios();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
     public void generarReporteUsuarios(){
        Map parametros = new HashMap();
        parametros.put("_CodigoUsuario", null);
        GenerarReporte.mostrarReporte("ReporteUsuarios.jasper", "Reporte de Usuarios", parametros);
    }
    
    
    
    
    
    
     
    
}
