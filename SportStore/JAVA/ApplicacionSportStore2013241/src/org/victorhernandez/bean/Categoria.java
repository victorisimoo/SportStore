//@author: Victor Hernández Meléndez.

package org.victorhernandez.bean;

// Creación de clase Categoria.
public class Categoria {
    
    //Atributos de la clase Categoria.
    private int CodigoCategoria;
    private String DescripcionCategoria;
    
    //Constructor sin parámetros de la clase Categoria.
    public Categoria(){
        
    }

    //Constructor con los parámetros entrantes de la clase Categoria.
    public Categoria(int CodigoCategoria, String DescripcionCategoria) {
        this.CodigoCategoria = CodigoCategoria;
       this.DescripcionCategoria = DescripcionCategoria;
    }

    //Metodos get y set de los atributos.
    
    //Metodos get y set de CodigoCategoria.
    public int getCodigoCategoria() {
        return CodigoCategoria;
    }

    public void setCodigoCategoria(int CodigoCategoria) {
        this.CodigoCategoria = CodigoCategoria;
    }

    // Metodos get y set de DescripcionCategoria.
    public String getDescripcionCategoria() {
        return DescripcionCategoria;
    }

    public void setDescripcionCategoria(String DescripcionCategoria) {
        this.DescripcionCategoria = DescripcionCategoria;
    }
    
    //Método encargado de concatenar para el combobox.
    public String toString(){
        return getCodigoCategoria()+ ". " +getDescripcionCategoria();
    }
}
