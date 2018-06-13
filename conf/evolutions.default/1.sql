# CAT schema

# --- !Ups

CREATE TABLE CAT (
    NAME varchar(255) NOT NULL,
    COLOR varchar(255) NOT NULL,
    PRIMARY KEY (NAME)
);

# --- !Downs

DROP TABLE CAT;