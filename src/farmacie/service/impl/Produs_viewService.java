package farmacie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farmacie.dao.Produs_viewDao;
import farmacie.filters.impl.FilterProdus;
import farmacie.page.Page;
import farmacie.service.Produs_viewServiceI;
import farmacie.sort.Sort;
import farmacie.statics.FarmacieConstants;

@Service
public class Produs_viewService implements FarmacieConstants,
		Produs_viewServiceI {

	@Autowired
	Produs_viewDao produs_viewDao;
	
	
	@Override
	@Transactional
	public List<Integer> filterProdus_view(FilterProdus filter, Page page,
			Sort sort) {
		
		return produs_viewDao.filterProdus_view(filter, page, sort);
	}

}
