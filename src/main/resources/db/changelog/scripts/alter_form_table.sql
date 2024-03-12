ALTER TABLE `form`
    ADD COLUMN created_by_user_id INT,
    ADD COLUMN last_updated_by_user_id INT,
    ADD CONSTRAINT fk_created_by_user FOREIGN KEY (created_by_user_id) REFERENCES user(id)