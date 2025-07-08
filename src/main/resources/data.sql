-- sql file
CREATE TABLE clients (
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email VARCHAR(200)
);
CREATE TABLE sentiments (
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    texte VARCHAR(200),
    type VARCHAR(200),
    client_id INTEGER,
    CONSTRAINT client_fk FOREIGN KEY (client_id) REFERENCES clients(id)
);
