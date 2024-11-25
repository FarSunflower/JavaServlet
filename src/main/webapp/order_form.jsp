<%@ page import="model.Customer" %>
<%@ page import="model.Orders" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Create a New Order</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            color: #333;
        }
        .form-container {
            width: 50%;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            background-color: #f9f9f9;
            border-radius: 5px;
        }
        .form-container input[type="text"],
        .form-container input[type="number"],
        .form-container select {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .form-container input[type="submit"] {
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }
        .form-container input[type="submit"]:hover {
            background-color: #218838;
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

<%
    // Get the customer object from the request
    Customer customer = (Customer) request.getAttribute("customer");

    // Check if the customer exists
    if (customer != null) {
%>

    <h1>Create a New Order for <%= customer.getName() %></h1>

    <div class="form-container">
        <form action="<%= request.getContextPath() + "/customer/" + customer.getId() + "/order" %>" method="POST">
            <input type="hidden" name="action" value="createOrder"/>

            <!-- Order Amount -->
            <label for="amount">Amount:</label>
            <input type="number" id="amount" name="amount" step="0.01" required>

            <!-- Payment Method -->
            <label for="paymentMethod">Payment Method:</label>
            <select id="paymentMethod" name="paymentMethod" required>
                <option value="Credit Card">Credit Card</option>
                <option value="PayPal">PayPal</option>
                <option value="Cash">Cash</option>
            </select>

            <!-- Submit Button -->
            <input type="submit" value="Create Order"/>
        </form>
    </div>

    <!-- Back Button -->
    <a href="<%= request.getContextPath() + "/customer/" + customer.getId() + "/orders" %>" class="back-button">Back to Orders</a>

<%
    } else {
%>
    <p>Customer not found. Please make sure the customer is properly loaded.</p>
<%
    }
%>

</body>
</html>
