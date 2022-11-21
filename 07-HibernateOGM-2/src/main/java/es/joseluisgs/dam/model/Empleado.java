package es.joseluisgs.dam.model;


import org.bson.types.ObjectId;

import javax.persistence.*;
import java.util.Objects;

@Entity
// @Embeddable Por si queremos hacerla embebida en algo, sin onToMany o Similares
@NamedQueries({
        @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
        @NamedQuery(name = "Empleado.porDepartamentoNamed", query = "SELECT e FROM Empleado e WHERE e.departamento.nombre = ?1"),
        @NamedQuery(name = "Empleado.porSalarioSuperior", query = "SELECT e FROM Empleado e WHERE e.salario>= :salario")
})
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String nombre;
    private double salario;
    private Departamento departamento;

    public Empleado() {
    }

    public Empleado(String nombre, double salario, Departamento departamento) {
        this.nombre = nombre;
        this.salario = salario;
        this.departamento = departamento;
    }

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

    public Empleado setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @Basic
    @Column(name = "salario") // No es obligatorio
    public double getSalario() {
        return salario;
    }

    public Empleado setSalario(double salario) {
        this.salario = salario;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "departamento", referencedColumnName = "id", nullable = false)
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", salario=" + salario +
                ", Departamento={id='" + departamento.getId() + '\''  + ", nombre= '" +departamento.getNombre() + '\'' + "}" + // Rompemos la recursividad
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(id, empleado.id) && Objects.equals(nombre, empleado.nombre) && Objects.equals(salario, empleado.salario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, salario);
    }
}
