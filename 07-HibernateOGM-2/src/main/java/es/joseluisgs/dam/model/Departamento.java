package es.joseluisgs.dam.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d"),
})
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String nombre;
    private Set<Empleado> empleados;

    public Departamento() {
    }

    public Departamento(String nombre, Set<Empleado> empleados) {
        this.nombre = nombre;
        this.empleados = empleados;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public Departamento setId(long id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Al ponerle Cascade ALLL esta todo lo que hagamos con departamento afecta a los empleaoos
    @OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    public Set<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departamento that = (Departamento) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", empleados=" + empleados +
                '}';
    }
}
