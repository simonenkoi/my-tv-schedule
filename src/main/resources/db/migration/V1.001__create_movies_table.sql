CREATE SEQUENCE hibernate_sequence;

CREATE TABLE movies (
    id          INT NOT NULL AUTO_INCREMENT,
    external_id INT NOT NULL,
    name        VARCHAR(255),
    image       VARCHAR(512),
    PRIMARY KEY (id)
  );

CREATE UNIQUE INDEX movies_external_id_uix ON movies(external_id);
