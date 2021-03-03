//@author: Victor Hernández Meléndez.

package org.victorhernandez.system;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.victorhernandez.controller.CategoriaController;
import org.victorhernandez.controller.ClienteController;
import org.victorhernandez.controller.CompraController;
import org.victorhernandez.controller.DetalleCompraController;
import org.victorhernandez.controller.DetalleFacturaController;
import org.victorhernandez.controller.EmailClienteController;
import org.victorhernandez.controller.EmailProveedorController;
import org.victorhernandez.controller.FacturasController;
import org.victorhernandez.controller.InformacionController;
import org.victorhernandez.controller.LoginController;
import org.victorhernandez.controller.MarcasController;
import org.victorhernandez.controller.MenuPrincipalController;
import org.victorhernandez.controller.ProductoController;
import org.victorhernandez.controller.TallaController;
import org.victorhernandez.controller.ProveedorController;
import org.victorhernandez.controller.TelefonoClienteController;
import org.victorhernandez.controller.TelefonoProveedorController;
import org.victorhernandez.controller.UsuarioController;
import org.victorhernandez.reportes.GenerarReporte;

public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/victorhernandez/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    
    
    @Override
    public void start(Stage escenarioPrincipal) {
       this.escenarioPrincipal = escenarioPrincipal;
       escenarioPrincipal.setTitle("SportStore Application");
       ventanaNueva();
       escenarioPrincipal.getIcons().add(new Image(Principal.class.getResourceAsStream("/org/victorhernandez/images/PrincipalIcon.png")));
       escenarioPrincipal.show();
        
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 600, 433);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace(); 
        }
    }  
        
    public void ventanaCategorias(){
        try{
            CategoriaController categoriaController = (CategoriaController) cambiarEscena("MenuCategoriasView.fxml", 600, 400);
            categoriaController.setEscenarioPrincipal (this);
        }catch(Exception e){
            e.printStackTrace();
        }  
    }
    
    public void ventanaMarcas(){
        try{
            MarcasController marcaController = (MarcasController) cambiarEscena("MenuMarcasView.fxml", 600, 400);
            marcaController.setEscenarioPrincipal (this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTallas(){
        try{
            TallaController tallaController = (TallaController) cambiarEscena ("MenuTallasView.fxml", 600, 400);
            tallaController.setEscenarioPrincipal(this);    
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProveedores(){
        try{
            ProveedorController proveedorController = (ProveedorController) cambiarEscena ("MenuProveedoresView.fxml", 600, 400);
            proveedorController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaClientes(){
        try{
            ClienteController clienteController = (ClienteController) cambiarEscena ("ClientesView.fxml", 600, 400);
            clienteController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaFacturas(){
        try{
            FacturasController facturasController = (FacturasController) cambiarEscena ("MenuFacturasView.fxml", 600, 400);
            facturasController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProductos(){
        try{
            ProductoController productoController = (ProductoController) cambiarEscena ("MenuProductosView.fxml", 600, 400);
            productoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCompras(){
        try{
            CompraController compraController = (CompraController) cambiarEscena("MenuComprasView.fxml", 600, 400);
            compraController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmailClientes(){
        try{
            EmailClienteController emailClienteController = (EmailClienteController) cambiarEscena("MenuEmailClientesView.fxml", 600, 400);
            emailClienteController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTelefonoClientes(){
        try{
            TelefonoClienteController telefonoClienteController = (TelefonoClienteController) cambiarEscena("MenuTelefonoClienteView.fxml", 600, 400);
            telefonoClienteController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
       
    public void ventanaTelefonoProveedores(){
        try{
            TelefonoProveedorController telefonoProveedorController = (TelefonoProveedorController) cambiarEscena("MenuTelefonoProveedoresView.fxml", 600, 400);
            telefonoProveedorController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
    public void ventanaEmailProveedores(){
        try{
            EmailProveedorController emailProveedorController = (EmailProveedorController) cambiarEscena("MenuEmailProveedoresView.fxml", 600, 400);
            emailProveedorController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDetalleFacturas(){
        try{
            DetalleFacturaController detalleFacturaController = (DetalleFacturaController) cambiarEscena("MenuDetalleFacturasView.fxml", 600, 400);
            detalleFacturaController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    
    public void ventanaDetalleCompra(){
        try{
            DetalleCompraController detalleCompraController = (DetalleCompraController) cambiarEscena ("MenuDetalleView.fxml", 600, 400);
            detalleCompraController.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaInformacion(){
        try{
            InformacionController informacionController = (InformacionController) cambiarEscena ("InformacionView.fxml", 600, 400);
            informacionController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuarios(){
        try{
            UsuarioController usuarioController = (UsuarioController) cambiarEscena ("MenuIngresoUsuarioView.fxml", 600, 400);
            usuarioController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaNueva(){
         try{
            LoginController loginController = (LoginController) cambiarEscena ("MenuUsuarioView.fxml", 600, 400);
            loginController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void generarReporte(){
        Map parametros = new HashMap();
        parametros.put("_CodigoProducto", null);
        GenerarReporte.mostrarReporte("ReporteProductos.jasper", "Reporte de Productos", parametros);
    }
     
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{       
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA + fxml));
        escena = new Scene ((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) cargadorFXML.getController();
        return resultado;      
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
