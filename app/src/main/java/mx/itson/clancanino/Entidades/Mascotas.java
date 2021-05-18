package mx.itson.clancanino.Entidades;

import android.content.Context;

public class Mascotas {

    private int id;
    private String nombre;
    private int edad;
    private String foto;
    private String especie;

    Context context;

    public Mascotas (Context context){
        this.context = context;

    }

    public Mascotas(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }


}
