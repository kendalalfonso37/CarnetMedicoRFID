
package edu.dao;

import edu.conexion.Conexion;
import edu.modelo.Paciente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class DaoPaciente extends Conexion
{
    public void insertarPaciente(Paciente pa) throws Exception {
        try {
            this.conectar();
            String sql="insert into paciente(codigoPaciente,nombre,apellido,edad,genero) "
                    + "value(?,?,?,?,?)";
            PreparedStatement pre=this.getCon().prepareStatement(sql);
            pre.setString(1, pa.getCodigoPaciente());
            pre.setString(2, pa.getNombre());
            pre.setString(3, pa.getApellido());
            pre.setInt(4, pa.getEdad());
            pre.setString(5, pa.getGenero());
            pre.executeUpdate();
            
        } catch (Exception e) {
            throw e;
        }
        finally
        {
            this.desconectar();
        }
        
    }
    
    public void modificarPaciente(Paciente pa) throws Exception{
        try {
            this.conectar();
            String sql="update paciente set codigoPaciente=?, nombre=?, apellido=?, edad=?, genero=? "
                    + "where idPaciente=?";
            PreparedStatement pre=this.getCon().prepareStatement(sql);
            pre.setString(1,pa.getCodigoPaciente());
            pre.setString(2,pa.getNombre());
            pre.setString(3,pa.getApellido());
            pre.setInt(4,pa.getEdad());
            pre.setString(5,pa.getGenero());
            pre.setInt(6,pa.getIdPaciente());
            pre.executeUpdate();
            
        } catch (Exception e) {
            throw e;
        }
        finally{
            this.desconectar();
            
        }
    }
    
    public void eliminarPaciente(Paciente pa) throws Exception {
        try {
            this.conectar();
            String sql="delete from paciente where idPaciente=?";
            PreparedStatement pre=this.getCon().prepareStatement(sql);
            pre.setInt(1,pa.getIdPaciente());
            pre.executeUpdate();
        } catch (Exception e) {
        }
        finally{
            this.desconectar();
            
        }
    }
    public ArrayList<Paciente> mostrarPaciente() throws Exception
    {
        ArrayList<Paciente> pacientes = new ArrayList();
        ResultSet res=null;
        try {
            this.conectar();
            String sql="select * from paciente";
            PreparedStatement pre=this.getCon().prepareCall(sql);
            res = pre.executeQuery();
            while (res.next()){
                Paciente pa=new Paciente();
                pa.setIdPaciente(res.getInt("idPaciente"));
                pa.setCodigoPaciente(res.getString("codigoPaciente"));
                pa.setNombre(res.getString("nombre"));
                pa.setApellido(res.getString("apellido"));
                pa.setEdad(res.getInt("edad"));
                pa.setGenero(res.getString("genero"));
                pacientes.add(pa);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
     return pacientes;   
    }
    
    public ArrayList<Paciente> buscarPaciente(String busqueda) throws Exception {
        ArrayList<Paciente> pacientes = new ArrayList();
        ResultSet res=null;
        try {
            this.conectar();
            String sql="select * from paciente where codigoPaciente=?";
            PreparedStatement pre=this.getCon().prepareCall(sql);
            pre.setString(1,busqueda);
            res = pre.executeQuery();
            while (res.next()){
                Paciente pa=new Paciente();
                pa.setIdPaciente(res.getInt("idPaciente"));
                pa.setCodigoPaciente(res.getString("codigoPaciente"));
                pa.setNombre(res.getString("nombre"));
                pa.setApellido(res.getString("apellido"));
                pa.setEdad(res.getInt("edad"));
                pa.setGenero(res.getString("genero"));
                pacientes.add(pa);
            }
            
        } catch (Exception e) {
        }
        finally{
            this.desconectar();
            
        }
        return pacientes;
    }
    
}
