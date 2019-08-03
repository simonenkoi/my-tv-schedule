CREATE SEQUENCE hibernate_sequence;

CREATE TABLE shows (
    id          INT NOT NULL AUTO_INCREMENT,
    external_id INT NOT NULL,
    name        VARCHAR(255),
    image       VARCHAR(512),
    PRIMARY KEY (id)
  );

CREATE UNIQUE INDEX shows_external_id_uix ON shows(external_id);
