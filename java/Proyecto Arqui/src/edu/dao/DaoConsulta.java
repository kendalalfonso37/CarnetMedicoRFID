
package edu.dao;

import edu.conexion.Conexion;
import edu.modelo.Consulta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class DaoConsulta extends Conexion
{
    
    public ArrayList<Consulta> mostrarConsulta() throws Exception
    {
        ArrayList<Consulta> consultas = new ArrayList();
        ResultSet res=null;
        try {
            this.conectar();
            String sql="select c.idConsulta, p.codigoPaciente, c.fecha, c.hora, c.descripcion "
                    + "from consulta as c "
                    + "inner join paciente as p on p.idPaciente = c.idPaciente";
            PreparedStatement pre=this.getCon().prepareCall(sql);
            res = pre.executeQuery();
            while (res.next()){
                Consulta consulta=new Consulta();
                consulta.setIdConsulta(res.getInt("idConsulta"));
                consulta.setIdPaciente((res.getString("codigoPaciente")));
                consulta.setFecha(res.getString("fecha"));
                consulta.setHora(res.getString("hora"));
                consulta.setDescripcion(res.getString("descripcion"));
                consultas.add(consulta);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
     return consultas;   
    }
    
    public ArrayList<Consulta> buscarConsulta(String busqueda) throws Exception {
        ArrayList<Consulta> consultas = new ArrayList();
        ResultSet res=null;
        try {
            this.conectar();
             String sql="select c.idConsulta, p.codigoPaciente, c.fecha, c.hora, c.descripcion "
                    + "from consulta as c "
                    + "inner join paciente as p on p.idPaciente = c.idPaciente "
                     + "where p.codigoPaciente=? ";
            PreparedStatement pre=this.getCon().prepareCall(sql);
            pre.setString(1,busqueda);
            res = pre.executeQuery();
            while (res.next()){
                Consulta consulta=new Consulta();
                consulta.setIdConsulta(res.getInt("idConsulta"));
                consulta.setIdPaciente((res.getString("codigoPaciente")));
                consulta.setFecha(res.getString("fecha"));
                consulta.setHora(res.getString("hora"));
                consulta.setDescripcion(res.getString("descripcion"));
                consultas.add(consulta);
            }
            
        } catch (Exception e) {
        }
        finally{
            this.desconectar();
            
        }
        return consultas;
    }
    
}
