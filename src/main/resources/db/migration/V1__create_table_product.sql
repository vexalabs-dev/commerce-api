CREATE TABLE product (
    id          UUID PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description VARCHAR(500),
    sale_price  DECIMAL(10, 2) NOT NULL
)