//@author: Victor Hernandez Meléndez.

package org.victorhernandez.bean;

//Creación de clase Marca.
public class Marca {
    
    //Atributos de la clase Marca.
    private int CodigoMarca;
    private String DescripcionMarca;
    
    //Costructor vacío de la clase Marca.
    public Marca(){
        
    }

    //Costructor con los parámetros entrantes.
    public Marca(int CodigoMarca, String DescripcionMarca) {
        this.CodigoMarca = CodigoMarca;
        this.DescripcionMarca = DescripcionMarca;
    }

    //Métodos get y set de los atributos.
    
    public int getCodigoMarca() {
        return CodigoMarca;
    }

    public void setCodigoMarca(int CodigoMarca) {
        this.CodigoMarca = CodigoMarca;
    }

    public String getDescripcionMarca() {
        return DescripcionMarca;
    }

    public void setDescripcionMarca(String DescripcionMarca) {
        this.DescripcionMarca = DescripcionMarca;
    }
    
    //Método encargado de concatenar en el combobox.
    public String toString(){
        return getCodigoMarca()+ ". "+getDescripcionMarca();
    }
  
}
