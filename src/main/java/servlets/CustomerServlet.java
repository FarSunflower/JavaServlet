package servlets;

import model.Customer;
import model.Orders;
import service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.OrderService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customer/*")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        // Initialize the CustomerService
        customerService = new CustomerService();
        orderService = new OrderService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Check if we are in the customer/new path
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.equals("/new")) {
            if ("addCustomer".equals(action)) {
                // Retrieve customer data from the request parameters
                String name = request.getParameter("name");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String image = request.getParameter("image");

                // Validate or process the data
                if (name == null || phone == null || address == null || image == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Missing required fields.");
                    return;
                }

                // Create a new Customer object
                Customer newCustomer = new Customer();
                newCustomer.setName(name);
                newCustomer.setPhone(phone);
                newCustomer.setAddress(address);
                newCustomer.setImage(image);
                newCustomer.setOrders(null);

                // Save the new customer using the CustomerService
                customerService.addCustomer(newCustomer);

                // Redirect to the customer list page (catalog/all)
                response.sendRedirect(request.getContextPath() + "/customer/all");
            } else {
                // Handle invalid action for the customer form
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid action.");
            }
        }
        if (pathInfo != null && pathInfo.matches("/\\d+/order/\\d+/edit")) {
            try {
                // Extract customer and order IDs
                String[] pathParts = pathInfo.split("/");
                int customerId = Integer.parseInt(pathParts[1]);
                int orderId = Integer.parseInt(pathParts[3]);

                // Parse updated order details
                double amount = Double.parseDouble(request.getParameter("amount"));
                String paymentMethod = request.getParameter("paymentMethod");

                if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
                    throw new IllegalArgumentException("Payment method is required.");
                }

                // Create updated order
                Orders updatedOrder = new Orders();
                updatedOrder.setId(orderId);
                updatedOrder.setAmount(amount);
                updatedOrder.setPaymentMethod(paymentMethod);

                // Update the order
                orderService.updateOrder(customerId, updatedOrder);

                response.sendRedirect(request.getContextPath() + "/customer/" + customerId + "/order/all");
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid amount format.");
            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(e.getMessage());
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
                response.getWriter().write("An error occurred while updating the order.");
            }
        } else if (pathInfo != null && pathInfo.matches("/\\d+/order")) {
            handleOrderCreation(request, response, pathInfo);
        }else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid endpoint.");
        }
    }

    private void handleOrderCreation(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws IOException {
        try {
            int customerId = extractCustomerId(pathInfo);
            double amount = Double.parseDouble(request.getParameter("amount"));
            String paymentMethod = request.getParameter("paymentMethod");

            if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
                throw new IllegalArgumentException("Payment method is required.");
            }

            Orders newOrder = new Orders(new Date(), amount, paymentMethod, null);
            customerService.addOrderForCustomer(customerId, newOrder);

            response.sendRedirect(request.getContextPath() + "/customer/" + customerId + "/order/all");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            response.getWriter().write("An error occurred while processing the order: " + e.getMessage());
        }
    }

    private int extractCustomerId(String pathInfo) {
        String[] pathParts = pathInfo.split("/");
        return Integer.parseInt(pathParts[1]);
    }



    // Handling GET requests for displaying customer list or specific customer details
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/all")) {
            // Retrieve all customers and forward to customer.jsp
            List<Customer> customers = customerService.getAllCustomersWithoutOrders();
            request.setAttribute("customers", customers);
            getServletContext().getRequestDispatcher("/customer.jsp").forward(request, response);
        } else if (pathInfo ==null || pathInfo.equals("/new")) {
            getServletContext().getRequestDispatcher("/customer_form.jsp").forward(request, response);
        } else if (pathInfo != null && pathInfo.matches("/\\d+/order/\\d+/edit"))  {
            // Extract customer and order IDs
            String[] pathParts = pathInfo.split("/");
            int customerId = Integer.parseInt(pathParts[1]);
            int orderId = Integer.parseInt(pathParts[3]);

            // Retrieve the specific order
            Orders order = orderService.getOrderById(customerId, orderId);

            if (order != null) {
                request.setAttribute("order", order);
                request.setAttribute("customerId", customerId);
                getServletContext().getRequestDispatcher("/edit_order.jsp").forward(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Order not found.");
            }
        } else if (pathInfo.matches("/\\d+/order/all")) {
            // Handle route for displaying all orders for a specific customer
            String[] pathParts = pathInfo.split("/");
            int customerId = Integer.parseInt(pathParts[1]);
            Customer customer = customerService.getCustomerByIdWithOrders(customerId);

            if (customer == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Customer not found.");
                return;
            }

            request.setAttribute("customer", customer);
            getServletContext().getRequestDispatcher("/customerId.jsp").forward(request, response);
        } else if (pathInfo.matches("/\\d+/order/new")) {
            // Handle route for displaying the form to create a new order for a specific customer
            String[] pathParts = pathInfo.split("/");
            int customerId = Integer.parseInt(pathParts[1]);
            Customer customer = customerService.getCustomerByIdWithOrders(customerId);

            if (customer == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Customer not found.");
                return;
            }

            request.setAttribute("customer", customer);
            getServletContext().getRequestDispatcher("/order_form.jsp").forward(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid request path\"}");
        }
    }
}
