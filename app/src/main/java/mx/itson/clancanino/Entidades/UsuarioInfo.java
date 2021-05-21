package mx.itson.clancanino.Entidades;

import com.google.gson.annotations.SerializedName;

public class UsuarioInfo {

    @SerializedName("success")
    private int success;

    @SerializedName("idUsuario")
    private int idUsuario;

    @SerializedName("edad")
    private int edad;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("numMascotas")
    private int numeroMascotas;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("cedula")
    private String cedula;

    @SerializedName("celula")
    private String celular;


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNumeroMascotas() {
        return numeroMascotas;
    }

    public void setNumeroMascotas(int numeroMascotas) {
        this.numeroMascotas = numeroMascotas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
