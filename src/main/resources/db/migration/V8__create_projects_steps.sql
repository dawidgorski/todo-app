create table project_steps
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    description      VARCHAR(100),
    project_id       INT,
    project_steps_id INT,
    FOREIGN KEY (project_id) references projects (id)
);