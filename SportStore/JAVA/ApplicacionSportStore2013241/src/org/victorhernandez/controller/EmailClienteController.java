//@author: Victor Hernández Meléndez.
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
import org.victorhernandez.bean.EmailCliente;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Cliente;
import org.victorhernandez.reportes.GenerarReporte;


public class EmailClienteController implements Initializable{
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<EmailCliente>listarEmailCliente;
    private ObservableList<Cliente>listarCliente;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtEmail;
    @FXML private ComboBox cmbCodigoCliente;
    @FXML private ComboBox cmbCodigoEmail;
    @FXML private TableView tblEmailClientes;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colEmail;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colNombreCliente;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoEmail.setItems(getEmailClientes());
        cmbCodigoCliente.setItems(getClientes());
    }
    
    
    public void cargarDatos(){
        tblEmailClientes.setItems(getEmailClientes());
        colCodigo.setCellValueFactory(new PropertyValueFactory<EmailCliente, Integer>("CodigoEmailCliente"));
        colEmail.setCellValueFactory(new PropertyValueFactory<EmailCliente, String>("EmailCliente"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<EmailCliente, String>("DescripcionEmailCliente"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<EmailCliente, Integer>("CodigoCliente"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<EmailCliente, String>("NombreCliente"));
    }
    
    public ObservableList<EmailCliente> getEmailClientes(){
        
        ArrayList<EmailCliente> lista = new ArrayList<EmailCliente>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarEmailCliente}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new EmailCliente(resultado.getInt("CodigoEmailCliente"), resultado.getString("EmailCliente"), resultado.getString("DescripcionEmailCliente"), resultado.getInt("CodigoCliente"), resultado.getString("NombreCliente")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarEmailCliente = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Cliente> getClientes(){
        
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCliente}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cliente(resultado.getInt("CodigoCliente"), resultado.getString("NombreCliente"), resultado.getString("NitCliente"), resultado.getString("DireccionCliente")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarCliente = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElementos(){
        if(tblEmailClientes.getSelectionModel().getSelectedIndex() > -1){
            cmbCodigoEmail.getSelectionModel().select(buscarEmailCliente(((EmailCliente) tblEmailClientes.getSelectionModel().getSelectedItem()).getCodigoEmailCliente()));
            txtEmail.setText(((EmailCliente)tblEmailClientes.getSelectionModel().getSelectedItem()).getEmailCliente());
            txtDescripcion.setText(((EmailCliente)tblEmailClientes.getSelectionModel().getSelectedItem()).getDescripcionEmailCliente());
            cmbCodigoCliente.getSelectionModel().select(buscarCliente(((EmailCliente) tblEmailClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        }
    }
   
    public  EmailCliente buscarEmailCliente (int CodigoEmailCliente){
        EmailCliente  resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarEmailCliente(?)}");
            procedimiento.setInt(1, CodigoEmailCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new EmailCliente(registro.getInt("CodigoEmailCliente"), registro.getString("EmailCliente"), registro.getString("DescripcionEmailCliente"), registro.getInt("CodigoCliente"), registro.getString("NombreCliente"));
            }             
        } catch(SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Cliente buscarCliente (int CodigoCliente){
        Cliente  resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarCliente(?)}");
            procedimiento.setInt(1, CodigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Cliente(registro.getInt("CodigoCliente"), registro.getString("NombreCliente"), registro.getString("NitCliente"), registro.getString("DireccionCliente"));
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
                tipoDeOperaciones = operaciones.GUARDAR;
            break;
            
            case GUARDAR:
                agregar();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void activarControles(){
        txtDescripcion.setEditable(true);
        txtEmail.setEditable(true);
        cmbCodigoCliente.setEditable(false);
        cmbCodigoEmail.setDisable(true);
    }
    
    public void desactivarControles(){
        txtDescripcion.setEditable(false);
        txtEmail.setEditable(false);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoEmail.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        txtEmail.setText("");
        cmbCodigoCliente.setValue("");
        cmbCodigoEmail.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblEmailClientes.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarEmailCliente(?,?,?,?)}");
            EmailCliente registro = (EmailCliente)tblEmailClientes.getSelectionModel().getSelectedItem();
            registro.setCodigoEmailCliente(((EmailCliente)cmbCodigoEmail.getSelectionModel().getSelectedItem()).getCodigoEmailCliente());
            registro.setEmailCliente(txtEmail.getText());
            registro.setDescripcionEmailCliente(txtDescripcion.getText());
            registro.setCodigoCliente(((Cliente)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            procedimiento.setInt(1, registro.getCodigoEmailCliente());
            procedimiento.setString(2, registro.getEmailCliente());
            procedimiento.setString(3, registro.getDescripcionEmailCliente());
            procedimiento.setInt(4, registro.getCodigoCliente());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == EmailClienteController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = EmailClienteController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblEmailClientes.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar email cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarEmailCliente (?)}");
                                procedimiento.setInt(1, ((EmailCliente)tblEmailClientes.getSelectionModel().getSelectedItem()).getCodigoEmailCliente());
                                procedimiento.execute();
                                listarEmailCliente.remove(tblEmailClientes.getSelectionModel().getSelectedIndex());
                                limpiarControles();
                                cargarDatos();
                            }catch(SQLException e){
                                e.printStackTrace();
                            }
                        }
                    }
            }
        }
    }
    
    public void agregar(){
        EmailCliente registro = new EmailCliente();
        registro.setEmailCliente(txtEmail.getText());
        registro.setDescripcionEmailCliente(txtDescripcion.getText());
        registro.setCodigoCliente(((Cliente)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarEmailCliente(?,?,?)}");
            procedimiento.setString(1, registro.getEmailCliente());
            procedimiento.setString(2, registro.getDescripcionEmailCliente());
            procedimiento.setInt(3, registro.getCodigoCliente());
            procedimiento.execute();
            listarEmailCliente.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }  
    
    public void generarReporte(){
        if(tipoDeOperaciones == EmailClienteController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = EmailClienteController.operaciones.NINGUNO;
        }else{
            if(tblEmailClientes.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoEmailCliente = ((EmailCliente) tblEmailClientes.getSelectionModel().getSelectedItem()).getCodigoEmailCliente();
                parametros.put("_CodigoEmailCliente", codigoEmailCliente);
                GenerarReporte.mostrarReporte("ReporteEmailCliente.jasper", "Reporte de Email Clientes", parametros);
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
