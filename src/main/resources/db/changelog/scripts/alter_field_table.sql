ALTER TABLE `field`
    ADD COLUMN created_by_user_id INT,
    ADD COLUMN last_updated_by_user_id INT,
    ADD CONSTRAINT created_by_user FOREIGN KEY (created_by_user_id) REFERENCES user(id) ON DELETE NO ACTION