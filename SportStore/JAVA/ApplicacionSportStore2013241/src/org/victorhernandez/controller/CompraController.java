//@author: Victor Hernández Meléndez.
package org.victorhernandez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory; 
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import org.victorhernandez.bean.Compra;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Proveedor;
import java.util.HashMap;
import java.util.Map;
import org.victorhernandez.reportes.GenerarReporte;

public class CompraController implements Initializable {
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Compra>listarCompra;
    private ObservableList<Proveedor>listarProveedor;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtTotal;
    @FXML private DatePicker dateFecha;
    @FXML private ComboBox cmbProveedor;
    @FXML private ComboBox cmbNumeroDocumento;
    @FXML private TableView tblCompras;
    @FXML private TableColumn colNumeroDocumento;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colCodigoProveedor;
    @FXML private TableColumn colRazonSocial;
    @FXML private TableColumn colTotal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbNumeroDocumento.setItems(getCompras());
        cmbProveedor.setItems(getProveedores());
    }
    
    public void cargarDatos(){
        tblCompras.setItems(getCompras());
        colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("NumeroDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Compra, String>("DescripcionCompra"));    
        colFecha.setCellValueFactory(new PropertyValueFactory<Compra, String>("FechaCompra"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("CodigoProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<Compra, String>("RazonSocial"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Compra, Double>("TotalCompra"));
        
    }
    
    public ObservableList<Compra> getCompras(){
        
        ArrayList<Compra> lista = new ArrayList<Compra>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCompra}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
               lista.add(new Compra (resultado.getInt("NumeroDocumento"), resultado.getString("DescripcionCompra"), resultado.getString("FechaCompra"), resultado.getInt("CodigoProveedor"), resultado.getString("RazonSocial"), resultado.getDouble("TotalCompra")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarCompra = FXCollections.observableArrayList(lista);
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
        if(tblCompras.getSelectionModel().getSelectedIndex() > -1){
            cmbNumeroDocumento.getSelectionModel().select(buscarCompra(((Compra) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
            txtDescripcion.setText(((Compra)tblCompras.getSelectionModel().getSelectedItem()).getDescripcionCompra());
            txtTotal.setText(String.valueOf(((Compra)tblCompras.getSelectionModel().getSelectedItem()).getTotalCompra()));
            dateFecha.setValue(LocalDate.parse(((Compra) tblCompras.getSelectionModel().getSelectedItem()).getFechaCompra()));
            cmbProveedor.getSelectionModel().select(buscarProveedor(((Compra) tblCompras.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        }
    }
   
    public Compra buscarCompra (int NumeroDocumento){
        Compra resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarCompra(?)}");
            procedimiento.setInt(1, NumeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Compra (registro.getInt("NumeroDocumento"), registro.getString("DescripcionCompra"), registro.getString("FechaCompra"), registro.getInt("CodigoProveedor"), registro.getString("RazonSocial"), registro.getDouble("TotalCompra"));
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
                tipoDeOperaciones = CompraController.operaciones.GUARDAR;
            break;
            
            case GUARDAR:
                agregar();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = CompraController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void activarControles(){
        txtDescripcion.setEditable(true);
        txtTotal.setEditable(true);
        dateFecha.setDisable(false);
        cmbProveedor.setDisable(false);
        cmbNumeroDocumento.setDisable(true);
    }
    
    public void desactivarControles(){
        txtDescripcion.setEditable(false);
        txtTotal.setEditable(false);
        dateFecha.setDisable(true);
        cmbProveedor.setDisable(false);
        cmbNumeroDocumento.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        dateFecha.setValue(null);
        txtTotal.setText("");
        cmbProveedor.setValue(""); 
        cmbNumeroDocumento.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblCompras.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = CompraController.operaciones.ACTUALIZAR;
                    txtDescripcion.setEditable(true);
                    dateFecha.setDisable(false);
                    txtTotal.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar una compra!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtDescripcion.setEditable(false);
                txtTotal.setEditable(false);
                dateFecha.setDisable(false);
                tipoDeOperaciones = CompraController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarCompra(?,?,?,?,?)}");
            Compra registro = (Compra)tblCompras.getSelectionModel().getSelectedItem();
            registro.setNumeroDocumento(((Compra)cmbNumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());
            registro.setDescripcionCompra(txtDescripcion.getText());
            registro.setFechaCompra(String.valueOf(dateFecha.getValue()));
            registro.setCodigoProveedor(((Proveedor)cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setTotalCompra(Double.valueOf(txtTotal.getText()));
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getDescripcionCompra());
            procedimiento.setString(3, registro.getFechaCompra());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.setDouble(5, registro.getTotalCompra());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
  
    public void eliminar(){
        if(tipoDeOperaciones == CompraController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = CompraController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = CompraController.operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblCompras.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarCompra(?)}");
                                procedimiento.setInt(1, ((Compra)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                                procedimiento.execute();
                                listarCompra.remove(tblCompras.getSelectionModel().getSelectedIndex());
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
        Compra registro = new Compra();
        registro.setDescripcionCompra(txtDescripcion.getText());
        registro.setFechaCompra(String.valueOf(dateFecha.getValue()));
        registro.setCodigoProveedor(((Proveedor)cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());  
        registro.setTotalCompra(Double.valueOf(txtTotal.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarCompra(?,?,?,?)}");
            procedimiento.setString(1, registro.getDescripcionCompra());
            procedimiento.setString(2, registro.getFechaCompra());
            procedimiento.setInt(3, registro.getCodigoProveedor());
            procedimiento.setDouble(4, registro.getTotalCompra());
            procedimiento.execute();
            listarCompra.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }  
    
    public void generarReporte(){
        if(tblCompras.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null, "¡Debe seleccionar un registro para generar registro!");
        }else{
            Map parametros = new HashMap();
            int numeroDocumento = ((Compra) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento();
            parametros.put("_NumeroDocumento", numeroDocumento);
            GenerarReporte.mostrarReporte("ReporteCompra.jasper", "Reporte de Compras", parametros);
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
    
    public void ventanaDetalleCompra(){
        escenarioPrincipal.ventanaDetalleCompra();
    }
}
