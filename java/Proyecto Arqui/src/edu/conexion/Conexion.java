
package edu.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 
/* Prueba */
public class Conexion 
{
    private Connection con;

    
    //Metodo GET y SET
    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    //METODO CONECTAR
    
    public void conectar() throws Exception
    {
        try 
        {
           Class.forName("com.mysql.jdbc.Driver"); 
           con=DriverManager.getConnection("jdbc:mysql://localhost:3306/arqui2018?user=root&password=grupo10");
          
        } 
        
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al conectar" + e.toString());
        }
        
    }
    
    //METODO DESCONECTAR
    public  void desconectar() throws Exception
    {
        try 
        {
            if(con!=null)
            {
                if(con.isClosed()==false)
                {
                    con.close();
                }
            
            }    
        } 
        catch (Exception e) 
        {
            throw e;
        }
    }
}
