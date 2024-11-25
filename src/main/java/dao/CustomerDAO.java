package dao;

import model.Customer;
import utils.EntityManagerUtil;

import javax.persistence.*;
import java.util.List;

public class CustomerDAO {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJpaUnit");

    // Save a customer
    public void saveCustomer(Customer customer) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(customer);  // Save the customer
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Re-throw the exception
        } finally {
            em.close();
        }
    }

    public Customer getCustomerById(int customerId) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        Customer customer = em.find(Customer.class, customerId);
        if (customer != null) {
            // Ensure that orders are loaded
            customer.getOrders().size();  // Triggers lazy loading for the orders
        }
        em.close();
        return customer;
    }
    public Customer getCustomerByIdWithOrders(int customerId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            // Use JOIN FETCH to load orders eagerly
            String query = "SELECT c FROM Customer c LEFT JOIN FETCH c.orders WHERE c.id = :customerId";
            Customer customer = em.createQuery(query, Customer.class)
                    .setParameter("customerId", customerId)
                    .getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null; // Handle case when customer is not found
        } finally {
            em.close();
        }
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = query.getResultList();
        em.close();
        return customers;
    }

    // Delete customer
    public void deleteCustomer(int id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Customer customer = em.find(Customer.class, id);
            if (customer != null) {
                em.remove(customer);
            }
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
