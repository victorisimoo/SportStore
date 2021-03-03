//@author: Victor Hernandez Meléndez.

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
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Marca;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import org.victorhernandez.reportes.GenerarReporte;

public class MarcasController implements Initializable {

    private enum operaciones {NUEVO, GUARDAR, EDITAR, ACTUALIZAR, CANCELAR, ELIMINAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Marca> listarMarca;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox cmbMarca;
    @FXML private TableView tblMarcas;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbMarca.setItems(getMarcas());
    }
    
    public void cargarDatos(){
        tblMarcas.setItems(getMarcas());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Marca, Integer>("CodigoMarca"));
        colDescripcion.setCellValueFactory((new PropertyValueFactory<Marca, String>("DescripcionMarca")));
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
    
    public void seleccionarElementos(){
        if(tblMarcas.getSelectionModel().getSelectedIndex() > -1){
            cmbMarca.getSelectionModel().select(buscarMarca(((Marca) tblMarcas.getSelectionModel().getSelectedItem()).getCodigoMarca()));
            txtDescripcion.setText(((Marca)tblMarcas.getSelectionModel().getSelectedItem()).getDescripcionMarca());
        }
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
    
    public void nuevo(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperaciones = MarcasController.operaciones.GUARDAR;
            break;
            
            case GUARDAR:
                agregar();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = MarcasController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void activarControles(){
        txtDescripcion.setEditable(true);
        cmbMarca.setDisable(true);
    }
    
    public void desactivarControles(){
        txtDescripcion.setEditable(false);
        cmbMarca.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        cmbMarca.setValue("");
    }
    
     public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblMarcas.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = MarcasController.operaciones.ACTUALIZAR;
                    txtDescripcion.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar una Marca!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtDescripcion.setEditable(false);
                tipoDeOperaciones = MarcasController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarMarca(?,?)}");
            Marca registro = (Marca)tblMarcas.getSelectionModel().getSelectedItem();
            registro.setDescripcionMarca(txtDescripcion.getText());
            registro.setCodigoMarca(((Marca)cmbMarca.getSelectionModel().getSelectedItem()).getCodigoMarca());
            procedimiento.setInt(1, registro.getCodigoMarca());
            procedimiento.setString(2, registro.getDescripcionMarca());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == MarcasController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = MarcasController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblMarcas.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar marca", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarMarca(?)}");
                                procedimiento.setInt(1, ((Marca)tblMarcas.getSelectionModel().getSelectedItem()).getCodigoMarca());
                                procedimiento.execute();
                                listarMarca.remove(tblMarcas.getSelectionModel().getSelectedIndex());
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
        Marca registro = new Marca();
        registro.setDescripcionMarca(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarMarcas(?)}");
            procedimiento.setString(1, registro.getDescripcionMarca());
            procedimiento.execute();
            listarMarca.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
     
    public void generarReporte(){
        if(tipoDeOperaciones == MarcasController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = MarcasController.operaciones.NINGUNO;
        }else{
            if(tblMarcas.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int codigoMarca= ((Marca) tblMarcas.getSelectionModel().getSelectedItem()).getCodigoMarca();
                parametros.put("_CodigoMarca", codigoMarca);
                GenerarReporte.mostrarReporte("ReporteMarcas.jasper", "Reporte de Marcas", parametros);
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
