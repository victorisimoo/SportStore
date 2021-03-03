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
import org.victorhernandez.bean.DetalleCompra;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Compra;
import org.victorhernandez.bean.Producto;
import org.victorhernandez.reportes.GenerarReporte;


public class DetalleCompraController implements Initializable {
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleCompra>listarDetalleCompra;
    private ObservableList<Compra>listarCompra;
    private ObservableList<Producto>listarProducto;
    @FXML private ComboBox cmbNDocumento;
    @FXML private TextField txtCostoP;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtCostoU;
    @FXML private ComboBox cmbCodProd;
    @FXML private ComboBox cmbDCompra;
    @FXML private TableView tblDetalleCompras;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDocumento;
    @FXML private TableColumn colCostoP;
    @FXML private TableColumn colCostoU;
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
        cmbDCompra.setItems(getDetalleCompras());  
        cmbCodProd.setItems(getProductos());
        cmbNDocumento.setItems(getCompras());
    }
    
    
    public void cargarDatos(){
        tblDetalleCompras.setItems(getDetalleCompras());
        colCodigo.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("CodigoDetalleCompra"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("NumeroDocumento"));    
        colCostoP.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("CostoProducto"));
        colCostoU.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("CostoUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("Cantidad"));
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("CodigoProducto"));
        colProducto.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("DescripcionProducto"));
    }
    
    public ObservableList<DetalleCompra> getDetalleCompras(){
        
        ArrayList<DetalleCompra> lista = new ArrayList<DetalleCompra>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarDetalleCompra}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
               lista.add(new DetalleCompra (resultado.getInt("CodigoDetalleCompra"), resultado.getInt("NumeroDocumento"), resultado.getDouble("CostoProducto"), resultado.getDouble("CostoUnitario"), resultado.getInt("Cantidad"), resultado.getInt("CodigoProducto"), resultado.getString("DescripcionProducto")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarDetalleCompra = FXCollections.observableArrayList(lista);
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
    
    public void seleccionarElementos(){
        if(tblDetalleCompras.getSelectionModel().getSelectedIndex() > -1){
            cmbDCompra.getSelectionModel().select(buscarDetalleCompra(((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
            cmbCodProd.getSelectionModel().select(buscarProducto(((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getCodigoProducto()));
            txtCostoP.setText(String.valueOf(((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getCostoProducto()));
            txtCantidad.setText(String.valueOf(((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getCantidad()));
            txtCostoU.setText(String.valueOf(((DetalleCompra)tblDetalleCompras.getSelectionModel().getSelectedItem()).getCostoUnitario()));
            cmbNDocumento.getSelectionModel().select(buscarCompra(((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        }
    }
   
    public DetalleCompra buscarDetalleCompra (int CodigoDetalleCompra){
        DetalleCompra resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarDetalleCompra(?)}");
            procedimiento.setInt(1, CodigoDetalleCompra);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new DetalleCompra (registro.getInt("CodigoDetalleCompra"), registro.getInt("NumeroDocumento"), registro.getDouble("CostoProducto"), registro.getDouble("CostoUnitario"), registro.getInt("Cantidad"), registro.getInt("CodigoProducto"), registro.getString("DescripcionProducto"));
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
    
    public void nuevo(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperaciones = DetalleCompraController.operaciones.GUARDAR;
            break;
            
            case GUARDAR:
                agregar();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = DetalleCompraController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void activarControles(){
        txtCostoP.setEditable(true);
        txtCantidad.setEditable(true);
        txtCostoU.setEditable(true);
        cmbNDocumento.setDisable(false);
        cmbCodProd.setDisable(false);
        cmbDCompra.setDisable(true);
    }
    
    public void desactivarControles(){
        txtCostoP.setEditable(false);
        txtCantidad.setEditable(false);
        txtCostoU.setEditable(false);
        cmbNDocumento.setDisable(false);
        cmbCodProd.setDisable(false);
        cmbDCompra.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCostoP.setText("");
        txtCantidad.setText("");
        txtCostoU.setText("");
        cmbNDocumento.setValue("");
        cmbCodProd.setValue("");
        cmbDCompra.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblDetalleCompras.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = DetalleCompraController.operaciones.ACTUALIZAR;
                    txtCostoP.setEditable(true);
                    txtCantidad.setEditable(true);
                    txtCostoU.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar un detallecompra!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtCostoP.setEditable(false);
                txtCantidad.setEditable(false);
                txtCostoU.setEditable(false);
                tipoDeOperaciones = DetalleCompraController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarDetalleCompra(?,?,?,?,?,?)}");
            DetalleCompra registro = (DetalleCompra)tblDetalleCompras.getSelectionModel().getSelectedItem();
            registro.setCodigoDetalleCompra(((DetalleCompra)cmbDCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
            registro.setNumeroDocumento(((Compra)cmbNDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());
            registro.setCostoProducto(Double.valueOf(txtCostoP.getText()));
            registro.setCostoUnitario(Double.valueOf(txtCostoU.getText()));
            registro.setCantidad(Integer.valueOf(txtCantidad.getText()));
            registro.setCodigoProducto(((Producto)cmbCodProd.getSelectionModel().getSelectedItem()).getCodigoProducto());
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setInt(2, registro.getNumeroDocumento());
            procedimiento.setDouble(3, registro.getCostoProducto());
            procedimiento.setDouble(4, registro.getCostoUnitario());
            procedimiento.setInt(5, registro.getCantidad());
            procedimiento.setInt(6, registro.getCodigoProducto());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == DetalleCompraController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = DetalleCompraController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = DetalleCompraController.operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblDetalleCompras.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar DetallCompra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarDetalleCompra(?)}");
                                procedimiento.setInt(1, ((DetalleCompra)tblDetalleCompras.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                                procedimiento.execute();
                                listarDetalleCompra.remove(tblDetalleCompras.getSelectionModel().getSelectedIndex());
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
        DetalleCompra registro = new DetalleCompra();
        registro.setNumeroDocumento(((Compra)cmbNDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());
        registro.setCostoProducto(Double.valueOf(txtCostoP.getText()));
        registro.setCantidad(Integer.valueOf(txtCantidad.getText()));
        registro.setCostoUnitario(Double.valueOf(txtCostoU.getText()));
        registro.setCodigoProducto(((Producto)cmbCodProd.getSelectionModel().getSelectedItem()).getCodigoProducto());
     
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarDetalleCompra(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setDouble(2, registro.getCostoProducto());
            procedimiento.setDouble(3, registro.getCostoUnitario());
            procedimiento.setInt(4, registro.getCantidad());
            procedimiento.setInt(5, registro.getCodigoProducto());
            procedimiento.execute();
            listarDetalleCompra.add(registro);        
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void generarReporte(){
        if(tipoDeOperaciones == DetalleCompraController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = DetalleCompraController.operaciones.NINGUNO;
        }else{
            if(tblDetalleCompras.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoTp= ((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento();
                parametros.put("_NumeroDocumento", codigoTp);
                GenerarReporte.mostrarReporte("DetalleCompra.jasper", "Reporte Detalle Compra", parametros);
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
