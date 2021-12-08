package es.joseluisgs.dam;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Hola a ObjectBD con JPA");

        // Me voy a cargar la databae primero
        Files.deleteIfExists(Path.of("db/empleados.odb"));

        // EntityManager JPA
//        Map<String, String> properties = new HashMap<String, String>();
//        properties.put("javax.persistence.jdbc.user", "admin");
//        properties.put("javax.persistence.jdbc.password", "admin");
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
//                "objectdb://localhost:6136/myDbFile.odb", properties);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            // Para tranformar a JSON: https://www.baeldung.com/java-json-binding-api
            Jsonb jsonb = JsonbBuilder.create();

            // Vamos a insertar 3 Departamentos
            System.out.println("Creando Departamentos");
            transaction.begin();
            Departamento dep1 = new Departamento();
            dep1.setNombre("Prueba " + LocalDateTime.now());
            entityManager.persist(dep1);

            Departamento dep2 = new Departamento();
            dep2.setNombre("Java Departamento");
            entityManager.persist(dep2);

            Departamento dep3 = new Departamento();
            dep3.setNombre("TypeScript Departamento");
            entityManager.persist(dep3);
            transaction.commit();

            // Listamos todos los departamentos, es un TypedQuery
            System.out.println("Listando Departamentos");
            entityManager.createNamedQuery("Departamento.finAll", Departamento.class).getResultList().forEach(System.out::println);
            // Como JSON
            entityManager.createNamedQuery("Departamento.finAll", Departamento.class).getResultList().forEach(d -> System.out.println(jsonb.toJson(d)));

            // Operaciones CRUD con Departamento
            // Insertar - Create
            System.out.println("CRUD Creando Departamento");
            transaction.begin();
            Departamento dep4 = new Departamento();
            dep4.setNombre("Nuevo Departamento");
            entityManager.persist(dep4);
            transaction.commit();
            System.out.println(dep4);

            // Leer -- Read
            System.out.println("CRUD Leyendo Departamento");
            Departamento dep = entityManager.find(Departamento.class, 4);
            System.out.println(dep);

            // Actualizar -- Update
            System.out.println("CRUD Actualizando Departamento");
            dep = entityManager.find(Departamento.class, 4);
            transaction.begin();
            dep.setNombre("Prueba Update");
            entityManager.merge(dep);
            transaction.commit();
            dep = entityManager.find(Departamento.class, 4);
            System.out.println(dep);

            // Eliminamos los datos
            System.out.println("CRUD Eliminando Departamento");
            transaction.begin();
            entityManager.remove(dep);
            transaction.commit();

            // Imprimimos todos
            System.out.println("Listando Departamentos");
            entityManager.createNamedQuery("Departamento.finAll", Departamento.class).getResultList().forEach(System.out::println);

            // Crud con usuarios

            // Insertamos usuarios
            transaction.begin();
            Empleado emp1 = new Empleado();
            emp1.setNombre("Pepe");
            emp1.setApellidos("Perez");
            emp1.setDepartamento(dep1);
            entityManager.persist(emp1);

            Empleado emp2 = new Empleado();
            emp2.setNombre("Luis");
            emp2.setApellidos("Lopez");
            emp2.setDepartamento(dep2);
            entityManager.persist(emp2);

            Empleado emp3 = new Empleado();
            emp3.setNombre("Ana");
            emp3.setApellidos("Anaya");
            emp3.setDepartamento(dep1);
            entityManager.persist(emp3);

            Empleado emp4 = new Empleado();
            emp4.setNombre("Pedro");
            emp4.setApellidos("Perez");
            emp4.setDepartamento(dep2);
            entityManager.persist(emp4);

            Empleado emp5 = new Empleado();
            emp5.setNombre("Elena");
            emp5.setApellidos("Fernandez");
            emp5.setDepartamento(dep2);
            entityManager.persist(emp5);
            transaction.commit();

            // Imprimimos todos
            System.out.println("Listando Empleados");
            entityManager.createNamedQuery("Empleado.finAll", Empleado.class).getResultList().forEach(System.out::println);

            // Operaciones CRUD con Departamento
            // Insertar - Create
            System.out.println("CRUD Creando Empleado");
            transaction.begin();
            Empleado emp = new Empleado();
            emp.setNombre("Nuevo");
            emp.setApellidos("Empleado");
            emp.setDepartamento(dep1);
            entityManager.persist(emp);
            transaction.commit();
            System.out.println(emp);

            // Leer -- Read
            System.out.println("CRUD Leyendo Empleado");
            emp = entityManager.find(Empleado.class, emp.getId());
            System.out.println(emp);

            // Actualizar -- Update
            System.out.println("CRUD Actualizando Empleado");
            emp = entityManager.find(Empleado.class, emp.getId());
            transaction.begin();
            emp.setNombre("Prueba");
            emp.setApellidos("Update Empleado");
            emp.setDepartamento(dep2);
            entityManager.merge(emp);
            transaction.commit();
            emp = entityManager.find(Empleado.class, emp.getId());
            System.out.println(emp);

            // Eliminamos los datos
            System.out.println("CRUD Eliminando Empleado");
            transaction.begin();
            entityManager.remove(emp);
            transaction.commit();

            // Imprimimos todos
            System.out.println("Listando Empleados");
            entityManager.createNamedQuery("Empleado.finAll", Empleado.class).getResultList().forEach(System.out::println);

            System.out.println("Consultas");
            System.out.println("Empleados Del departamento 1, usando su realación");
            dep = entityManager.find(Departamento.class, 1);
            entityManager.refresh(dep);
            System.out.println(dep);
            // Sale nulo, porque a los departamentos no le hemos asignado los usuarios, no tiene la bidireccionalidad.

            System.out.println("Listar empleados del Departamento uno 1 usando NamedQuery parametrizada desde Empleados ");
            List<Empleado> empleados = entityManager.createNamedQuery("Empleado.porDepartamentoNamed", Empleado.class)
                    .setParameter(1, "Java Departamento")
                    .getResultList();
            empleados.forEach(System.out::println);

            System.out.println("Listar empleados del Departamento uno 1 usando NamedQuery parametrizada desde Departamento ");
            entityManager.createNamedQuery("Departamento.getEmpleados", Empleado.class)
                    .setParameter("id", 1)
                    .getResultList().forEach(System.out::println);


            // Contamos los Empleados del Departamento 1
            Query queryDepartamento = entityManager.createQuery("SELECT count(e.id) FROM Empleado e where e.departamento.id = 1");
            System.out.println("Empleados del departamento 1: " + queryDepartamento.getSingleResult());

            // Ejemplos con puntos
            // Puntos(entityManager);

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

    }

    private static void Puntos(EntityManager em) {
        // Almacenamos 1000 puntos en la base de datos
        // Como siempre una transacción
        em.getTransaction().begin();
        for (int i = 0; i < 1000; i++) {
            Point p = new Point(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();

        // Encontramos eltotal de puntos
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        System.out.println("Total de Puntos: " + q1.getSingleResult());

        // Valor de la X Medio
        Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
        System.out.println("Average X: " + q2.getSingleResult());

        // Obtenemos todos los puntos
        TypedQuery<Point> query =
                em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results = query.getResultList();
        results.forEach(System.out::println);
    }
}

