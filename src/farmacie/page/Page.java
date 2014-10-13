package farmacie.page;

import farmacie.statics.FarmacieConstants;

public class Page implements FarmacieConstants {

	protected int total_pages = 0;
	protected int current_page = 1;
	protected int result_per_page = DEFAULT_RESULT_PER_PAGE;
	protected long total_results = 0;	
	protected boolean apasatLinkPaginare = false;
	
	
	public long getTotal_result() {
		return total_results;
	}

	public void setTotal_result(long totalResults) {
		total_results = totalResults;
	}

	public int getTotalPages(){
		return total_pages;
	}
	
	public void setTotalPages(int nr_pages){
		total_pages = nr_pages;
	}
	
	public int getCurrentPage(){
		return current_page;
	}
	
	public void setCurrentPage(int page){
		current_page = page;
	}
	
	public boolean hasNextPage(){
		return current_page < total_pages;
	}
	
	public boolean hasPreviousPage(){
		return current_page > 1;
	}
	
	public boolean isValidPage(int currPage){
		if (currPage > 0 && currPage <= total_results)
			return true;
		else
			return false;
	}
	
	public int getNextPage(){
		if (current_page <= total_pages)
			return ++current_page;
		return current_page;
	}
	
	public int getPreviousPage(){
		if (current_page > 1)
			return --current_page;
		return current_page;
	}

	public int getResult_per_page() {
		return result_per_page;
	}

	public void setResult_per_page(int resultPerPage) {
		result_per_page = resultPerPage;
	}
	
	public int getFirstResultPerPage(){
		if (getCurrentPage() > 0)
			return (getCurrentPage()-1) * getResult_per_page();
		else
			return 0;
	}

	public boolean isApasatLinkPaginare() {
		return apasatLinkPaginare;
	}

	public void setApasatLinkPaginare(boolean apasatLinkPaginare) {
		this.apasatLinkPaginare = apasatLinkPaginare;
	}	
}
