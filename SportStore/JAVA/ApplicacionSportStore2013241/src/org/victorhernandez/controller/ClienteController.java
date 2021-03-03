//@author: Victor Hernandez Meléndez.
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
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Cliente;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import org.victorhernandez.reportes.GenerarReporte;

public class ClienteController implements Initializable {
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Cliente>listarCliente;
    @FXML private TextField txtNombre;
    @FXML private TextField txtNit;
    @FXML private TextField txtDireccion;
    @FXML private ComboBox cmbCliente;
    @FXML private TableView tblClientes;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colNit;
    @FXML private TableColumn colDireccion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCliente.setItems(getClientes());
    }
    
    public void cargarDatos(){
        tblClientes.setItems(getClientes());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("CodigoCliente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("NombreCliente"));
        colNit.setCellValueFactory(new PropertyValueFactory<Cliente, String>("NitCliente"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("DireccionCliente"));
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
        if(tblClientes.getSelectionModel().getSelectedIndex() > -1){
            cmbCliente.getSelectionModel().select(buscarCliente(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            txtNombre.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
            txtNit.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getNitCliente());
            txtDireccion.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        }
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
        txtNombre.setEditable(true);
        txtNit.setEditable(true);
        txtDireccion.setEditable(true);
        cmbCliente.setDisable(true);
    }
    
    public void desactivarControles(){
        txtNombre.setEditable(false);
        txtNit.setEditable(false);
        txtDireccion.setEditable(false);
        cmbCliente.setDisable(false);
    }
    
    public void limpiarControles(){
        txtNombre.setText("");
        txtNit.setText("");
        txtDireccion.setText("");
        cmbCliente.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblClientes.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    txtNombre.setEditable(true);
                    txtNit.setEditable(true);
                    txtDireccion.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar un Cliente!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtNombre.setEditable(false);
                txtNit.setEditable(false);
                txtDireccion.setEditable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarCliente(?,?,?,?)}");
            Cliente registro = (Cliente)tblClientes.getSelectionModel().getSelectedItem();
            registro.setNombreCliente(txtNombre.getText());
            registro.setNitCliente(txtNit.getText());
            registro.setDireccionCliente(txtDireccion.getText());
            registro.setCodigoCliente(((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getNitCliente());
            procedimiento.setString(4, registro.getDireccionCliente());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == ClienteController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = ClienteController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblClientes.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarCliente (?)}");
                                procedimiento.setInt(1, ((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                                procedimiento.execute();
                                listarCliente.remove(tblClientes.getSelectionModel().getSelectedIndex());
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
        Cliente registro = new Cliente();
        registro.setNombreCliente(txtNombre.getText());
        registro.setNitCliente(txtNit.getText());
        registro.setDireccionCliente(txtDireccion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarCliente(?, ?, ?)}");
            procedimiento.setString(1, registro.getNombreCliente());
            procedimiento.setString(2, registro.getNitCliente());
            procedimiento.setString(3, registro.getDireccionCliente());
            procedimiento.execute();
            listarCliente.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }  
    
    public void generarReporte(){
        if(tipoDeOperaciones == ClienteController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = ClienteController.operaciones.NINGUNO;
        }else{
            if(tblClientes.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoCliente = ((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente();
                parametros.put("_CodigoCliente", codigoCliente);
                GenerarReporte.mostrarReporte("ReporteCliente.jasper", "Reporte de Clientes", parametros);
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
    
    public void ventanaEmailClientes(){
        escenarioPrincipal.ventanaEmailClientes();
    }
    
    public void ventanaTelefonoClientes(){
        escenarioPrincipal.ventanaTelefonoClientes();
    }
}
