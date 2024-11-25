<%@ page import="model.Customer" %>
<%@ page import="model.Orders" %>
<html>
<head>
    <title>Customer Orders</title>
    <style>
        /* Your existing styles */
        .group {
            padding: 10px;
            margin: 10px;
        }
        .bold {
            font-weight: bold;
        }
        .back-button {
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            text-decoration: none;
            font-size: 16px;
            cursor: pointer;
        }
        .back-button:hover {
            background-color: #0056b3;
        }
        .order-button {
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            font-size: 16px;
            cursor: pointer;
        }
        .order-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

    <%
        Customer customer = (Customer) request.getAttribute("customer");
        if (customer != null) {
    %>
        <div class="group">
            <h1>Customer Details</h1>
            <h3>ID: <%= customer.getId() %></h3>
            <h3>Name: <%= customer.getName() %></h3>
            <h3>Phone: <%= customer.getPhone() %></h3>
            <h3>Address: <%= customer.getAddress() %></h3>
            <h3>Image: <img src="<%= customer.getImage() %>" alt="Customer Image" style="max-width: 150px; height: auto;"></h3>
        </div>

        <!-- Displaying Orders -->
        <div class="group">
             <h4>Orders:</h4>
                    <%
                        if (customer.getOrders() != null && !customer.getOrders().isEmpty()) {
                            // Find the order with the highest amount
                            double maxAmount = Double.MIN_VALUE;
                            for (model.Orders order : customer.getOrders()) {
                                if (order.getAmount() > maxAmount) {
                                    maxAmount = order.getAmount();
                                }
                            }
                    %>
                        <ul>
                        <%
                            // Now render each order, marking the one with the highest amount as bold
                            for (model.Orders order : customer.getOrders()) {
                                String styleClass = order.getAmount() == maxAmount ? "bold" : "";
                        %>
                            <li class="<%= styleClass %>">
                                Order ID: <%= order.getId() %>,
                                Date: <%=order.getDate() %>,
                                Amount: <%= order.getAmount() %>,
                                Payment Method: <%= order.getPaymentMethod() %>
                                <!-- Edit Order Button -->
                                <form action="<%= request.getContextPath() + "/customer/" + customer.getId() + "/order/" + order.getId() + "/edit" %>" method="GET" style="display: inline;">
                                    <button type="submit" class="order-button">Edit</button>
                                </form>
                            </li>
                        <%
                            }
                        %>
                        </ul>
                    <%
                        } else {
                    %>
                        <p>No orders found.</p>
                    <%
                        }
                    %>
        </div>

        <!-- Button to create a new order -->
        <form action="<%= request.getContextPath() + "/customer/" + customer.getId() + "/order/new" %>" method="GET">
            <input type="submit" value="Create New Order" class="order-button"/>
        </form>

        <!-- Back button -->
        <a href="<%= request.getContextPath() + "/customer/all" %>" class="back-button">Back</a>

    <%
        } else {
    %>
        <p>Customer not found. Please make sure the customer is properly loaded.</p>
    <%
        }
    %>
</body>
</html>
