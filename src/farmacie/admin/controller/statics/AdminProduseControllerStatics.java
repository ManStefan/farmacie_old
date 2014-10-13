package farmacie.admin.controller.statics;

public interface AdminProduseControllerStatics {
	public static final String ADMIN_PRODUSE_VIEW="adminProduse";
	public static final String ADMIN_EDITARE_PRODUSE_VIEW="adminEditareProduse";
	public static final String ATRIBUTE_FOR_PRODUCT_VIEW="atributeForProduct";
	public static final String IMAGE_FOR_PRODUCT_VIEW="imageForProduct";
	public static final String ADAUGA_PRODUS_NOU_VIEW="adaugaProdusNou";
	public static final String UPDATE_PRODUS_NOU_VIEW="modificaProdus";
	public static final String ADMIN_STERGE_PRODUS_VIEW="stergeProdus";
	
	public static final String NR_OF_ATTRIBUTES_IN_PAGE = "nr_of_attributes_in_page";
	public static final String NR_OF_IMAGES_IN_PAGE = "nr_of_images_in_page";
	
	public static final String NEW_PRODUS_NAME = "newProdusName";
	public static final String NEW_PRODUS_DESCRIERE = "newProdusDescriere";
	public static final String NEW_PRODUS_PRET = "newProdusPret";
	public static final String NEW_PRODUS_CANTITATE = "newProdusCantitate";
	public static final String NEW_PRODUS_CATEGORIE = "selectedCategorieId";
	public static final String NEW_PRODUS_PRODUCATOR = "selectedProducatorId";
	public static final String NEW_PRODUS_ETALON = "selectedEtaloaneId";
	
	public static final String MOD_PRODUS_NAME = "selProdusName";
	public static final String MOD_PRODUS_DESCRIERE = "selProdusDescriere";
	public static final String MOD_PRODUS_PRET = "selProdusPret";
	public static final String MOD_PRODUS_CANTITATE = "selProdusCantitate";
	public static final String MOD_PRODUS_CATEGORIE = "selectedCategorieId2";
	public static final String MOD_PRODUS_PRODUCATOR = "selectedProducatorId1";
	public static final String MOD_PRODUS_ETALON = "selectedEtaloaneId1";	
	
	public static final String VALIDEAZA_PRODUS_VIEW = "valideazaProdus";

	public static final String DUPLICATE_PRODUCT_MESSAGE = "Produsul cu acest nume se alfa deja in Baza de Date!";
	public static final String DUPLICATE_ATTRS_MESSAGE = "Aveti doua sau mai multe atribute identice!";
	public static final String VALIDATE_PRODUCT_OK = "OK";
	public static final String VALIDATE_PRODUCT_ERROR = "validate_product_error";
	
	public static final String ADD_NEW_PRODUCT_ERROR = "add_new_product_error";
	public static final String UPDATE_PRODUCT_ERROR = "update_product_error";
	public static final String STERGE_PRODUS_ERROR = "sterge_produs_error";
	public static final String STERGE_PRODUS_SYSTEM_ERROR_LABEL = "A avut loc o eroare de sistem in momentul stergerii produslui!";
	public static final String ADD_NEW_PRODUCT_SYSTEM_ERROR_LABEL = "A avut loc o eroare de sistem in momentul adaugarii noului produs!";
	public static final String UPDATE_PRODUCT_SYSTEM_ERROR_LABEL = "A avut loc o eroare de sistem in momentul modificarii noului produs!";
	public static final String ADD_NEW_PRODUCT_DUPLICATE_ERROR_LABEL = "Eroare la adaugare produs: produsul este deja in baza de date!";
	public static final String UPDATE_PRODUCT_DUPLICATE_ERROR_LABEL = "Eroare la modificare produs: produsul este deja in baza de date!";
	public static final String ADD_NEW_PRODUCT_OK = "Adaugare realizata cu succes!";
	public static final String UPDATE_PRODUCT_OK = "Modificare realizata cu succes!";
	public static final String STERGE_NEW_PRODUCT_OK_LABEL = "OK";
	
	public static final String PRODUSE_SEARCH_DUPA_NUME_VIEW = "searchProduseDupaNumeCategorie";
	public static final String PRODUSE_SEARCH_DUPA_NUME_RESULTS = "produse_search_dupa_nume_results";
	
	public static final String DETALII_PRODUS_SELECTAT = "detalii_produs_selectat";
	public static final String DETALII_PRODUS_SELECTAT_VIEW = "obtineDetaliiProdusSelectat";
	
	public static final String ID_ATRIBUT_EXISTENT = "id_atribut";
	public static final String NUME_ATRIBUT_EXISTENT = "nume_atribut";
	public static final String ID_CATEGORIE_ATRIBUT_EXISTENT = "id_cat_atr";
	public static final String NUME_CAT_ATRIBUT_EXISTENT = "nume_cat_atr";
	
	public static final String DELETE_EXISTING_PICTURE_RESULT = "delete_existing_picture_result";
	public static final String DELETE_EXISTING_PICTURE_ERROR_LABEL = "Eroare de sistem!";
	public static final String DELETE_EXISTING_PICTURE_OK_LABEL = "OK";
	public static final String DELETE_EXISTING_PICTURE_VIEW = "deleteExistingPicture";
}
