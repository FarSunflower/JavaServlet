<%@ page import="model.Customer" %>
<html>
<head>
    <title>Customer Details</title>
    <style>
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
    </style>
</head>
<body>
    <h1>Customer Details</h1>

    <%
        Customer customer = (Customer) request.getAttribute("customer");
        if (customer != null) {
    %>

    <div class="group">
        <h3>ID: <%= customer.getId() %></h3>
        <h3>Name: <%= customer.getName() %></h3>
        <h3>Phone: <%= customer.getPhone() %></h3>
        <h3>Address: <%= customer.getAddress() %></h3>
        <h3>Image: <img src="<%= customer.getImage() %>" alt="Customer Image" style="max-width: 150px; height: auto;"></h3>

        <h4>Orders:</h4>
        <%
            if (customer.getOrders() != null && !customer.getOrders().isEmpty()) {
                // Find the order with the highest amount
                double maxAmount = Double.MIN_VALUE;
                for (model.Order order : customer.getOrders()) {
                    if (order.getAmount() > maxAmount) {
                        maxAmount = order.getAmount();
                    }
                }
        %>
            <ul>
            <%
                // Now render each order, marking the one with the highest amount as bold
                for (model.Order order : customer.getOrders()) {
                    String styleClass = order.getAmount() == maxAmount ? "bold" : "";
            %>
                <li class="<%= styleClass %>">
                    Order ID: <%= order.getId() %>,
                    Date: <%=order.getDate() %>,
                    Amount: <%= order.getAmount() %>,
                    Payment Method: <%= order.getPaymentMethod() %>
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

    <%-- "Back" button that redirects to /customer/all --%>
    <a href="<%= request.getContextPath() + "/customer/all" %>" class="back-button">Back</a>

    <%
        } else {
    %>
        <p>Customer not found.</p>
    <%
        }
    %>
</body>
</html>
