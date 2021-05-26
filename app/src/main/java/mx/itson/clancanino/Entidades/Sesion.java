package mx.itson.clancanino.Entidades;

public class Sesion {

    private int success;
    private int status;
    private String message;
    private int id;
    private String nombre;
    private String rol;
    private String correo;
    private int cuentaActiva;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCuentaActiva() {
        return cuentaActiva;
    }

    public void setCuentaActiva(int cuentaActiva) {
        this.cuentaActiva = cuentaActiva;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getIdUsuario() {
        return id;
    }

    public void setIdUsuario(int idUsuario) {
        this.id = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }







}
