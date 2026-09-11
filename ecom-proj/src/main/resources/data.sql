INSERT INTO product (id, name, description, brand, price, category, release_date, product_available, stock_quantity)
VALUES
    (1, 'iPhone duo', 'Latest Apple smartphone with titanium body and A17 chip', 'Apple', 331199.99, 'Mobile', '2023-09-22', true, 50),
    (2, 'Samsung Galaxy S25 Ultra', 'Premium Android phone with AI features and S-Pen', 'Samsung', 81299.99, 'Mobile', '2024-01-31', true, 35),
    (3, 'Sony WH-1000XM5', 'Industry-leading noise cancelling wireless headphones', 'Sony', 3399.99, 'Headphone', '2023-05-15', true, 100),
    (4, 'Nike Air Max 270', 'Comfortable running shoes with visible air cushioning', 'Nike', 10149.99, 'Fashion', '2023-11-01', true, 200),
    (5, 'Dell XPS 16', 'High-performance laptop with Intel Core Ultra 9 processor', 'Dell', 91899.99, 'Laptop', '2024-02-14', false, 0);

ALTER TABLE product ALTER COLUMN id RESTART WITH 6; 