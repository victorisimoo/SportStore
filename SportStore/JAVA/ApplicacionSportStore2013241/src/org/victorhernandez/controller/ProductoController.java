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
import org.victorhernandez.bean.Producto;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Categoria;
import org.victorhernandez.bean.Factura;
import org.victorhernandez.bean.Marca;
import org.victorhernandez.bean.Talla;
import org.victorhernandez.reportes.GenerarReporte;


public class ProductoController implements Initializable {
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Producto>listarProducto;
    private ObservableList<Categoria>listarCategoria;
    private ObservableList <Marca> listarMarca;
    private ObservableList <Talla> listarTalla;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtExistencia;
    @FXML private ComboBox cmbCategoria;
    @FXML private ComboBox cmbMarca;
    @FXML private ComboBox cmbTalla;
    @FXML private TextField txtImagen;
    @FXML private ComboBox cmbProducto;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colPUnitario;
    @FXML private TableColumn colPDocena;
    @FXML private TableColumn colPMayor;
    @FXML private TableColumn colExistencia;
    @FXML private TableColumn colCategoria;
    @FXML private TableColumn colMarca;
    @FXML private TableColumn colTalla;
    @FXML private TableColumn colImagen;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbProducto.setItems(getProductos());
        cmbCategoria.setItems(getCategorias());
        cmbMarca.setItems(getMarcas());
        cmbTalla.setItems(getTallas());
    }
    
    public void cargarDatos(){                                                                        
        tblProductos.setItems(getProductos());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("CodigoProducto"));
        colPUnitario.setCellValueFactory(new PropertyValueFactory<Producto, Double>("PrecioUnitario"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("DescripcionProducto"));    
        colPDocena.setCellValueFactory(new PropertyValueFactory<Producto, Double>("PrecioDocena"));
        colPMayor.setCellValueFactory(new PropertyValueFactory<Producto, Double>("PrecioPorMayor"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("ExistenciaProducto"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("CodigoCategoria"));
        colMarca.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("CodigoMarca"));
        colTalla.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("CodigoTalla"));
        colImagen.setCellValueFactory(new PropertyValueFactory<Producto, String>("Imagen"));
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
    
    public ObservableList<Categoria> getCategorias(){
        
        ArrayList<Categoria> lista = new ArrayList<Categoria>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCategorias}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Categoria(resultado.getInt("CodigoCategoria"), resultado.getString("DescripcionCategoria")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarCategoria = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList <Marca> getMarcas(){
        ArrayList<Marca> lista = new ArrayList<Marca>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarMarca}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Marca(resultado.getInt("CodigoMarca"), resultado.getString("DescripcionMarca")));
            }   
        } catch(SQLException e){
            e.printStackTrace();
        }
        return listarMarca = FXCollections.observableArrayList(lista);
    }
     
    public ObservableList <Talla> getTallas(){
        ArrayList <Talla> lista = new ArrayList();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTalla}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Talla(resultado.getInt("CodigoTalla"), resultado.getString("DescripcionTalla")));
            }  
            
        }catch(SQLException e){
            e.printStackTrace();
        }  
        return listarTalla = FXCollections.observableArrayList(lista);
    } 
    
    public void seleccionarElementos(){
        if(tblProductos.getSelectionModel().getSelectedIndex() > -1){
            cmbProducto.getSelectionModel().select(buscarProducto(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
            txtDescripcion.setText(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
            txtExistencia.setText(String.valueOf(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getExistenciaProducto()));
            cmbCategoria.getSelectionModel().select(buscarCategoria(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getCodigoCategoria()));
            cmbMarca.getSelectionModel().select(buscarMarca(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getCodigoMarca()));
            cmbTalla.getSelectionModel().select(buscarTalla(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getCodigoTalla()));
            txtImagen.setText(String.valueOf(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getImagen()));
        }
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

    public Categoria buscarCategoria (int CodigoCategoria){
        Categoria resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarCategoria(?)}");
            procedimiento.setInt(1, CodigoCategoria);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Categoria (registro.getInt("CodigoCategoria"), registro.getString("DescripcionCategoria"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
     
    public Marca buscarMarca (int CodigoMarca){
        Marca  resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarMarca(?)}");
            procedimiento.setInt(1, CodigoMarca);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Marca(registro.getInt("CodigoMarca"), registro.getString("DescripcionMarca"));
            }      
        } catch(SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Talla buscarTalla (int CodigoTalla){
        Talla resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarTalla(?)}");
            procedimiento.setInt(1, CodigoTalla);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Talla (registro.getInt("CodigoTalla"), registro.getString("DescripcionTalla"));
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
                tipoDeOperaciones = ProductoController.operaciones.GUARDAR;
            break;
            
            case GUARDAR:
                agregar();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = ProductoController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void activarControles(){
        txtDescripcion.setEditable(true);
        txtExistencia.setEditable(true);
        cmbCategoria.setEditable(false);
        cmbMarca.setDisable(false);
        cmbTalla.setDisable(false);
        txtImagen.setEditable(true);
        cmbProducto.setDisable(true);
    }
    
    public void desactivarControles(){
        txtDescripcion.setEditable(false);
        txtExistencia.setEditable(false);
        cmbCategoria.setEditable(false);
        cmbMarca.setEditable(false);
        cmbTalla.setEditable(false);
        txtImagen.setEditable(false);
        cmbProducto.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        txtExistencia.setText("");
        cmbCategoria.setValue("");
        cmbMarca.setValue("");
        cmbTalla.setValue("");
        txtImagen.setText("");
        cmbProducto.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblProductos.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = ProductoController.operaciones.ACTUALIZAR;
                    txtDescripcion.setEditable(true);
                    txtExistencia.setEditable(true);
                    txtImagen.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar un producto!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtDescripcion.setEditable(false);
                txtExistencia.setEditable(false);
                txtImagen.setEditable(false);
                tipoDeOperaciones = ProductoController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{ 
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarProducto(?,?,?,?,?,?,?)}");
            Producto registro = (Producto)tblProductos.getSelectionModel().getSelectedItem();
            registro.setCodigoProducto(((Producto)cmbProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setDescripcionProducto(txtDescripcion.getText());
            registro.setExistenciaProducto(Integer.valueOf(txtExistencia.getText()));
            registro.setCodigoCategoria(((Categoria)cmbCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria());
            registro.setCodigoMarca(((Marca)cmbMarca.getSelectionModel().getSelectedItem()).getCodigoMarca());
            registro.setCodigoTalla(((Talla)cmbTalla.getSelectionModel().getSelectedItem()).getCodigoTalla());
            registro.setImagen(txtImagen.getText());
            procedimiento.setInt(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setInt(3, registro.getExistenciaProducto());
            procedimiento.setInt(4, registro.getCodigoCategoria());
            procedimiento.setInt(5, registro.getCodigoMarca());
            procedimiento.setInt(6, registro.getCodigoTalla());
            procedimiento.setString(7, registro.getImagen());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
  
    public void eliminar(){
        if(tipoDeOperaciones == ProductoController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = ProductoController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = ProductoController.operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblProductos.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarProducto(?)}");
                                procedimiento.setInt(1, ((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                                procedimiento.execute();
                                listarProducto.remove(tblProductos.getSelectionModel().getSelectedIndex());
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
        Producto registro = new Producto();
        registro.setDescripcionProducto(txtDescripcion.getText());
        registro.setExistenciaProducto(Integer.valueOf(txtExistencia.getText()));
        registro.setCodigoCategoria(((Categoria)cmbCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria());
        registro.setCodigoMarca(((Marca)cmbMarca.getSelectionModel().getSelectedItem()).getCodigoMarca());
        registro.setCodigoTalla(((Talla)cmbTalla.getSelectionModel().getSelectedItem()).getCodigoTalla());
        registro.setImagen(txtImagen.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarProducto(?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getDescripcionProducto());
            procedimiento.setInt(2, registro.getExistenciaProducto());
            procedimiento.setInt(3, registro.getCodigoCategoria());
            procedimiento.setInt(4, registro.getCodigoMarca());
            procedimiento.setInt(5, registro.getCodigoTalla());
            procedimiento.setString(6, registro.getImagen());
            procedimiento.execute();
            listarProducto.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }  
    
    public void generarReporte(){
        if(tipoDeOperaciones == ProductoController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Generar inventario");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = ProductoController.operaciones.NINGUNO;
        }else{
            Map parametros = new HashMap();
            parametros.put("_CodigoProducto", null);
            GenerarReporte.mostrarReporte("ReporteProductos.jasper", "Reporte de Productos", parametros);
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