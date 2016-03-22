/* Drop existing tables */
DROP TABLE ARTICLE, RECEIPT, ARTICLE_RECEIPT;

/* create table 'ArticleDto' */
CREATE TABLE ARTICLE (
  id int not null AUTO_INCREMENT,
  name VARCHAR(255) not null,
  price double not null,
  description TEXT not null,
  image_path VARCHAR(255) NOT NULL,
  category VARCHAR(255) NOT NULL,
  visible BOOL NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id)
);

/* create table 'ReceiptDto' */
CREATE TABLE RECEIPT(
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  creation_date DATETIME not null,
  receiver VARCHAR(255) not null,
  receiver_adress VARCHAR(255) not null
);

CREATE TABLE ARTICLE_RECEIPT (
  receiptDto int not null,
  articleDto int not null,
  amount double not null,
  CONSTRAINT RECEIPT_FK FOREIGN KEY (receiptDto) REFERENCES RECEIPT (id),
  CONSTRAINT ARTICLE_FK FOREIGN KEY (articleDto) REFERENCES ARTICLE (id),
  primary key (receiptDto,articleDto)

);

/* Insert dummy data into articleDto
   Pull data from here: http://www.johnlewis.com/gifts/easter/c600003104?rdr=1 */
INSERT INTO ARTICLE (ID, NAME, DESCRIPTION, IMAGE_PATH, CATEGORY, PRICE)
VALUES (1, 'Kuschelente', 'Kuschelente, Dekorationsgegenstand. Weich und flauschig, und außerdem gelb!', './img/kuschelente.jpg', 'Dekoration', 18.90),
  (2, 'Glas-Osterei', 'Solides und wahnsinnig hübsches handgemaltes Osterei. Zum Aufhängen passt das in jede Bude.', './img/glasei.jpg', 'Dekoration', 2),
  (3, 'Schokoladen Henne', 'Köstliches Schokoladenhuhn. Verpackt in super glänzender Megafolie.', './img/osterhenne.jpg', 'Süßigkeiten', 7),
  (4, 'Riesenei', 'Ein gigantisches Schokoei. Falls man sich mal nur von Schokolade ernähren möchte.', './img/riesenei.jpg', 'Süßigkeiten', 22);



/* Insert dummy data into receiptDto */
INSERT INTO RECEIPT (ID, creation_date, receiver, receiver_adress)
VALUES (1, '2012-06-18 10:34:09', 'Bugs Bunny', 'Hinterwald 15, 111 Imaginationland'),
  (2, '2015-12-13 16:11:10', 'Elmer Fudd', 'Oberhausen 9, 23 Jägersdorf');

/* insert dummy data into join table */
INSERT INTO ARTICLE_RECEIPT (receiptDto, articleDto, amount)
VALUES (1,3,1),
 (1,2,1),
 (2,1,4);
