//@author: Victor Hernández Meléndez.

package org.victorhernandez.controller;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.victorhernandez.system.Principal;

public class InformacionController implements Initializable {
    
    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void enlaceLinkendin(){
        try{
            if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("https://www.linkedin.com/in/victor-noe-hernandez-melendez-37728b12a/"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void enlaceFacebook(){
        try{
            if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("https://www.facebook.com/profile.php?id=100015935647787"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
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
