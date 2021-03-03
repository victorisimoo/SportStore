//@author: Victor Noé Hernández Meléndez

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
import org.victorhernandez.bean.DetalleFactura;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Factura;
import org.victorhernandez.bean.Producto;
import org.victorhernandez.reportes.GenerarReporte;

public class DetalleFacturaController implements Initializable {
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleFactura>listarDetalleFactura;
    private ObservableList<Factura>listarFactura;
    private ObservableList<Producto>listarProducto;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox cmbNFactura;
    @FXML private ComboBox cmbCProducto;
    @FXML private ComboBox cmbCodigoDetalle;
    @FXML private TableView tblDetalleFacturas;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colPrecio;
    @FXML private TableColumn colNFactura;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colProducto;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
       cmbCodigoDetalle.setItems(getDetalleFacturas()); 
       cmbNFactura.setItems(getFacturas());
       cmbCProducto.setItems(getProductos());
    }
    
    
    public void cargarDatos(){
        tblDetalleFacturas.setItems(getDetalleFacturas());
        colCodigo.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("CodigoDetalleFactura"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("Precio"));        
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("Cantidad"));
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("CodigoProducto"));
        colNFactura.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("NumeroFactura"));
        colProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("DescripcionProducto"));
    }
    
    public ObservableList<DetalleFactura> getDetalleFacturas(){
        
        ArrayList<DetalleFactura> lista = new ArrayList<DetalleFactura>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarDetalleFactura}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
               lista.add(new DetalleFactura(resultado.getInt("CodigoDetalleFactura"), resultado.getDouble("Precio"), resultado.getInt("Cantidad"), resultado.getInt("CodigoProducto"), resultado.getInt("NumeroFactura"), resultado.getString("DescripcionProducto")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarDetalleFactura = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Producto> getProductos(){
        
        ArrayList<Producto> lista = new ArrayList<Producto>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarProducto}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
               lista.add(new Producto(resultado.getInt("CodigoProducto"), resultado.getDouble("PrecioUnitario"), resultado.getString("DescripcionProducto"), resultado.getDouble("PrecioDocena"), resultado.getDouble("PrecioPorMayor"), resultado.getInt("ExistenciaProducto"), resultado.getInt("CodigoCategoria"), resultado.getInt("CodigoMarca"), resultado.getInt("CodigoTalla"), resultado.getString("Imagen")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarProducto = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Factura> getFacturas(){
        
        ArrayList<Factura> lista = new ArrayList<Factura>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarFactura}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
               lista.add(new Factura(resultado.getInt("NumeroFactura"), resultado.getString("FechaFactura"), resultado.getString("NitFactura"), resultado.getString("EstadoFactura"), resultado.getInt("CodigoCliente"), resultado.getString("NombreCliente"), resultado.getDouble("TotalFactura")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarFactura = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElementos(){
        if(tblDetalleFacturas.getSelectionModel().getSelectedIndex() > -1){
            cmbCodigoDetalle.getSelectionModel().select(buscarDetalleFactura(((DetalleFactura) tblDetalleFacturas.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura()));
            txtCantidad.setText(String.valueOf(((DetalleFactura) tblDetalleFacturas.getSelectionModel().getSelectedItem()).getCantidad()));
            cmbCProducto.getSelectionModel().select(buscarProducto(((DetalleFactura) tblDetalleFacturas.getSelectionModel().getSelectedItem()).getCodigoProducto()));
            cmbNFactura.getSelectionModel().select(buscarFactura(((DetalleFactura) tblDetalleFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        }
    }
    
    public DetalleFactura buscarDetalleFactura (int CodigoDetalleFactura){
        DetalleFactura resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarDetalleFactura(?)}");
            procedimiento.setInt(1, CodigoDetalleFactura);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new DetalleFactura (registro.getInt("CodigoDetalleFactura"), registro.getDouble("Precio"), registro.getInt("Cantidad"), registro.getInt("CodigoProducto"), registro.getInt("NumeroFactura"), registro.getString("DescripcionProducto"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Producto buscarProducto (int CodigoProducto){
        Producto resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarProducto(?)}");
            procedimiento.setInt(1, CodigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Producto (registro.getInt("CodigoProducto"), registro.getDouble("PrecioUnitario"), registro.getString("DescripcionProducto"), registro.getDouble("PrecioDocena"), registro.getDouble("PrecioPorMayor"), registro.getInt("ExistenciaProducto"), registro.getInt("CodigoCategoria"), registro.getInt("CodigoMarca"), registro.getInt("CodigoTalla"), registro.getString("Imagen"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Factura buscarFactura (int NumeroFactura){
        Factura resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarFactura(?)}");
            procedimiento.setInt(1, NumeroFactura);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Factura (registro.getInt("NumeroFactura"), registro.getString("FechaFactura"), registro.getString("NitFactura"), registro.getString("EstadoFactura"), registro.getInt("CodigoCliente"), registro.getString("NombreCliente"), registro.getDouble("TotalFactura"));
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
        txtCantidad.setEditable(true);
        cmbCProducto.setDisable(false);
        cmbNFactura.setDisable(false);
        cmbCodigoDetalle.setDisable(true);
    }
    
    public void desactivarControles(){
        txtCantidad.setEditable(false);
        cmbCProducto.setDisable(false);
        cmbNFactura.setDisable(false);
        cmbCodigoDetalle.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCantidad.setText("");
        cmbCProducto.setValue("");
        cmbNFactura.setValue("");
        cmbCodigoDetalle.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblDetalleFacturas.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    txtCantidad.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar un DetalleFactura!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtCantidad.setEditable(false); 
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarDetalleFactura(?,?,?,?)}");
            DetalleFactura registro = (DetalleFactura)tblDetalleFacturas.getSelectionModel().getSelectedItem();
            registro.setCodigoDetalleFactura(((DetalleFactura)cmbCodigoDetalle.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura());
            registro.setCantidad(Integer.valueOf(txtCantidad.getText()));
            registro.setCodigoProducto(((Producto)cmbCProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setNumeroFactura(((Factura)cmbNFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.setInt(3, registro.getCodigoProducto());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == DetalleFacturaController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = DetalleFacturaController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblDetalleFacturas.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar DetalleFactura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarDetalleFactura (?)}");
                                procedimiento.setInt(1, ((DetalleFactura)tblDetalleFacturas.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura());
                                procedimiento.execute();
                                listarDetalleFactura.remove(tblDetalleFacturas.getSelectionModel().getSelectedIndex());
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
        DetalleFactura registro = new DetalleFactura();
        registro.setCantidad(Integer.valueOf(txtCantidad.getText()));
        registro.setCodigoProducto(((Producto)cmbCProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroFactura(((Factura)cmbNFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarDetalleFactura(?, ?, ?)}");
            procedimiento.setInt(1, registro.getCantidad());
            procedimiento.setInt(2, registro.getCodigoProducto());
            procedimiento.setInt(3, registro.getNumeroFactura());
            try{
                procedimiento.execute();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "¡No contamos con esa cantidad en existencia!");
            }
            listarDetalleFactura.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }  
    
    public void generarReporte(){
        if(tipoDeOperaciones == DetalleFacturaController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = DetalleFacturaController.operaciones.NINGUNO;
        }else{
            if(tblDetalleFacturas.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoTp= ((DetalleFactura) tblDetalleFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura();
                parametros.put("_NumeroFactura", codigoTp);
                GenerarReporte.mostrarReporte("ReporteDetalleFactura.jasper", "Reporte Detalle Factura", parametros);
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
