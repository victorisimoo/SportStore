//@author: Victor Hernandez Melendez

package org.victorhernandez.bean;

//Creación de la clase Talla.
public class Talla {
    
    //Atributos de la clase Talla.
    private int CodigoTalla;
    private String DescripcionTalla;
    
    //Costructor vacío de la clase Talla
    public Talla(){
        
    }

    //Costructor con los parámetros entrantes de la clase talla.
    public Talla(int CodigoTalla, String DescripcionTalla) {
        this.CodigoTalla = CodigoTalla;
        this.DescripcionTalla = DescripcionTalla;
    }

    //Métodos get y set de la clase Talla.
    
    public int getCodigoTalla() {
        return CodigoTalla;
    }

    public void setCodigoTalla(int CodigoTalla) {
        this.CodigoTalla = CodigoTalla;
    }

    public String getDescripcionTalla() {
        return DescripcionTalla;
    }

    public void setDescripcionTalla(String DescripcionTalla) {
        this.DescripcionTalla = DescripcionTalla;
    }
    
    
    //Método para concatenar en el combobox.
    public String toString(){
        return getCodigoTalla()+ ". "+getDescripcionTalla();
    }
    
    
}
