<form method="POST" action="/customer/new">
    <input type="hidden" name="action" value="addCustomer"/>

    Name: <input type="text" name="name" required/><br/>
    Phone: <input type="text" name="phone" required/><br/>
    Address: <input type="text" name="address" required/><br/>
    Image: <input type="text" name="image" required/><br/>

    <input type="submit" value="Add Customer"/>
</form>
