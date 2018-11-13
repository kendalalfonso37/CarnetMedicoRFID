
package edu.modelo;

/**
 *
 * @author DTO
 */
public class Paciente 
{
    private int idPaciente;
    private String codigoPaciente;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;

    public Paciente() {
    }

    public Paciente(int idPaciente, String codigoPaciente, String nombre, String apellido, int edad, String genero) {
        this.idPaciente = idPaciente;
        this.codigoPaciente = codigoPaciente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(String codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    
}
