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
import org.victorhernandez.bean.TelefonoProveedor;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Proveedor;
import org.victorhernandez.bean.TelefonoCliente;
import org.victorhernandez.reportes.GenerarReporte;

public class TelefonoProveedorController implements Initializable {
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TelefonoProveedor>listarTelefonoProveedor;
    private ObservableList<Proveedor>listarProveedor;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtNumeroTelefono;
    @FXML private ComboBox cmbCodigoProveedor;
    @FXML private ComboBox cmbCodigoTelefono;
    @FXML private TableView tblTelefonoProveedores;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colNumero;
    @FXML private TableColumn colCodigoProveedor;
    @FXML private TableColumn colRazonSocial;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoTelefono.setItems(getTelefonoProveedores());
        cmbCodigoProveedor.setItems(getProveedores());
    }
    
    
    public void cargarDatos(){
        tblTelefonoProveedores.setItems(getTelefonoProveedores());
        colCodigo.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("CodigoTelefonoProveedor"));
        colNumero.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("NumeroTelefono"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("DescripcionTelefono"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("CodigoProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("RazonSocial"));
    }
    
     public void seleccionarElementos(){
        if(tblTelefonoProveedores.getSelectionModel().getSelectedIndex() > -1){
            cmbCodigoTelefono.getSelectionModel().select(buscarTelefonoProveedor(((TelefonoProveedor) tblTelefonoProveedores.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
            txtNumeroTelefono.setText(((TelefonoProveedor)tblTelefonoProveedores.getSelectionModel().getSelectedItem()).getNumeroTelefono());
            txtDescripcion.setText(((TelefonoProveedor)tblTelefonoProveedores.getSelectionModel().getSelectedItem()).getDescripcionTelefono());
            cmbCodigoProveedor.getSelectionModel().select(buscarProveedor(((TelefonoProveedor) tblTelefonoProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        }
    }
    
    public ObservableList<TelefonoProveedor> getTelefonoProveedores(){      
        ArrayList<TelefonoProveedor> lista = new ArrayList<TelefonoProveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTelefonoProveedor}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TelefonoProveedor(resultado.getInt("CodigoTelefonoProveedor"), resultado.getString("NumeroTelefono"), resultado.getString("DescripcionTelefono"), resultado.getInt("CodigoProveedor"), resultado.getString("RazonSocial")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarTelefonoProveedor = FXCollections.observableArrayList(lista);
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
    
    public TelefonoProveedor buscarTelefonoProveedor (int CodigoTelefonoProveedor){
        TelefonoProveedor  resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarTelefonoProveedor(?)}");
            procedimiento.setInt(1, CodigoTelefonoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new TelefonoProveedor(registro.getInt("CodigoTelefonoProveedor"), registro.getString("NumeroTelefono"), registro.getString("DescripcionTelefono"), registro.getInt("CodigoProveedor"), registro.getString("RazonSocial"));
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
        cmbCodigoProveedor.setDisable(false);
        cmbCodigoTelefono.setDisable(true);
    }
    
    public void desactivarControles(){
        txtDescripcion.setEditable(false);
        txtNumeroTelefono.setEditable(false);
        cmbCodigoProveedor.setDisable(false);
        cmbCodigoTelefono.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        txtNumeroTelefono.setText("");
        cmbCodigoProveedor.setValue("");
        cmbCodigoTelefono.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblTelefonoProveedores.getSelectionModel().getSelectedItem() != null){
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarTelefonoProveedor(?,?,?,?)}");
            TelefonoProveedor registro = (TelefonoProveedor)tblTelefonoProveedores.getSelectionModel().getSelectedItem();
            registro.setCodigoTelefonoProveedor(((TelefonoProveedor)cmbCodigoTelefono.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
            registro.setNumeroTelefono(txtNumeroTelefono.getText());
            registro.setDescripcionTelefono(txtDescripcion.getText());
            registro.setCodigoProveedor(((Proveedor) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroTelefono());
            procedimiento.setString(3, registro.getDescripcionTelefono());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == TelefonoProveedorController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = TelefonoProveedorController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblTelefonoProveedores.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar telefono proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarTelefonoProveedor (?)}");
                                procedimiento.setInt(1, ((TelefonoProveedor)tblTelefonoProveedores.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                                procedimiento.execute();
                                listarTelefonoProveedor.remove(tblTelefonoProveedores.getSelectionModel().getSelectedIndex());
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
        TelefonoProveedor registro = new TelefonoProveedor();
        registro.setNumeroTelefono(txtNumeroTelefono.getText());
        registro.setDescripcionTelefono(txtDescripcion.getText());
        registro.setCodigoProveedor(((Proveedor) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarTelefonoProveedor(?, ?, ?)}");
            procedimiento.setString(1, registro.getNumeroTelefono());
            procedimiento.setString(2, registro.getDescripcionTelefono());
            procedimiento.setInt(3, registro.getCodigoProveedor());
            procedimiento.execute();
            listarTelefonoProveedor.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    } 
    
    public void generarReporte(){
        if(tipoDeOperaciones == TelefonoProveedorController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = TelefonoProveedorController.operaciones.NINGUNO;
        }else{
            if(tblTelefonoProveedores.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoTp= ((TelefonoProveedor) tblTelefonoProveedores.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor();
                parametros.put("_CodigoTelefonoProveedor", codigoTp);
                GenerarReporte.mostrarReporte("ReporteTelefonoProveedor.jasper", "Reporte Telefono Proveedor", parametros);
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
