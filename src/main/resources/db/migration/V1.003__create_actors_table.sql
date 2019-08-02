CREATE TABLE actors (
    id          INT NOT NULL AUTO_INCREMENT,
    external_id INT NOT NULL,
    name        VARCHAR(255),
    image       VARCHAR(512),
    PRIMARY KEY (id)
  );

CREATE TABLE movie_actor(
    movie_id INT NOT NULL,
    actor_id INT NOT NULL,
    PRIMARY KEY (movie_id, actor_id),
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (actor_id) REFERENCES actors(id)
);

CREATE UNIQUE INDEX actors_external_id_uix ON actors(external_id);