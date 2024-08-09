CREATE TABLE _user
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    _name    NVARCHAR(100),
    email    VARCHAR(255),
    password NVARCHAR(255)
);

CREATE TABLE role
(
    code  VARCHAR(255) PRIMARY KEY NOT NULL,
    _name NVARCHAR(255)
);

CREATE TABLE user_role
(
    user_id   BIGINT NOT NULL,
    role_code VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES _user (id),
    FOREIGN KEY (role_code) REFERENCES role (code),
    PRIMARY KEY (user_id, role_code)
);

CREATE TABLE product
(
    id       BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL UNIQUE,
    price    DECIMAL NOT NULL,
    quantity DECIMAL NOT NULL
);

CREATE TABLE _order
(
    id         VARCHAR(255) NOT NULL PRIMARY KEY,
    status     VARCHAR(30) NOT NULL,
    total_price DECIMAL NULL
);

CREATE TABLE order_product
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    order_id      VARCHAR(255) NOT NULL,
    product_id    BIGINT NULL,
    product_name    VARCHAR(255) NOT NULL,
    quantity     INT NULL,
    price_per_unit DECIMAL NULL,
    subtotal     DECIMAL NULL,
    CONSTRAINT order_fk FOREIGN KEY (order_id) REFERENCES _order (id)
);

CREATE TABLE validation_error
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    order_id      VARCHAR(255) NOT NULL,
    error_message VARCHAR(255) NOT NULL,
    CONSTRAINT order_error_fk FOREIGN KEY (order_id) REFERENCES _order (id)
);
