//@author: Victor Noé Hernández Meléndez.
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
import org.victorhernandez.bean.Marca;
import org.victorhernandez.bean.Proveedor;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import org.victorhernandez.reportes.GenerarReporte;

public class ProveedorController implements Initializable {
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Proveedor>listarProveedor;
    @FXML private TextField txtRazonSocial;
    @FXML private TextField txtNit;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtPaginaWeb;
    @FXML private TextField txtContactoP;
    @FXML private ComboBox cmbProveedor;
    @FXML private TableView tblProveedores;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colRazonSocial;
    @FXML private TableColumn colNit;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colPaginaW;
    @FXML private TableColumn colContacto;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private Button btnAgregarMedio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbProveedor.setItems(getProveedores());
    }
    
    public void cargarDatos(){
        tblProveedores.setItems(getProveedores());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Proveedor, Integer>("CodigoProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("RazonSocial"));
        colNit.setCellValueFactory(new  PropertyValueFactory<Proveedor, String>("NitProveedor"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("DireccionProveedor"));
        colPaginaW.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("PaginaWeb"));
        colContacto.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("ContactoPrincipal"));
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
        if(tblProveedores.getSelectionModel().getSelectedIndex() > -1){
            cmbProveedor.getSelectionModel().select(buscarProveedor(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtRazonSocial.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
            txtNit.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getNitProveedor());
            txtDireccion.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
            txtPaginaWeb.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
            txtContactoP.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        }
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
        txtRazonSocial.setEditable(true);
        txtNit.setEditable(true);
        txtDireccion.setEditable(true);
        txtPaginaWeb.setEditable(true);
        txtContactoP.setEditable(true);
        cmbProveedor.setDisable(true);
    }
    
    public void desactivarControles(){
        txtRazonSocial.setEditable(false);
        txtNit.setEditable(false);
        txtDireccion.setEditable(false);
        txtPaginaWeb.setEditable(false);
        txtContactoP.setEditable(false);
        cmbProveedor.setDisable(false);
    }
    
    public void limpiarControles(){
        txtRazonSocial.setText("");
        txtNit.setText("");
        txtDireccion.setText("");
        txtPaginaWeb.setText("");
        txtContactoP.setText("");
        cmbProveedor.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    txtRazonSocial.setEditable(true);
                    txtNit.setEditable(true);
                    txtDireccion.setEditable(true);
                    txtPaginaWeb.setEditable(true);
                    txtContactoP.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar un Proveedor!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtRazonSocial.setEditable(false);
                txtNit.setEditable(false);
                txtDireccion.setEditable(false);
                txtPaginaWeb.setEditable(false);
                txtContactoP.setEditable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarProveedor(?,?,?,?,?,?)}");
            Proveedor registro = (Proveedor)tblProveedores.getSelectionModel().getSelectedItem();
            registro.setRazonSocial(txtRazonSocial.getText());
            registro.setNitProveedor(txtNit.getText());
            registro.setDireccionProveedor(txtDireccion.getText());
            registro.setPaginaWeb(txtPaginaWeb.getText());
            registro.setContactoPrincipal(txtContactoP.getText());
            registro.setCodigoProveedor(((Proveedor)cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getRazonSocial());
            procedimiento.setString(3, registro.getNitProveedor());
            procedimiento.setString(4, registro.getDireccionProveedor());
            procedimiento.setString(5, registro.getPaginaWeb());
            procedimiento.setString(6, registro.getContactoPrincipal());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == ProveedorController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = ProveedorController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                                procedimiento.setInt(1, ((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                                procedimiento.execute();
                                listarProveedor.remove(tblProveedores.getSelectionModel().getSelectedIndex());
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
        Proveedor registro = new Proveedor();
        registro.setRazonSocial(txtRazonSocial.getText());
        registro.setNitProveedor(txtNit.getText());
        registro.setDireccionProveedor(txtDireccion.getText());
        registro.setPaginaWeb(txtPaginaWeb.getText());
        registro.setContactoPrincipal(txtContactoP.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarProveedor(?,?,?,?,?)}");
            procedimiento.setString(1, registro.getRazonSocial());
            procedimiento.setString(2, registro.getNitProveedor());
            procedimiento.setString(3, registro.getDireccionProveedor());
            procedimiento.setString(4, registro.getPaginaWeb());
            procedimiento.setString(5, registro.getContactoPrincipal());
            procedimiento.execute();
            listarProveedor.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void generarReporte(){
        if(tipoDeOperaciones == ProveedorController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = ProveedorController.operaciones.NINGUNO;
        }else{
            if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoP= ((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor();
                parametros.put("_CodigoProveedor", codigoP);
                GenerarReporte.mostrarReporte("ReporteProveedores.jasper", "Reporte Proveedores", parametros);
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
    public void ventanaEmailProveedores(){
        escenarioPrincipal.ventanaEmailProveedores();
    }
    
    public void ventanaTelefonoProveedores(){
        escenarioPrincipal.ventanaTelefonoProveedores();
    }
    
}
