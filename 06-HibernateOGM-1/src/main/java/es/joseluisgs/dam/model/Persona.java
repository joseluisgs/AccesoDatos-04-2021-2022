package es.joseluisgs.dam.model;

import org.bson.types.ObjectId;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    private String nombre;
    private String apellidos;

    public String getApellidos() {
        return apellidos;
    }

    public Persona setApellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Persona() {
        super();
    }

    public Persona(String nombre) {
        super();
        this.nombre = nombre;
    }

    public Persona setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                '}';
    }
}