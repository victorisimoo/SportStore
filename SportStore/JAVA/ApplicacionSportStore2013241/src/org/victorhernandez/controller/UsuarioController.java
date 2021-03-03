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
import javafx.scene.control.PasswordField;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Usuario;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import org.victorhernandez.reportes.GenerarReporte;

public class UsuarioController implements Initializable {
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Usuario>listarUsuario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtTipo;
    @FXML private PasswordField txtContrasena;
    @FXML private ComboBox cmbCodigo;
    @FXML private TableView tblUsuarios;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colEmail;
    @FXML private TableColumn colUsuario;
    @FXML private TableColumn colTipo;
    @FXML private TableColumn colContrasena;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigo.setItems(getUsuarios());   
    }
    
    public void cargarDatos(){
        tblUsuarios.setItems(getUsuarios());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("CodigoUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("NombreUsuario"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Usuario"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Usuario, String>("EmailUsuario"));      
        colTipo.setCellValueFactory(new PropertyValueFactory<Usuario, String>("TipoUsuario"));
        colContrasena.setCellValueFactory(new PropertyValueFactory<Usuario, String>("ContrasenaUsuario"));
    }
    
    public ObservableList<Usuario> getUsuarios(){ 
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarUsuario}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Usuario(resultado.getInt("CodigoUsuario"), resultado.getString("NombreUsuario"), resultado.getString("Usuario"), resultado.getString("EmailUsuario"), resultado.getString("TipoUsuario"), resultado.getString("ContrasenaUsuario")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarUsuario = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElementos(){
        if(tblUsuarios.getSelectionModel().getSelectedIndex() > -1){
            cmbCodigo.getSelectionModel().select(buscarUsuario(((Usuario) tblUsuarios.getSelectionModel().getSelectedItem()).getCodigoUsuario()));
            txtNombre.setText(((Usuario)tblUsuarios.getSelectionModel().getSelectedItem()).getNombreUsuario());
            txtUsuario.setText(((Usuario)tblUsuarios.getSelectionModel().getSelectedItem()).getUsuario());
            txtEmail.setText(((Usuario)tblUsuarios.getSelectionModel().getSelectedItem()).getEmailUsuario());
            txtTipo.setText(((Usuario)tblUsuarios.getSelectionModel().getSelectedItem()).getTipoUsuario());
            txtContrasena.setText(((Usuario)tblUsuarios.getSelectionModel().getSelectedItem()).getContrasenaUsuario());
        }
    }
    
    public Usuario buscarUsuario (int CodigoUsuario){
        Usuario resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarUsuario(?)}");
            procedimiento.setInt(1, CodigoUsuario);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Usuario(registro.getInt("CodigoUsuario"), registro.getString("NombreUsuario"), registro.getString("Usuario"), registro.getString("EmailUsuario"), registro.getString("TipoUsuario"), registro.getString("ContrasenaUsuario"));
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
                tipoDeOperaciones = UsuarioController.operaciones.GUARDAR;
            break;
            
            case GUARDAR:
                agregar();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = UsuarioController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
     
    public void activarControles(){
        txtNombre.setEditable(true);
        txtUsuario.setEditable(true);
        txtEmail.setEditable(true);
        txtTipo.setEditable(true);
        txtContrasena.setEditable(true);
        cmbCodigo.setDisable(true);
    }
    
    public void desactivarControles(){
        txtNombre.setEditable(false);
        txtUsuario.setEditable(false);
        txtEmail.setEditable(false);
        txtTipo.setEditable(false);
        txtContrasena.setEditable(false);
        cmbCodigo.setDisable(false);
    }
    
    public void limpiarControles(){
        txtNombre.setText("");
        txtUsuario.setText("");
        txtEmail.setText("");
        txtTipo.setText("");
        txtContrasena.setText("");
        cmbCodigo.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblUsuarios.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = UsuarioController.operaciones.ACTUALIZAR;
                    txtNombre.setEditable(true);
                    txtUsuario.setEditable(true);
                    txtEmail.setEditable(true);
                    txtTipo.setEditable(true);
                    txtContrasena.setEditable(true);

                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar un Usuario!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtNombre.setEditable(false);
                txtUsuario.setEditable(false);
                txtEmail.setEditable(false);
                txtTipo.setEditable(false);
                txtContrasena.setEditable(false);
                tipoDeOperaciones = UsuarioController.operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarUsuario(?,?,?,?,?,?)}");
            Usuario registro = (Usuario)tblUsuarios.getSelectionModel().getSelectedItem();
            registro.setCodigoUsuario(((Usuario)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoUsuario());
            registro.setNombreUsuario(txtNombre.getText());
            registro.setUsuario(txtUsuario.getText());
            registro.setEmailUsuario(txtEmail.getText());
            registro.setTipoUsuario(txtTipo.getText());
            registro.setContrasenaUsuario((txtContrasena.getText()));
            procedimiento.setInt(1, registro.getCodigoUsuario());
            procedimiento.setString(2, registro.getNombreUsuario());
            procedimiento.setString(3, registro.getUsuario());
            procedimiento.setString(4, registro.getEmailUsuario());
            procedimiento.setString(5, registro.getTipoUsuario());
            procedimiento.setString(6, registro.getContrasenaUsuario());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        if(tipoDeOperaciones == UsuarioController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = UsuarioController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = UsuarioController.operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblUsuarios.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar al usuario?", "Eliminar usuario cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarUsuario (?)}");
                                procedimiento.setInt(1, ((Usuario)tblUsuarios.getSelectionModel().getSelectedItem()).getCodigoUsuario());
                                procedimiento.execute();
                                listarUsuario.remove(tblUsuarios.getSelectionModel().getSelectedIndex());
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
        Usuario registro = new Usuario();
        registro.setNombreUsuario(txtNombre.getText());
        registro.setUsuario(txtUsuario.getText());
        registro.setEmailUsuario(txtEmail.getText());
        registro.setTipoUsuario(txtTipo.getText());
        registro.setContrasenaUsuario((txtContrasena.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarUsuario(?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNombreUsuario());
            procedimiento.setString(2, registro.getUsuario());
            procedimiento.setString(3, registro.getEmailUsuario());
            procedimiento.setString(4, registro.getTipoUsuario());
            procedimiento.setString(5, registro.getContrasenaUsuario());
            procedimiento.execute();
            listarUsuario.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }  
    
    public void generarReporte(){
        if(tipoDeOperaciones == UsuarioController.operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = UsuarioController.operaciones.NINGUNO;
        }else{
            Map parametros = new HashMap();
            parametros.put("_CodigoUsuario", null);
            GenerarReporte.mostrarReporte("ReporteUsuarios.jasper", "Reporte de Usuarios", parametros);
        }
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaUsuarios(){
        escenarioPrincipal.ventanaNueva();
    }  
    
    
    
}
