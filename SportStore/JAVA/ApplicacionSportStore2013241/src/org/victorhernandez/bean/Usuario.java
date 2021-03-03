//@author: Victor Noé Hernández Meléndez.
package org.victorhernandez.bean;

public class Usuario {
    
    private int CodigoUsuario;
    private String NombreUsuario;
    private String Usuario;
    private String EmailUsuario;
    private String TipoUsuario;
    private String ContrasenaUsuario;
    
    public Usuario(){
        
    }

    public Usuario(int CodigoUsuario, String NombreUsuario, String Usuario, String EmailUsuario, String TipoUsuario, String ContrasenaUsuario) {
        this.CodigoUsuario = CodigoUsuario;
        this.NombreUsuario = NombreUsuario;
        this.Usuario = Usuario;
        this.EmailUsuario = EmailUsuario;
        this.TipoUsuario = TipoUsuario;
        this.ContrasenaUsuario = ContrasenaUsuario;
    }

    public int getCodigoUsuario() {
        return CodigoUsuario;
    }

    public void setCodigoUsuario(int CodigoUsuario) {
        this.CodigoUsuario = CodigoUsuario;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getEmailUsuario() {
        return EmailUsuario;
    }

    public void setEmailUsuario(String EmailUsuario) {
        this.EmailUsuario = EmailUsuario;
    }

    public String getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(String TipoUsuario) {
        this.TipoUsuario = TipoUsuario;
    }

    public String getContrasenaUsuario() {
        return ContrasenaUsuario;
    }

    public void setContrasenaUsuario(String ContrasenaUsuario) {
        this.ContrasenaUsuario = ContrasenaUsuario;
    }
    
    
    
    
}
