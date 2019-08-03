CREATE TABLE episodes (
    id          INT NOT NULL AUTO_INCREMENT,
    external_id INT NOT NULL,
    name        VARCHAR(255),
    season      INT,
    number      INT,
    airdate     DATE,
    watched     BOOLEAN NOT NULL DEFAULT FALSE,
    show_id    INT,
    PRIMARY KEY (id),
    FOREIGN KEY (show_id) REFERENCES shows(id)
  );

CREATE UNIQUE INDEX episodes_external_id_uix ON episodes(external_id);