//@author: Victor Hernandez Meléndez.
package org.victorhernandez.bean;

//Creación de la clase Producto.
public class Producto {
    
    //Atributos de la clase Producto.
    private int CodigoProducto;
    private double PrecioUnitario;
    private String DescripcionProducto;
    private double PrecioDocena;
    private double PrecioPorMayor;
    private int ExistenciaProducto;
    private int CodigoCategoria;
    private int CodigoMarca;
    private int CodigoTalla;
    private String Imagen;
    
    //Costructor vacío de la clase Producto.
    public Producto(){
        
    }

    //Costructor con los parámetros entrantes a la clase.
    public Producto(int CodigoProducto, double PrecioUnitario, String DescripcionProducto, double PrecioDocena, double PrecioPorMayor, int ExistenciaProducto, int CodigoCategoria, int CodigoMarca, int CodigoTalla, String Imagen) {
        this.CodigoProducto = CodigoProducto;
        this.PrecioUnitario = PrecioUnitario;
        this.DescripcionProducto = DescripcionProducto;
        this.PrecioDocena = PrecioDocena;
        this.PrecioPorMayor = PrecioPorMayor;
        this.ExistenciaProducto = ExistenciaProducto;
        this.CodigoCategoria = CodigoCategoria;
        this.CodigoMarca = CodigoMarca;
        this.CodigoTalla = CodigoTalla;
        this.Imagen = Imagen;
    }

    //Métodos get y set de los atributos de la clase.
    
    public int getCodigoProducto() {
        return CodigoProducto;
    }

    public void setCodigoProducto(int CodigoProducto) {
        this.CodigoProducto = CodigoProducto;
    }

    public double getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(double PrecioUnitario) {
        this.PrecioUnitario = PrecioUnitario;
    }

    public String getDescripcionProducto() {
        return DescripcionProducto;
    }

    public void setDescripcionProducto(String DescripcionProducto) {
        this.DescripcionProducto = DescripcionProducto;
    }

    public double getPrecioDocena() {
        return PrecioDocena;
    }

    public void setPrecioDocena(double PrecioDocena) {
        this.PrecioDocena = PrecioDocena;
    }

    public double getPrecioPorMayor() {
        return PrecioPorMayor;
    }

    public void setPrecioPorMayor(double PrecioPorMayor) {
        this.PrecioPorMayor = PrecioPorMayor;
    }

    public int getExistenciaProducto() {
        return ExistenciaProducto;
    }

    public void setExistenciaProducto(int ExistenciaProducto) {
        this.ExistenciaProducto = ExistenciaProducto;
    }

    public int getCodigoCategoria() {
        return CodigoCategoria;
    }

    public void setCodigoCategoria(int CodigoCategoria) {
        this.CodigoCategoria = CodigoCategoria;
    }

    public int getCodigoMarca() {
        return CodigoMarca;
    }

    public void setCodigoMarca(int CodigoMarca) {
        this.CodigoMarca = CodigoMarca;
    }

    public int getCodigoTalla() {
        return CodigoTalla;
    }

    public void setCodigoTalla(int CodigoTalla) {
        this.CodigoTalla = CodigoTalla;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }
    
    //Método encargado de concatenar en el combobox.
    public String toString(){
        return getCodigoProducto()+". "+getDescripcionProducto();
    }
   
    
}
