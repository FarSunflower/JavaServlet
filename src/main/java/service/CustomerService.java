package service;

import dao.CustomerDAO;
import dao.OrderDAO;
import model.Customer;
import model.Orders;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private CustomerDAO customerDAO;
    private OrderService orderService;

    public CustomerService() {

        this.customerDAO = new CustomerDAO(); // Use the DAO to interact with the database
        this.orderService = new OrderService();
    }

    // Add a customer to the database
    public void addCustomer(Customer customer) {
        customerDAO.saveCustomer(customer);
    }

    // Return all customers from the database without their orders
    public List<Customer> getAllCustomersWithoutOrders() {
        return customerDAO.getAllCustomers(); // Assuming DAO fetches customers without orders
    }

    // Return a customer by ID with all their orders
    public Customer getCustomerByIdWithOrders(int id) {
        return customerDAO.getCustomerByIdWithOrders(id);
    }
    public void addOrderForCustomer(int customerId, Orders newOrder) {
        try {
            Customer customer = customerDAO.getCustomerById(customerId);
            if (customer == null) {
                throw new IllegalArgumentException("Customer not found.");
            }

            // Ensure the orders list is initialized
            if (customer.getOrders() == null) {
                customer.setOrders(new ArrayList<>());
            }

            // Add the order to the customer's list
            customer.getOrders().add(newOrder);
            newOrder.setCustomer(customer); // Set customer reference in the order

            // Save the order using OrderService
            orderService.createOrder(newOrder);
        } catch (Exception e) {
            System.err.println("Error adding order: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error saving order.");
        }
    }
    // Example method to retrieve a customer by ID
    public Customer getCustomerById(int customerId) {
        // Implement database retrieval logic or in-memory logic
        // For example, return some customer based on the ID
        return new Customer();  // Return a dummy customer for now
    }

    // Example method to update the customer in the database
    public void updateCustomer(Customer customer) {
        // Implement logic to update the customer in the database
    }
}
