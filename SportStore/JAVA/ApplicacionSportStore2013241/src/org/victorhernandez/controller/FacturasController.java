//@author: Victor Noé Hernández Meléndez

package org.victorhernandez.controller;

import javafx.scene.control.DatePicker;
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
import javafx.scene.control.cell.PropertyValueFactory; 
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import org.victorhernandez.bean.Factura;
import org.victorhernandez.system.Principal;
import org.victorhernandez.db.Conexion;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.victorhernandez.bean.Cliente;
import org.victorhernandez.reportes.GenerarReporte;


public class FacturasController implements Initializable {
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Factura>listarFactura;
    private ObservableList<Cliente>listarCliente;
    @FXML private DatePicker dateFecha;
    @FXML private TextField txtNit;
    @FXML private TextField txtTotal;
    @FXML private TextField txtEstado;
    @FXML private ComboBox cmbCliente;
    @FXML private ComboBox cmbFactura;
    @FXML private TableView tblFacturas;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colNit;
    @FXML private TableColumn colEstado;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colNombreCliente;
    @FXML private TableColumn colTotal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbFactura.setItems(getFacturas());
        cmbCliente.setItems(getClientes());
    }
    
    public void cargarDatos(){
        tblFacturas.setItems(getFacturas());
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("NumeroFactura"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Factura, String>("FechaFactura"));
        colNit.setCellValueFactory(new PropertyValueFactory<Factura, String>("NitFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Factura, String>("EstadoFactura"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("CodigoCliente"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("NombreCliente"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Factura, Double>("TotalFactura"));
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
    
    public ObservableList<Cliente> getClientes(){
        
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCliente}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cliente(resultado.getInt("CodigoCliente"), resultado.getString("NombreCliente"), resultado.getString("NitCliente"), resultado.getString("DireccionCliente")));
            }    
        } catch(SQLException e){
            e.printStackTrace();    
        }  
        return listarCliente = FXCollections.observableArrayList(lista);
    }
    
    
    public void seleccionarElementos(){
        if(tblFacturas.getSelectionModel().getSelectedIndex() > -1){
            cmbFactura.getSelectionModel().select(buscarFactura(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura()));
            dateFecha.setValue(LocalDate.parse(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getFechaFactura()));
            txtNit.setText(((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getNitFactura());
            txtEstado.setText(((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getEstadoFactura());
            cmbCliente.getSelectionModel().select(buscarCliente(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            txtTotal.setText(String.valueOf(((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getTotalFactura()));
        }
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
    
    public Cliente buscarCliente (int CodigoCliente){
        Cliente  resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarCliente(?)}");
            procedimiento.setInt(1, CodigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Cliente(registro.getInt("CodigoCliente"), registro.getString("NombreCliente"), registro.getString("NitCliente"), registro.getString("DireccionCliente"));
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
        dateFecha.setDisable(false);
        txtNit.setEditable(true);
        txtEstado.setEditable(true);
        txtTotal.setEditable(true);
        cmbCliente.setDisable(false);
        cmbFactura.setDisable(true);
    }
    
    public void desactivarControles(){
        dateFecha.setDisable(true);
        txtNit.setEditable(false);
        txtEstado.setEditable(false);
        txtTotal.setEditable(false);
        cmbCliente.setDisable(false);
        cmbFactura.setDisable(false);
    }
    
    public void limpiarControles(){
        dateFecha.setValue(null);
        txtNit.setText("");
        txtEstado.setText("");
        txtTotal.setText("");
        cmbCliente.setValue("");
        cmbFactura.setValue("");
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblFacturas.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    dateFecha.setDisable(false);
                    txtNit.setEditable(true);
                    txtTotal.setEditable(true);
                    txtEstado.setEditable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar una Factura!");
                }
            break;
            
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                dateFecha.setDisable(false);
                txtNit.setEditable(false);
                txtEstado.setEditable(false);
                txtTotal.setEditable(false);  
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarFactura(?,?,?,?,?,?)}");
            Factura registro = (Factura)tblFacturas.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(((Factura)cmbFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
            registro.setFechaFactura(String.valueOf(dateFecha.getValue()));
            registro.setNitFactura(txtNit.getText());
            registro.setEstadoFactura(txtEstado.getText());
            registro.setCodigoCliente(((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setTotalFactura(Double.valueOf(txtTotal.getText()));
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getFechaFactura());
            procedimiento.setString(3, registro.getNitFactura());
            procedimiento.setString(4, registro.getEstadoFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setDouble(6, registro.getTotalFactura());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
  
    public void eliminar(){
        if(tipoDeOperaciones == FacturasController.operaciones.GUARDAR){
            desactivarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperaciones = FacturasController.operaciones.NINGUNO;
        }else{
            tipoDeOperaciones = operaciones.ELIMINAR;
            switch(tipoDeOperaciones){
                case ELIMINAR:
                    if(tblFacturas.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarFactura (?)}");
                                procedimiento.setInt(1, ((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura());
                                procedimiento.execute();
                                listarFactura.remove(tblFacturas.getSelectionModel().getSelectedIndex());
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
        Factura registro = new Factura();
        registro.setFechaFactura(String.valueOf(dateFecha.getValue()));
        registro.setNitFactura(txtNit.getText());
        registro.setEstadoFactura(txtEstado.getText());
        registro.setCodigoCliente(((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setTotalFactura(Double.valueOf(txtTotal.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarFactura(?,?,?,?,?)}");
            procedimiento.setString(1, registro.getFechaFactura());
            procedimiento.setString(2, registro.getNitFactura());
            procedimiento.setString(3, registro.getEstadoFactura());
            procedimiento.setInt(4, registro.getCodigoCliente());
            procedimiento.setDouble(5, registro.getTotalFactura());
            procedimiento.execute();
            listarFactura.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }  
 
    public void generarReporte(){
        if(tipoDeOperaciones == operaciones.ACTUALIZAR){
            desactivarControles();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoDeOperaciones = operaciones.NINGUNO;
        }else{
            if(tblFacturas.getSelectionModel().getSelectedItem() != null){
                Map parametros = new HashMap();
                int numeroFactura = ((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura();
                parametros.put("_NumeroFactura", numeroFactura);
                GenerarReporte.mostrarReporte("ReporteFacturaM.jasper", "Reporte de Facturas", parametros);
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
    
    public void ventanaDetalleFacturas(){
        escenarioPrincipal.ventanaDetalleFacturas();
    }
    
}

