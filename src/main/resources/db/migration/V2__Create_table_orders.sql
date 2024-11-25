CREATE TABLE orders (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(255),
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);