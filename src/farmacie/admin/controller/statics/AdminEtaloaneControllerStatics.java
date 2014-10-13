package farmacie.admin.controller.statics;

public interface AdminEtaloaneControllerStatics {
	public static final String ADMIN_ETALOANE_VIEW = "adminEtaloane";
	public static final String ADMIN_ADD_ETALON_VIEW = "addNewEtalon";
	
	public static final String ADD_ETALON_LABEL_ERROR = "add_etalon_label_errors";
	public static final String ADD_ETALON_DUPLICATE_ENTRY_ERROR = "Acest etalon exista in baza de date!";
	public static final String ADD_ETALON_SYSTEM_ERROR = "Eroare de sistem!";
	public static final String ADD_ETALON_OK = "OK";
	public static final String RENAME_ETALON_LABEL_ERROR = "rename_producator_label_error";
	public static final String DUPLICATE_PRODUCATOR_ERROR = "Noul nume exista deja in baza de date!";
	public static final String RENAME_PRODUCATOR_ERROR = "Eroare la redenumire producator!";
	public static final String DELETE_ETALON_LABEL_MESSAGE = "delete_etalon_label_message";
	public static final String DELETE_ETALON_EXISTS_ASSOCIATED_PRODS = "Exista produse asociate cu acest etalon!";
	public static final String DELETE_ETALON_NO_EXISTS_ASSOCIATED_PRODS = "OK";
	public static final String FINISH_DELETE_ETALON_LABEL_ERROR = "finish_delete_etalon_label_error";
	public static final String FINISH_DELETE_ETALON_ERROR = "Eroare de sistem la stergerea etalonului!";
	public static final String ADMIN_FINISH_DELETE_ETALON_VIEW = "finishDeleteEtalon";
	public static final String FINISH_DELETE_ETALON_OK = "OK";
	
	public static final Integer NR_OF_RECORDS_SHOWN = 100000;
	public static final String RENAME_ETALON_OK = "OK";
	
	public static final String ETALOANE_SEARCH_RESULTS = "etaloane_search_results";
	public static final String ADMIN_SEARCH_ETALOANE_VIEW = "searchEtaloane";
	public static final String ADMIN_RENAME_ETALON_VIEW = "renameEtalon";
	public static final String ADMIN_DELETE_ETALON_VIEW = "deleteEtalon";
}
