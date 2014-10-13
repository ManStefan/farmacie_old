package farmacie.dao;

import java.util.List;

import farmacie.filters.impl.FilterProdus;
import farmacie.page.Page;
import farmacie.sort.Sort;

public interface Produs_viewDao {

	public List<Integer> filterProdus_view(FilterProdus filter, Page page, Sort sort);
}
