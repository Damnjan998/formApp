CREATE TABLE IF NOT EXISTS `field` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    form_id INT,
    name VARCHAR(255),
    order_display INT,
    type VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (form_id) REFERENCES form(id)
);