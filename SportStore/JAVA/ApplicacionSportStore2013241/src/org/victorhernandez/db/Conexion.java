//@author: Victor Hernández Meléndez.

package org.victorhernandez.db;

import java.sql.DriverManager; //Hace la conexion con el ODBC
import java.sql.ResultSet; // Para poder ejecutar los querys.
import java.sql.Connection; //Crea la conexion con la base de datos.
import java.sql.SQLException; //Provee informacion si la informacion tiene error.
import java.sql.Statement; //Sirve para enviar las sentencias de SQL hacia la base de datos.
import com.microsoft.sqlserver.jdbc.SQLServerDriver; //Para utilizar el driver.

public class Conexion {
    
    private Connection conexion;
    private Statement sentencia;
    private static Conexion instancia;
    
    public Conexion(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            conexion = DriverManager.getConnection("jdbc:sqlserver://VictorHernandez:0;instanceName=<InstanceName>;dataBaseName=DBApplicationSportStore2013241;user=<user>;password=<password>;");
            
        } catch(ClassNotFoundException e){
            e.printStackTrace();
            
        } catch (InstantiationException e){
            e.printStackTrace();
            
        } catch (IllegalAccessException e){
            e.printStackTrace();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public static Conexion getInstancia(){
        if (instancia == null){
            instancia = new Conexion();
        } return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }  
}
