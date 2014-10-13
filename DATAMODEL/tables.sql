USE farmacie;

CREATE TABLE admin (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), 
					user VARCHAR(30) NOT NULL, 
					password VARCHAR(30) NOT NULL);

CREATE TABLE produse (id  INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
						nume VARCHAR(50) NOT NULL UNIQUE,
                        pret DECIMAL(10,2) NOT NULL,
						cantitate INT NOT NULL,     
                        data_inreg DATE NOT NULL,
                        descriere VARCHAR(200));
                        
CREATE TABLE categorii_ale_atributelor(id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
								       nume VARCHAR(50) NOT NULL UNIQUE);
                        
CREATE TABLE atribute (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
					   id_categorii_ale_atributelor INT, FOREIGN KEY (id_categorii_ale_atributelor) references categorii_ale_atributelor(id) ON DELETE CASCADE,
                       nume VARCHAR(50) NOT NULL UNIQUE);
						 
CREATE TABLE atribute_produse (id_prod INT  , FOREIGN KEY (id_prod) references produse(id) ON DELETE CASCADE,
                               id_atribut INT , FOREIGN KEY (id_atribut) references atribute(id) ON DELETE CASCADE,
                               PRIMARY KEY (id_prod, id_atribut));
								
CREATE TABLE etaloane (id  INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
					   nume VARCHAR(50) NOT NULL UNIQUE);
						 
CREATE TABLE etaloane_produse (id_prod INT, FOREIGN KEY (id_prod) references produse(id) ON DELETE CASCADE,
                               id_etalon INT, FOREIGN KEY (id_etalon) references etaloane(id) ON DELETE CASCADE,
                               PRIMARY KEY(id_prod, id_etalon));
							   
CREATE TABLE poze (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
				   id_prod INT, FOREIGN KEY (id_prod) references produse(id) ON DELETE CASCADE,
				   descriere VARCHAR(80),
				   poza VARCHAR(100));
					 
CREATE TABLE categorii (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
						nume VARCHAR(50) NOT NULL UNIQUE,
						nivel INT NOT NULL,
						frunza INT NOT NULL);

CREATE TABLE arbore_categorii (id_cat_parinte INT NOT NULL, FOREIGN KEY (id_cat_parinte) references categorii(id) ON DELETE CASCADE,
							   id_cat_copil INT NOT NULL, FOREIGN KEY (id_cat_copil) references categorii(id) ON DELETE CASCADE,
							   PRIMARY KEY(id_cat_parinte, id_cat_copil));
								 
CREATE TABLE categorii_produse (id_cat INT, FOREIGN KEY (id_cat) references categorii(id) ON DELETE CASCADE,
								id_prod INT, FOREIGN KEY (id_prod) references produse(id) ON DELETE CASCADE,
								PRIMARY KEY(id_cat, id_prod));
								
CREATE TABLE producatori (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
						 nume VARCHAR(50) NOT NULL UNIQUE);
						 
CREATE TABLE producatori_produse(id_producator INT, FOREIGN KEY (id_producator) references producatori(id) ON DELETE CASCADE,
								 id_produs INT , FOREIGN KEY (id_produs) references produse(id) ON DELETE CASCADE,
								 PRIMARY KEY(id_producator, id_produs));
							
CREATE TABLE statistici (
					versiune_meniu INT
				)								 
