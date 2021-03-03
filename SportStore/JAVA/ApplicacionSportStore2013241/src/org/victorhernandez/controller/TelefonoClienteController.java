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
import org.victorhernandez.bean.TelefonoCliente;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Cliente;
import org.victorhernandez.bean.Talla;
import org.victorhernandez.reportes.GenerarReporte;

public class TelefonoClienteController implements Initializable {
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TelefonoCliente>listarTelefonoCliente;
    private ObservableList<Cliente>listarCliente;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtNumeroTelefono;
    @FXML private ComboBox cmbCodigoCliente;
    @FXML private ComboBox cmbCodigoTelefono;
    @FXML private TableView tblTelefonoClientes;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colNumero;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colNombreCliente;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoTelefono.setItems(getTelefonoClientes());
        cmbCodigoCliente.setItems(getClientes());
    }
    
    
    public void cargarDatos(){
        tblTelefonoClientes.setItems(getTelefonoClientes());
        colCodigo.setCellValueFactory(new PropertyValueFactory<TelefonoCliente, Integer>("CodigoTelefonoCliente"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TelefonoCliente, String>("DescripcionTelefonoCliente"));
        colNumero.setCellValueFactory(new PropertyValueFactory<TelefonoCliente, String>("NumeroCliente"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<TelefonoCliente, Integer>("CodigoCliente"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<TelefonoCliente, String>("NombreCliente"));
    }
    
    public ObservableList<TelefonoCliente> getTelefonoClientes(){
        
        ArrayList<TelefonoCliente> lista = new ArrayList<TelefonoCliente>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTelefonoCliente}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TelefonoCliente(resultado.getInt("CodigoTelefonoCliente"), resultado.getString("DescripcionTelefonoCliente"), resultado.getString("NumeroCliente"), resultado.getInt("CodigoCliente"), resultado.getString("NombreCliente")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarTelefonoCliente = FXCollections.observableArrayList(lista);
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
        if(tblTelefonoClientes.getSelectionModel().getSelectedIndex() > -1){
            cmbCodigoTelefono.getSelectionModel().select(buscarTelefonoCliente(((TelefonoCliente) tblTelefonoClientes.getSelectionModel().getSelectedItem()).getCodigoTelefonoCliente()));
            txtDescripcion.setText(((TelefonoCliente)tblTelefonoClientes.getSelectionModel().getSelectedItem()).getDescripcionTelefonoCliente());
            txtNumeroTelefono.setText(((TelefonoCliente)tblTelefonoClientes.getSelectionModel().getSelectedItem()).getNumeroCliente());
            cmbCodigoCliente.getSelectionModel().select(buscarCliente(((TelefonoCliente) tblTelefonoClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        }
    }
    
    public TelefonoCliente buscarTelefonoCliente (int CodigoTelefonoCliente){
        TelefonoCliente  resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarTelefonoCliente(?)}");
            procedimiento.setInt(1, CodigoTelefonoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new TelefonoCliente(registro.getInt("CodigoTelefonoCliente"), registro.getString("DescripcionTelefonoCliente"), registro.getString("NumeroCliente"), registro.getInt("CodigoCliente"), registro.getString("NombreCliente"));
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
        txtNumeroTelefono.setEditable(true);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoTelefono.setDisable(true);
    }
    
    public void desactivarControles(){
        txtDescripcion.setEditable(false);
        txtNumeroTelefono.setEditable(false);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoTelefono.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        txtNumeroTelefono.setText("");
        cmbCodigoCliente.setValue("");
        cmbCodigoTelefono.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblTelefonoClientes.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    txtDescripcion.setEditable(true);
                    txtNumeroTelefono.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar un Teléfono!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtDescripcion.setEditable(false);
                txtNumeroTelefono.setEditable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarTelefonoCliente(?,?,?,?)}");
            TelefonoCliente registro = (TelefonoCliente)tblTelefonoClientes.getSelectionModel().getSelectedItem();
            registro.setCodigoTelefonoCliente(((TelefonoCliente)cmbCodigoTelefono.getSelectionModel().getSelectedItem()).getCodigoTelefonoCliente());
            registro.setDescripcionTelefonoCliente(txtDescripcion.getText());
            registro.setNumeroCliente(txtNumeroTelefono.getText());
            registro.setCodigoCliente(((Cliente)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            procedimiento.setInt(1, registro.getCodigoTelefonoCliente());
            procedimiento.setString(2, registro.getDescripcionTelefonoCliente());
            procedimiento.setString(3, registro.getNumeroCliente());
            procedimiento.setInt(4, registro.getCodigoCliente());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == TelefonoClienteController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = TelefonoClienteController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblTelefonoClientes.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar telefono cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarTelefonoCliente (?)}");
                                procedimiento.setInt(1, ((TelefonoCliente)tblTelefonoClientes.getSelectionModel().getSelectedItem()).getCodigoTelefonoCliente());
                                procedimiento.execute();
                                listarTelefonoCliente.remove(tblTelefonoClientes.getSelectionModel().getSelectedIndex());
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
        TelefonoCliente registro = new TelefonoCliente();
        registro.setDescripcionTelefonoCliente(txtDescripcion.getText());
        registro.setNumeroCliente(txtNumeroTelefono.getText());
        registro.setCodigoCliente(((Cliente)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarTelefonoCliente(?, ?, ?)}");
            procedimiento.setString(1, registro.getDescripcionTelefonoCliente());
            procedimiento.setString(2, registro.getNumeroCliente());
            procedimiento.setInt(3, registro.getCodigoCliente());
            procedimiento.execute();
            listarTelefonoCliente.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    } 
    
    public void generarReporte(){
        if(tipoDeOperaciones == TelefonoClienteController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = TelefonoClienteController.operaciones.NINGUNO;
        }else{
            if(tblTelefonoClientes.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoTc= ((TelefonoCliente) tblTelefonoClientes.getSelectionModel().getSelectedItem()).getCodigoTelefonoCliente();
                parametros.put("_CodigoTelefonoCliente", codigoTc);
                GenerarReporte.mostrarReporte("ReporteTelefonoCliente.jasper", "Reporte Telefono Cliente", parametros);
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
