/* Drop existing tables */
DROP TABLE ARTICLE, RECEIPT;

/* Create table 'Article' */
CREATE TABLE ARTICLE (
  id int not null PRIMARY KEY,
  name VARCHAR(255) not null,
  price double not null,
  description TEXT not null,
  image_path VARCHAR(255) NOT NULL,
  category VARCHAR(255) NOT NULL
);

/* Create table 'Receipt' */
CREATE TABLE RECEIPT(
  ID INT NOT NULL,
  article VARCHAR(255) not null,
  article_amount int not null,
  creation_date DATETIME not null,
  receiver VARCHAR(255) not null,
  receiver_adress VARCHAR(255) not null,
  primary key (id, article),
);


/* Insert dummy data into article
   Pull data from here: http://www.johnlewis.com/gifts/easter/c600003104?rdr=1 */
INSERT INTO ARTICLE (ID, NAME, DESCRIPTION, IMAGE_PATH, CATEGORY, PRICE)
VALUES (1, 'Kuschelente', 'Kuschelente, Dekorationsgegenstand. Weich und flauschig, und außerdem gelb!', '', 'Dekoration', 5);
INSERT INTO ARTICLE (ID, NAME, DESCRIPTION, IMAGE_PATH, CATEGORY, price)
VALUES (2, 'Glas-Osterei', 'Solides und wahnsinnig hübsches handgemaltes Osterei. Zum Aufhängen passt das in jede Bude.', '', 'Dekoration', 2);
INSERT INTO ARTICLE (ID, NAME, DESCRIPTION, IMAGE_PATH, CATEGORY, price)
VALUES (3, 'Schokoladen Henne', 'Köstliches Schokoladenhuhn. Verpackt in super glänzender Megafolie.', '', 'Süßigkeiten', 7);


/* Insert dummy data into receipt */
