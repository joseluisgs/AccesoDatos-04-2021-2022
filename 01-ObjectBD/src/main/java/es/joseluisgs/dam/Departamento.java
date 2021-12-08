package es.joseluisgs.dam;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Departamento.finAll", query = "SELECT d FROM Departamento d"),
        @NamedQuery(name = "Departamento.getEmpleados", query = "SELECT e FROM Empleado e where e.departamento.id = :id"),
})
public class Departamento {

    private long id;
    private String nombre;
    private Set<Empleado> empleados;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Departamento that = (Departamento) o;

        if (id != that.id) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER)
    public Set<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }
}
