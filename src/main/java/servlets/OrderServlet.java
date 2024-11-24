package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import service.CustomerService;

import java.io.*;
import java.util.*;

@WebServlet(name = "OrderServlet", urlPatterns = "/order/*")
public class OrderServlet extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        // Initialize the CustomerService with the path to your JSON file
        customerService = new CustomerService("D:\\java\\JavaServletDemo\\src\\main\\webapp\\file.json");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("The Post request has benn made to /order");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("The Get request has benn made to /order");

// Extract the customerId passed in the URL
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");  // Split to extract customer ID
        int customerId = Integer.parseInt(pathParts[1]);  // Get customer ID from URL
        Customer customer = customerService.getCustomerByIdWithOrders(customerId);
        // Retrieve customer orders and prepare the response
        // (You can implement logic to retrieve orders for the customer here)
        if (customer != null) {
            // Set the customer as a request attribute to be used in JSP
            request.setAttribute("customer", customer);

        // Forward the request to the JSP page for rendering the customer details and orders
            getServletContext().getRequestDispatcher("/customerId.jsp").forward(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Customer not found
            response.getWriter().write("{\"error\": \"Customer not found\"}");
        }
    }
}
