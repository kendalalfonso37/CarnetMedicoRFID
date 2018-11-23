
package edu.modelo;

/**
 *
 * @author admin
 */
public class Consulta 
{
    private int idConsulta;
    private String idPaciente;
    private String fecha;
    private String hora;
    private String descripcion;

    public Consulta() {
    }

    public Consulta(int idConsulta, String idPaciente, String fecha, String hora, String descripcion) {
        this.idConsulta = idConsulta;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

   
    
}
