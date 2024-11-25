<%@ page import="model.Orders" %>
<html>
<head>
    <title>Edit Order</title>
    <style>
        .form-group {
            margin: 10px 0;
        }
        .submit-button {
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            font-size: 16px;
            cursor: pointer;
        }
        .submit-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <%
        Orders order = (Orders) request.getAttribute("order");
        int customerId = (Integer) request.getAttribute("customerId");
        if (order != null) {
    %>
    <h1>Edit Order</h1>
    <form action="<%= request.getContextPath() + "/customer/" + customerId + "/order/" + order.getId() + "/edit" %>" method="POST">
        <div class="form-group">
            <label for="amount">Amount:</label>
            <input type="text" id="amount" name="amount" value="<%= order.getAmount() %>" required>
        </div>
        <div class="form-group">
            <label for="paymentMethod">Payment Method:</label>
            <input type="text" id="paymentMethod" name="paymentMethod" value="<%= order.getPaymentMethod() %>" required>
        </div>
        <button type="submit" class="submit-button">Save Changes</button>
    </form>
    <%
        } else {
    %>
    <p>Order not found.</p>
    <%
        }
    %>
</body>
</html>
