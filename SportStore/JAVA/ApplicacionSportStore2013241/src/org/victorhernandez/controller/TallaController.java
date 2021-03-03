//@author: Victor Hernandez Meléndez

package org.victorhernandez.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
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
import org.victorhernandez.bean.Talla;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Proveedor;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import org.victorhernandez.reportes.GenerarReporte;

public class TallaController implements Initializable {
    private enum operaciones {NUEVO, GUARDAR, EDITAR, ACTUALIZAR, CANCELAR, ELIMINAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Talla> listarTalla;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox cmbTalla;
    @FXML private TableView tblTallas;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
       cmbTalla.setItems(getTallas());
    }
    
    public void cargarDatos(){
        tblTallas.setItems(getTallas());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Talla, Integer>("CodigoTalla"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Talla, String>("DescripcionTalla"));
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
        if(tblTallas.getSelectionModel().getSelectedIndex() > -1){
            cmbTalla.getSelectionModel().select(buscarTalla(((Talla) tblTallas.getSelectionModel().getSelectedItem()).getCodigoTalla()));
            txtDescripcion.setText(((Talla)tblTallas.getSelectionModel().getSelectedItem()).getDescripcionTalla());
        }
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
        cmbTalla.setDisable(true);
    }
    
    public void desactivarControles(){
        txtDescripcion.setEditable(false);
        cmbTalla.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        cmbTalla.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblTallas.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    txtDescripcion.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar una Talla!");
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarTalla(?,?)}");
            Talla registro = (Talla)tblTallas.getSelectionModel().getSelectedItem();
            registro.setDescripcionTalla(txtDescripcion.getText());
            registro.setCodigoTalla(((Talla)cmbTalla.getSelectionModel().getSelectedItem()).getCodigoTalla());
            procedimiento.setInt(1, registro.getCodigoTalla());
            procedimiento.setString(2, registro.getDescripcionTalla());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == TallaController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = TallaController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblTallas.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar talla", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarTalla(?)}");
                                procedimiento.setInt(1, ((Talla)tblTallas.getSelectionModel().getSelectedItem()).getCodigoTalla());
                                procedimiento.execute();
                                listarTalla.remove(tblTallas.getSelectionModel().getSelectedIndex());
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
        Talla registro = new Talla();
        registro.setDescripcionTalla(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarTalla(?)}");
            procedimiento.setString(1, registro.getDescripcionTalla());
            procedimiento.execute();
            listarTalla.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void generarReporte(){
        if(tipoDeOperaciones == TallaController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = TallaController.operaciones.NINGUNO;
        }else{
            if(tblTallas.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoT= ((Talla) tblTallas.getSelectionModel().getSelectedItem()).getCodigoTalla();
                parametros.put("_CodigoTallas", codigoT);
                GenerarReporte.mostrarReporte("ReporteTalla.jasper", "Reporte Tallas", parametros);
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
