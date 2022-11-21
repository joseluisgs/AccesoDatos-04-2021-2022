package es.joseluisgs.dam;



import es.joseluisgs.dam.model.Departamento;
import es.joseluisgs.dam.model.Empleado;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.List;


public class App {
    public static void main(String[] args) {
        System.out.println("Hola Hibernate OGM con MongoDB");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            System.out.println("Conexión Establecida");
            System.out.println(entityManagerFactory);

            // Vamos a borra algunos datos si los hay
            entityManager.createNamedQuery("Departamento.findAll", Departamento.class).getResultList().forEach(d -> {
                // Como estan unidos por el cascade, si me cargo el departamento se eliminan sus empleados
                transaction.begin();
                entityManager.remove(d);
                transaction.commit();
            });

            // Creando departamento
            System.out.println("Añadiendo Departamentos...");

            Departamento dep1 = new Departamento("Java Departamento", new HashSet<Empleado>());
            Departamento dep2 = new Departamento("TypeScript Departamento", new HashSet<Empleado>());
            Departamento dep3 = new Departamento("Prueba Departamento", new HashSet<Empleado>());

            System.out.println("Añadiendo empleados...");

            // Empleados del departamento 1
            Empleado emp1 = new Empleado("Pepe Perez", 10000.0, dep1);
            Empleado emp2 = new Empleado( "Ana Anaya", 15000.0, dep1);
            // Bidireccionalidad dep1
            dep1.getEmpleados().add(emp1);
            dep1.getEmpleados().add(emp2);

            // Empleados departamento 2
            Empleado emp3 = new Empleado("Luis Lopez", 11000.0, dep2);
            Empleado emp4 = new Empleado("Pedro Perez", 14000.0, dep2);
            Empleado emp5 = new Empleado("Elena Fernandez", 13000.0, dep2);
            // Bidireccionalidad dep2
            dep2.getEmpleados().add(emp3);
            dep2.getEmpleados().add(emp4);
            dep2.getEmpleados().add(emp5);

            transaction.begin();
            System.out.println("Insertando Departamentos");
            // insertando departamentos
            entityManager.persist(dep1);
            entityManager.persist(dep2);
            entityManager.persist(dep3);

            // Ya de por si tenemos los empleados insertados por el cascade, pero si queremos los instertamos
            System.out.println("Insertando Empleados");
            entityManager.persist(emp1);
            entityManager.persist(emp2);
            entityManager.persist(emp3);
            entityManager.persist(emp4);
            entityManager.persist(emp5);

            transaction.commit();

            // Listamos cosas
            System.out.println("Listado de todos los departamentos");
            entityManager.createNamedQuery("Departamento.findAll", Departamento.class).getResultList().forEach(System.out::println);

            System.out.println("Listado de todos los Empleados");
            entityManager.createNamedQuery("Empleado.findAll", Empleado.class).getResultList().forEach(System.out::println);

            // Modificamos el departamento 3, pero lo buscamos
            System.out.println("Modificando Departamento 3");
            Departamento buscadoDepartamento = entityManager.find(Departamento.class, dep3.getId());
            buscadoDepartamento.setNombre("Departamento Actualizado");
            transaction.begin();
            entityManager.merge(buscadoDepartamento);
            transaction.commit();
            System.out.println("Modificado: " + buscadoDepartamento);

            // Eliminamos un deopartamento
            System.out.println("Eliminando Departamento 3");
            transaction.begin();
            entityManager.remove(buscadoDepartamento);
            transaction.commit();
            System.out.println("Eliminado: " + buscadoDepartamento);

            // Modificando un empleado
            System.out.println("Modificando Empleado 1");
            Empleado buscadoEmpleado = entityManager.find(Empleado.class, emp1.getId());
            buscadoEmpleado.setNombre("Empleado Actualizado");
            buscadoEmpleado.setSalario(16000);
            transaction.begin();
            entityManager.merge(buscadoEmpleado);
            transaction.commit();
            System.out.println("Modificado: " + buscadoEmpleado);


            // De la misma manera con empleados ...

            // Empleados del departamento 2
            System.out.println("Empleados del departamento 2");
            buscadoDepartamento = entityManager.find(Departamento.class, dep2.getId());
            buscadoDepartamento.getEmpleados().forEach(System.out::println);

            // System.out.println("Listar empleados del Departamento uno 1 ");
           /*
           Hibernate OGM does not currently support JP-QL queries with JOIN on *-to-many associations:
           https://docs.jboss.org/hibernate/stable/ogm/reference/en-US/html_single/#_using_jp_ql
           https://docs.jboss.org/hibernate/stable/ogm/reference/en-US/html_single/#ogm-jpql-query
           entityManager.createNamedQuery("Empleado.porDepartamentoNamed", Empleado.class)
                    .setParameter(1, "Java Departamento")
                    .getResultList().forEach(System.out::println);*/

            System.out.println("Emplados que su salario es >=14000 API Stream");
            entityManager.createNamedQuery("Empleado.findAll", Empleado.class)
                    .getResultList().stream().filter(e-> e.getSalario() >= 14000).forEach(System.out::println);

            System.out.println("Empleados que su salario es >=14000 NamedQuery");
            entityManager.createNamedQuery("Empleado.porSalarioSuperior", Empleado.class)
                    .setParameter("salario", 14000.0)
                    .getResultList().forEach(System.out::println);

            // O saber cuantos empleados por departamento cobran más de 13000 del departamento 2


            // O saber cuantos empleados por departamento cobran más de 13000 d

            // Puedo borrar un empleado...
            // Eliminamos un departamento
            System.out.println("Eliminando Empleado 1");
            transaction.begin();
            entityManager.remove(buscadoEmpleado);
            transaction.commit();
            System.out.println("Eliminado: " + buscadoEmpleado);

            // Ya no saldrá en la búsquedas de los que ganaban mas
            System.out.println("Eliminando Empleado 1 de la lista de los que mas cobraban de 14000");
            entityManager.createNamedQuery("Empleado.findAll", Empleado.class)
                    .getResultList().stream().filter(e-> e.getSalario() >= 14000).forEach(System.out::println);

            // Finalmente si me cargo un departamento, por su borrado en cascada, se borran sus empleados
            System.out.println("Eliminando Departamento 2");
            buscadoDepartamento = entityManager.find(Departamento.class, dep2.getId());
            transaction.begin();
            entityManager.remove(buscadoDepartamento);
            transaction.commit();
            System.out.println("Eliminado: " + buscadoDepartamento);

            System.out.println("Listado de todos los Empleados");
            entityManager.createNamedQuery("Empleado.findAll", Empleado.class).getResultList().forEach(System.out::println);

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }


    }
}
