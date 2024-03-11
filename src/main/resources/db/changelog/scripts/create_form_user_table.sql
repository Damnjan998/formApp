CREATE TABLE IF NOT EXISTS `form_user` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    form_id INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    user_id INT,
    last_user_to_modify INT,
    FOREIGN KEY (form_id) REFERENCES form(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);