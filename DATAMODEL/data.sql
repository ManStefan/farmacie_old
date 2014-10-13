C:\Program Files (x86)\MySQL\MySQL Server 5.5\bin>mysqldump.exe -c -u root farmacie arbore_categorii > D:\arbore_categorii.dmp

INSERT INTO `arbore_categorii` VALUES (1,4),(1,5),(2,8),(3,9),(5,6),(5,7),(9,10),(9,12),(9,13),(10,11),(13,14),(14,15);

INSERT INTO `atribute` VALUES (1, NULL, 'verde'),(3, NULL, 'rosu');

INSERT INTO `produse` VALUES (1,'prod1','25.67',10,'2011-02-19',NULL),(2,'prod2','34',5,'2011-02-26',NULL),(3,'prod3','6',0,'2011-02-26',NULL),(4,'prod4','2',9,'2011-02-22',NULL),(5,'prod5','8',3,'2011-02-26',NULL),(6,'prod6','2',12,'2011-02-26',NULL),(7,'prod7','1',2,'2011-02-02',NULL),(8,'prod8','76',34,'2011-02-26',NULL),(9,'prod9','234',875,'2011-01-26',NULL),(10,'prod10','23',3,'2011-02-26',NULL);

INSERT INTO `atribute_produse` VALUES (1,1),(1,3);

INSERT INTO `categorii` VALUES (1,'unu',1,0),(2,'doi',1,0),(3,'trei',1,0),(4,'patru',2,1),(5,'cinci',2,0),(6,'sase',3,1),(7,'sapte',3,1),(8,'opt',2,1),(9,'noua',2,0),(10,'zece',3,0),(11,'unsprezece',4,0),(12,'doisprezece',3,1),(13,'treisprezece',3,0),(14,'paisprezece',4,0),(15,'cincisprezece',5,1);

INSERT INTO `categorii_produse` VALUES (1,1),(4,1),(1,2),(5,2),(6,2),(1,3),(5,3),(6,3),(3,4),(9,4),(10,4),(11,4),(3,5),(9,5),(10,5),(11,5),(3,6),(9,6),(12,6),(3,7),(9,7),(12,7),(3,8),(9,8),(12,8),(3,9),(9,9),(13,9),(14,9),(15,9),(3,10),(9,10),(13,10),(14,10),(15,10);

INSERT INTO `etaloane` VALUES (1,'diametru');

INSERT INTO `etaloane_produse` VALUES (1,1);

INSERT INTO `producatori` VALUES (1,'Producator 1');

INSERT INTO `producatori_produse` VALUES (1,1);

INSERT INTO `arbore_categorii` (`id_cat_parinte`, `id_cat_copil`) VALUES (1,4),(1,5),(1,16),(1,17),(1,35),(1,39),(1,51),(1,59),(1,74),(2,8),(3,9),(4,37),(5,6),(5,7),(9,10),(9,12),(9,13),(10,11),(13,14),(14,15),(37,42),(37,49),(39,41),(42,50),(42,80),(43,44),(43,45),(43,46),(64,65),(76,78),(76,79);