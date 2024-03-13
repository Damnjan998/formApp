CREATE TABLE IF NOT EXISTS `field_user` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    form_user_id INT,
    field_id INT,
    text_value VARCHAR(255),
    number_value DOUBLE(10,2),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    user_id INT,
    last_user_to_modify INT,
    FOREIGN KEY (form_user_id) REFERENCES form(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (field_id) REFERENCES field(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);