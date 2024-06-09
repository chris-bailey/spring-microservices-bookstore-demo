CREATE TABLE IF NOT EXISTS stock_check (
    id SERIAL PRIMARY KEY,
    sku_code VARCHAR(255) NOT NULL,
    quantity INT NOT NULL
);