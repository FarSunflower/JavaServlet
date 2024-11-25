package dao;

import model.Orders;
import utils.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderDAO {

    // Save a new order
    public void saveOrder(Orders order) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(order);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // Get all orders by a specific customer ID
    public List<Orders> getOrdersByCustomerId(int customerId) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        TypedQuery<Orders> query = em.createQuery("SELECT o FROM Orders o WHERE o.customer.id = :customerId", Orders.class);
        query.setParameter("customerId", customerId);
        List<Orders> orders = query.getResultList();
        em.close();
        return orders;
    }

    // Retrieve an order by ID
    public Orders getOrderById(int orderId) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        Orders order = em.find(Orders.class, orderId);
        em.close();
        return order;
    }

    // Update an existing order
    public void updateOrder(Orders order) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(order);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
