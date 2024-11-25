<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>

<html>
<head>
    <title>Customers</title>
    <style>
        .group {
            border: solid blue 2px;
            padding: 10px;
            float: left;
            margin: 10px;
        }

        img {
            cursor: pointer; /* Change cursor to indicate it's clickable */
        }
    </style>
</head>
<body>
    <h1>Here are some of the customers</h1>

    <%-- Check if there is an error variable, if so, show it --%>
    <h5 style="color: red"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></h5>
    <hr>

    <%-- Button to go to the new customer form --%>
    <form action="<%= request.getContextPath() + "/customer/new" %>" method="GET">
        <button type="submit">Add New Customer</button>
    </form>

    <%-- Loop through the list of customers --%>
    <%
        List<Customer> customers = (List<Customer>) request.getAttribute("customers");
        if (customers != null && !customers.isEmpty()) {
            for (Customer customer : customers) {
    %>
        <div class="group">
            <h5>Customer:</h5>
            <h3><%= customer.getId() %></h3>
            <h3><%= customer.getName() %></h3>
            <h3><%= customer.getPhone() %></h3>
            <h3><%= customer.getAddress() %></h3>
            <a href="<%= request.getContextPath() + "/customer/" + customer.getId() + "/order/" + "all"%>">
                <img src="<%= customer.getImage() %>" alt="Customer Image" style="max-width: 150px; height: auto;">
            </a>
        </div>
    <%
            }
        } else {
    %>
        <h3>No customers found.</h3>
    <%
        }
    %>

</body>
</html>
