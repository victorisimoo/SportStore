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
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Categoria;
import org.victorhernandez.bean.Factura;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import org.victorhernandez.reportes.GenerarReporte;

public class CategoriaController implements Initializable{
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Categoria>listarCategoria;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox cmbCategoria;
    @FXML private TableView tblCategorias;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCategoria.setItems(getCategorias());     
    }
   
    public void cargarDatos(){
        tblCategorias.setItems(getCategorias());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Categoria, Integer>("CodigoCategoria"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Categoria, String>("DescripcionCategoria"));
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
    
    public void seleccionarElementos(){
        if(tblCategorias.getSelectionModel().getSelectedIndex() > -1){
            cmbCategoria.getSelectionModel().select(buscarCategoria(((Categoria) tblCategorias.getSelectionModel().getSelectedItem()).getCodigoCategoria()));
            txtDescripcion.setText(((Categoria)tblCategorias.getSelectionModel().getSelectedItem()).getDescripcionCategoria());
        }
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
        cmbCategoria.setDisable(true);
    }
    
    public void desactivarControles(){
        txtDescripcion.setEditable(false);
        cmbCategoria.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        cmbCategoria.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblCategorias.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    txtDescripcion.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar una Categoria!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtDescripcion.setEditable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarCategoria(?,?)}");
            Categoria registro = (Categoria)tblCategorias.getSelectionModel().getSelectedItem();
            registro.setDescripcionCategoria(txtDescripcion.getText());
            registro.setCodigoCategoria(((Categoria)cmbCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria());
            procedimiento.setInt(1, registro.getCodigoCategoria());
            procedimiento.setString(2, registro.getDescripcionCategoria());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblCategorias.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar categoria", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarCategoria (?)}");
                                procedimiento.setInt(1, ((Categoria)tblCategorias.getSelectionModel().getSelectedItem()).getCodigoCategoria());
                                procedimiento.execute();
                                listarCategoria.remove(tblCategorias.getSelectionModel().getSelectedIndex());
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
        Categoria registro = new Categoria();
        registro.setDescripcionCategoria(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarCategoria(?)}");
            procedimiento.setString(1, registro.getDescripcionCategoria());
            procedimiento.execute();
            listarCategoria.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void generarReporte(){
        if(tipoDeOperaciones == CategoriaController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = CategoriaController.operaciones.NINGUNO;
        }else{
            if(tblCategorias.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoCategoria = ((Categoria) tblCategorias.getSelectionModel().getSelectedItem()).getCodigoCategoria();
                parametros.put("_CodigoCategoria", codigoCategoria);
                GenerarReporte.mostrarReporte("ReporteCategoria.jasper", "Reporte de Categorias", parametros);
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
