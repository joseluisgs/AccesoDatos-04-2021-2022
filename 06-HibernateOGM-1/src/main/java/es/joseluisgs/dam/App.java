package es.joseluisgs.dam;



import es.joseluisgs.dam.model.Persona;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.Instant;


public class App {
    public static void main(String[] args) {
        System.out.println("Hola Hibernate OGM con MongoDB");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        System.out.println("Conexión establecidad");
        System.out.println(entityManagerFactory);


        Persona p = new Persona().setNombre("Prueba " + Instant.now()).setApellidos("Persona");

        // Operaciones CRUD
        System.out.println("Insertando Persona");
        transaction.begin();
        entityManager.persist(p);
        transaction.commit();
        System.out.println(p);

        System.out.println("Listando personas con consulta JPA-JPQL");
        entityManager.createQuery("select p from Persona p",Persona.class).getResultList().forEach(System.out::println);

        System.out.println("Listando personas con consulta nombrada");
        entityManager.createNamedQuery("Persona.findAll", Persona.class).getResultList().forEach(System.out::println);

        System.out.println("Buscando Persona con id: " + p.getId());
        Persona buscada = entityManager.find(Persona.class, p.getId());
        System.out.println(p);

        System.out.println("Actualizando Persona con id: " + p.getId());
        buscada.setNombre("Nombre Actualizada").setApellidos("Apellidos Actualizados");
        transaction.begin();
        entityManager.merge(buscada);
        transaction.commit();
        System.out.println(buscada);

        System.out.println("Borrando Persona con id: " + p.getId());
        transaction.begin();
        entityManager.remove(buscada);
        transaction.commit();
        System.out.println(buscada);




        entityManager.close();
        entityManagerFactory.close();

    }
}
