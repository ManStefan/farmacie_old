package farmacie.admin.controller.statics;

public interface AdminCategoriiControllerStatics {

	public static final String ROOT_DROPDOWN_CATH_LIST = "root_dropdown_cath_list";
	public static final String DROPDOWN_CAT_LIST = "dropdown_cat_list";
	public static final String NR_OF_DROPDOWNS = "nrOfDropDowns";
	public static final Integer NR_OF_RECORDS_SHOWN = 500;
	
	
	public static final String ADMIN_CATEGORII_VIEW = "adminCategorii";
	public static final String CHILDREN_CATEGORIES = "childrenCategories";
	public static final String ADD_CHILD_CATEGORY = "addChildCategory";
	public static final String DELETE_CATEGORY = "deleteCategory";
	public static final String FINISH_DELETE_CATEGORY = "finishDeleteCategory";
	public static final String ADD_BROTHER_CATEGORY = "addBrotherCategory";
	public static final String UPDATE_CATEGORY_NAME = "updateNameCat";
	public static final String ADD_CATEGORIES_LABEL_ERROR = "add_category_error";
	public static final String DELETE_CATEGORIES_LABEL_ERROR = "delete_category_error";
	public static final String ADDED_BROTHER_CATEGORY = "added_brother_category";
	public static final String MODIFIED_CATEGORY = "modified_category";
	public static final String OLD_NAME_CATEGORY = "old_name_category";
	public static final String ARE_CHILD_CATEGORIES = "are_child_categories";
	public static final String ARE_PRODUCTS_IN_CATEGORIES = "are_products_in_categories";
	public static final String CATEGORIE_SEARCH_RESULTS = "categorie_search_results";
	public static final String ADMIN_SEARCH_CATEGORIE_VIEW = "searchCategorie";
	
	//ERRORS
	public static final String DUPLICATE_CATEGORIES_ERROR = "Numele categoriei este folosit deja!";
	public static final String DUPLICATE_CATEGORIES_MAPPING_ERROR = "Mapare duplicata in ierarhia de categorii!";
	public static final String ADD_CATEGORIES_ERROR = "Eroare de sistem la inserarea/modificarea unei categorii!";
	public static final String DELETE_CATEGORIES_ERROR_DELETE_PRODUCT = "Nu au putut fi sterse produsele din categoria: ";
	public static final String DELETE_CATEGORIES_ERROR_DELETE_CATEGORY = "Categoria urmatoare nu a putut fi stearsa: ";
}
