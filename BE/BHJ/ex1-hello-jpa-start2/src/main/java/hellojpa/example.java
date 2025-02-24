package hellojpa;

import jakarta.persistence.*;

public class example {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Order order = new Order();
            OrderItem orderItem = new OrderItem();
            order.addOrderItem(orderItem);

            Book book = new Book("jpa", 1000, 10,
                    "hanjin", "a1b2c3");
            em.persist(book);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            emf.close();
            em.close();
        }
    }
}
