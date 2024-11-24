package service;

import model.Customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

public class CustomerService {
    private List<Customer> customers;

    public CustomerService(String filePath) {
        this.customers = loadCustomersFromFile(filePath);
    }
    // Load customers from a JSON file
    private List<Customer> loadCustomersFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the list of customers from the file and map them to Customer objects
            Customer[] customerArray = objectMapper.readValue(new File(filePath), Customer[].class);
            return new ArrayList<>(Arrays.asList(customerArray));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    public CustomerService(List<Customer> customers) {
        this.customers = customers;
    }

    // Add a customer to the list
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // Return all customers without their orders
    public List<Customer> getAllCustomersWithoutOrders() {
        List<Customer> customersWithoutOrders = new ArrayList<>();
        for (Customer customer : customers) {
            customersWithoutOrders.add(new Customer(
                    customer.getId(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getAddress(),
                    customer.getImage(),
                    null // Exclude orders
            ));
        }
        return customersWithoutOrders;
    }

    // Return a customer by ID with all their orders
    public Customer getCustomerByIdWithOrders(int id) {
        Optional<Customer> customer = customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst();

        return customer.orElse(null); // Return null if no customer found
    }
}
