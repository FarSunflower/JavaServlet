package service;

import dao.OrderDAO;
import model.Orders;

import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO(); // Initialize DAO
    }

    // Create a new order
    public void createOrder(Orders order) {
        if (orderDAO == null) {
            throw new IllegalStateException("OrderDAO is not initialized.");
        }
        orderDAO.saveOrder(order); // Persist order using DAO
    }

    // Update an existing order
    public void updateOrder(int customerId, Orders updatedOrder) {
        Orders existingOrder = getOrderById(customerId, updatedOrder.getId());
        if (existingOrder != null) {
            existingOrder.setAmount(updatedOrder.getAmount());
            existingOrder.setPaymentMethod(updatedOrder.getPaymentMethod());
            orderDAO.updateOrder(existingOrder);
        } else {
            throw new IllegalArgumentException("Order not found or does not belong to the customer.");
        }
    }

    // Retrieve a specific order by customer ID and order ID
    public Orders getOrderById(int customerId, int orderId) {
        Orders order = orderDAO.getOrderById(orderId);
        if (order != null && order.getCustomer().getId() == customerId) {
            return order;
        }
        return null; // Order not found or does not belong to the customer
    }

    // Retrieve all orders by a specific customer ID
    public List<Orders> getOrdersByCustomerId(int customerId) {
        return orderDAO.getOrdersByCustomerId(customerId);
    }
}
