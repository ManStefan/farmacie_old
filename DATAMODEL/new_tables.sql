USE farmacie;

CREATE TABLE categorii_ale_atributelor(	id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
								       	nume VARCHAR(50) NOT NULL UNIQUE,
				                        creeat_de VARCHAR(100),
				                        modificat_de VARCHAR(100),
				                        data_creeare DATE,
				                        data_modificare DATE);
                        
CREATE TABLE atribute (	id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
					   	id_categorii_ale_atributelor INT, FOREIGN KEY (id_categorii_ale_atributelor) references categorii_ale_atributelor(id) ON DELETE CASCADE,
                       	nume VARCHAR(50) NOT NULL UNIQUE,
                       	creeat_de VARCHAR(100),
                        modificat_de VARCHAR(100),
                        data_creeare DATE,
                        data_modificare DATE);
						 								
CREATE TABLE etaloane (id  INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
					   nume VARCHAR(50) NOT NULL UNIQUE,
					   creeat_de VARCHAR(100),
                       modificat_de VARCHAR(100),
                       data_creeare DATE,
                       data_modificare DATE);
							 
					 
CREATE TABLE categorii (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
						nume VARCHAR(50) NOT NULL UNIQUE,
						nivel INT NOT NULL,
						frunza INT NOT NULL,
						creeat_de VARCHAR(100),
                        modificat_de VARCHAR(100),
                        data_creeare DATE,
                        data_modificare DATE);

CREATE TABLE arbore_categorii (	id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
								id_cat_parinte INT NOT NULL, FOREIGN KEY (id_cat_parinte) references categorii(id) ON DELETE CASCADE,
							   	id_cat_copil INT NOT NULL, FOREIGN KEY (id_cat_copil) references categorii(id) ON DELETE CASCADE);
								
CREATE TABLE producatori (	id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
						 	nume VARCHAR(50) NOT NULL UNIQUE,
						 	creeat_de VARCHAR(100),
                        	modificat_de VARCHAR(100),
                        	data_creeare DATE,
                        	data_modificare DATE);
                        	
CREATE TABLE produse (	id  INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
						nume VARCHAR(50) NOT NULL UNIQUE,
                        pret DECIMAL(10,2) NOT NULL,
						cantitate INT NOT NULL,     
                        descriere VARCHAR(5000),
                        id_categorie INT NOT NULL, FOREIGN KEY (id_categorie) references categorii(id),
                        id_etalon INT NOT NULL, FOREIGN KEY (id_etalon) references etaloane(id),
                        id_producator INT NOT NULL, FOREIGN KEY (id_producator) references producatori(id),
                        creeat_de VARCHAR(100),
                        modificat_de VARCHAR(100),
                        data_creeare TIMESTAMP,
                        data_modificare TIMESTAMP);   
                        
CREATE TABLE atribute_produse (	id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
								id_prod INT  , FOREIGN KEY (id_prod) references produse(id) ON DELETE CASCADE,
                               	id_atribut INT , FOREIGN KEY (id_atribut) references atribute(id) ON DELETE CASCADE);
                        
								
CREATE TABLE poze (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
				   id_prod INT, FOREIGN KEY (id_prod) references produse(id) ON DELETE CASCADE,
				   descriere VARCHAR(80),
				   poza VARCHAR(100),
				   creeat_de VARCHAR(100),
                   modificat_de VARCHAR(100),
                   data_creeare DATE,
                   data_modificare DATE);  
                   
CREATE TABLE admin (id INT(10) NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), 
					user VARCHAR(45) NOT NULL, 
					password VARCHAR(45) NOT NULL,
					enabled tinyint(1) NOT NULL);  
					
CREATE TABLE admin_roles(id INT(10) NOT NULL AUTO_INCREMENT, PRIMARY KEY(id),
                         authority VARCHAR(45) NOT NULL,
						 id_admin INT, FOREIGN KEY (id_admin) references admin(id)
);					
