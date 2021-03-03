//@author: Victor Hernández Meléndez

package org.victorhernandez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory; 
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import org.victorhernandez.bean.EmailProveedor;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.EmailCliente;
import org.victorhernandez.bean.Proveedor;
import org.victorhernandez.reportes.GenerarReporte;


public class EmailProveedorController implements Initializable {

    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<EmailProveedor>listarEmailProveedor;
    private ObservableList<Proveedor>listarProveedor;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtEmail;
    @FXML private ComboBox cmbCodigoProveedor;
    @FXML private ComboBox cmbCodigoEmail;
    @FXML private TableView tblEmailP;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colEmail;
    @FXML private TableColumn colCodigoP;
    @FXML private TableColumn colRazonSocial;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoEmail.setItems(getEmailProveedores());
        cmbCodigoProveedor.setItems(getProveedores());
    }
    
    public void cargarDatos(){
        tblEmailP.setItems(getEmailProveedores());
        colCodigo.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("CodigoEmailProveedor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("DescripcionEmail"));
        colEmail.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("EmailProveedor"));
        colCodigoP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("CodigoProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("RazonSocial"));
    }
    
    public ObservableList<EmailProveedor> getEmailProveedores(){
        ArrayList<EmailProveedor> lista = new ArrayList<EmailProveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarEmailProveedor}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new EmailProveedor(resultado.getInt("CodigoEmailProveedor"), resultado.getString("DescripcionEmail"), resultado.getString("EmailProveedor"), resultado.getInt("CodigoProveedor"), resultado.getString("RazonSocial")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarEmailProveedor = FXCollections.observableArrayList(lista);
    }
    
     public ObservableList<Proveedor>getProveedores(){    
        ArrayList <Proveedor> lista = new ArrayList<Proveedor>();    
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarProveedor}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedor(resultado.getInt("CodigoProveedor"), resultado.getString("RazonSocial"), resultado.getString("NitProveedor"), resultado.getString("DireccionProveedor"), resultado.getString("PaginaWeb"), resultado.getString("ContactoPrincipal")));
            }  
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listarProveedor = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElementos(){
        if(tblEmailP.getSelectionModel().getSelectedIndex() > -1){
            cmbCodigoEmail.getSelectionModel().select(buscarEmailProveedor(((EmailProveedor) tblEmailP.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
            txtDescripcion.setText(((EmailProveedor)tblEmailP.getSelectionModel().getSelectedItem()).getDescripcionEmail());
            txtEmail.setText(((EmailProveedor)tblEmailP.getSelectionModel().getSelectedItem()).getEmailProveedor());
            cmbCodigoProveedor.getSelectionModel().select(buscarProveedor(((EmailProveedor) tblEmailP.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        }
    }
   
    public  EmailProveedor buscarEmailProveedor (int CodigoEmailProveedor){
        EmailProveedor  resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarEmailProveedor(?)}");
            procedimiento.setInt(1, CodigoEmailProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new EmailProveedor(registro.getInt("CodigoEmailProveedor"), registro.getString("DescripcionEmail"), registro.getString("EmailProveedor"), registro.getInt("CodigoProveedor"), registro.getString("RazonSocial"));
            }             
        } catch(SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Proveedor buscarProveedor (int CodigoProveedor){
        Proveedor resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarProveedor(?)}");
            procedimiento.setInt(1, CodigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Proveedor (registro.getInt("CodigoProveedor"), registro.getString("RazonSocial"), registro.getString("NitProveedor"), registro.getString("DireccionProveedor"), registro.getString("PaginaWeb"), registro.getString("ContactoPrincipal"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }

    public void nuevo(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperaciones = EmailProveedorController.operaciones.GUARDAR;
            break;
            
            case GUARDAR:
                agregar();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = EmailProveedorController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void activarControles(){
        txtDescripcion.setEditable(true);
        txtEmail.setEditable(true);
        cmbCodigoProveedor.setDisable(false);
        cmbCodigoEmail.setDisable(true);
    }
    
    public void desactivarControles(){
        txtDescripcion.setEditable(false);
        txtEmail.setEditable(false);
        cmbCodigoProveedor.setDisable(false);
        cmbCodigoEmail.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        txtEmail.setText("");
        cmbCodigoProveedor.setValue("");
        cmbCodigoEmail.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblEmailP.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = EmailProveedorController.operaciones.ACTUALIZAR;
                    txtDescripcion.setEditable(true);
                    txtEmail.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar un Email!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtDescripcion.setEditable(false);
                txtEmail.setEditable(false);
                tipoDeOperaciones = EmailProveedorController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarEmailProveedor(?,?,?,?)}");
            EmailProveedor registro = (EmailProveedor)tblEmailP.getSelectionModel().getSelectedItem();
            registro.setCodigoEmailProveedor(((EmailProveedor)cmbCodigoEmail.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
            registro.setDescripcionEmail(txtDescripcion.getText());
            registro.setEmailProveedor(txtEmail.getText());
            registro.setCodigoProveedor(((Proveedor)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getDescripcionEmail());
            procedimiento.setString(3, registro.getEmailProveedor());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        tipoDeOperaciones = EmailProveedorController.operaciones.ELIMINAR;
        switch(tipoDeOperaciones){
            case ELIMINAR:
                if(tblEmailP.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar email proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarEmailProveedor (?)}");
                            procedimiento.setInt(1, ((EmailProveedor)tblEmailP.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
                            procedimiento.execute();
                            listarEmailProveedor.remove(tblEmailP.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                            cargarDatos();
                        }catch(SQLException e){
                            e.printStackTrace();
                            
                        }
                    }
                }
        }
    }
    
    public void agregar(){
        EmailProveedor registro = new EmailProveedor();
        registro.setDescripcionEmail(txtDescripcion.getText());
        registro.setEmailProveedor(txtEmail.getText());
        registro.setCodigoProveedor(((Proveedor)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarEmailProveedores(?, ?, ?)}");
            procedimiento.setString(1, registro.getDescripcionEmail());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setInt(3, registro.getCodigoProveedor());
            procedimiento.execute();
            listarEmailProveedor.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }  

    public void generarReporte(){
        if(tipoDeOperaciones == EmailProveedorController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = EmailProveedorController.operaciones.NINGUNO;
        }else{
            if(tblEmailP.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoEmailP = ((EmailProveedor) tblEmailP.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor();
                parametros.put("_CodigoEmailProveedor", codigoEmailP);
                GenerarReporte.mostrarReporte("ReporteEmailProveedor.jasper", "Reporte de Email Proveedores", parametros);
            }
        }
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    } 
    
}
