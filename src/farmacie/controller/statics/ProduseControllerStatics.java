package farmacie.controller.statics;

public interface ProduseControllerStatics {
	
	public static final String ID_PRODUS = "id_produs"; 
	
	//etichete din pagina
	public static final String PRODUSE = "produse";
	
	//cheile pentru modelMap
	public static final String ATRIBUTE_MAP = "atributeMap";
	public static final String PRODUCATORI_MAP = "producatoriMap";
	public static final String ETALOANE_MAP = "etaloaneMap";
	public static final String PRETURI_MAP = "preturiMap";
	public static final String CANTITATE_MAP = "cantitateMap";
	public static final String SHOW_IN_STOCK = "showInStock";
	public static final String ID_CATEGORIE_PRODUS = "idCategorieProdus";
	public static final String FILTERED_PRODUCTS_LIST = "filteredProductsList";
	public static final String CATEGORII_MAP = "categoriiMap";
	
	//numele pentru input-uri pentru filtru
	public static final String IN_STOCK = "inStock";
	public static final String SELECTED_ATTRS = "selectedAttrs";
	public static final String SELECTED_PRODUCERS = "selectedProducers";
	public static final String SELECTED_ETALON = "selectedEtalon";
	public static final String SELECTED_CATEGORIES = "id_categorie";
	
	//numele pentru input-uri pentru pagina
	public static final String PAGE_NR = "pageNr";
	
	//numele pentru input-uri pentru sortare
	public static final String SORT_FIELD = "sortField";
	
	//numarul de pasi in care sunt afisate preturile
	public static final Integer ITERATII_PRETURI = 5;
	
	public static final String IS_NEXT_PAGES_CHUNK = "isNextPagesChunk";
	public static final String IS_PREV_PAGES_CHUNK = "isPrevPagesChunk";
}
