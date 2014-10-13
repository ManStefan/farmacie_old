package farmacie.sort;

import farmacie.statics.FarmacieConstants;

public class Sort implements FarmacieConstants {

	public static final String SORT_ASC = "asc";
	public static final String SORT_DESC = "desc";
	public static final String DEFAULT_SORT_FIELD = "id";
	public static final String NO_SORT = "noSort";
	
	//sortare pret
	public static final String PRICE_SORT_FIELD = "pret";
	public static final String SORT_PRICE_ASC = "price_asc";
	public static final String SORT_PRICE_DESC = "price_desc";	
	
	protected String sortField;
	protected String sortOrder;
	
	public String getSortField() {
		return this.sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getSortOrder(){
		return this.sortOrder;
	}

	public Sort(){
		setSortOrder(NO_SORT);
	}
	
	
}
