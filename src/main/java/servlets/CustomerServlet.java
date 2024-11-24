package servlets;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import service.CustomerService;

import java.util.*;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customer/*")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        // Initialize the CustomerService with the path to your JSON file
        customerService = new CustomerService("D:\\java\\JavaServletDemo\\src\\main\\webapp\\file.json");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("The Post request has been made to /customer");
        // Handle POST request if needed (for adding customers, etc.)
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();  // Get the path info (e.g., /all or /{id})

        if (pathInfo == null || pathInfo.equals("/all")) {
            // Retrieve all customers from the service
            List<Customer> customers = customerService.getAllCustomersWithoutOrders();

            // Set the customers as a request attribute
            request.setAttribute("customers", customers);

            // Forward the request to the JSP page for rendering the customer list
            getServletContext().getRequestDispatcher("/customer.jsp").forward(request, response);
        } else if (pathInfo.matches("/\\d+/order/all")) {  // Route pattern to forward to OrderServlet
            // Handle /customer/{id}/order/all route
            String[] pathParts = pathInfo.split("/");  // Split the path to extract the ID
            int customerId = Integer.parseInt(pathParts[1]);  // Extract the customer ID from the URL

            // Forward the request to OrderServlet
            request.setAttribute("customerId", customerId);  // Pass the customer ID to OrderServlet
            getServletContext().getRequestDispatcher("/order/" + customerId + "/all").forward(request, response);
        } else {
            // Handle invalid routes (e.g., /catalog/xyz/order/all)
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid request path\"}");
        }
    }
}
