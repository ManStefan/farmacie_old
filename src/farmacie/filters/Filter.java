package farmacie.filters;

import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;

import farmacie.statics.FarmacieConstants;

public abstract class Filter implements FarmacieConstants {
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	public abstract String getSqlFromFilter();
	
	/**
	 * method care returneaza Criterion din filtru
	 * @return
	 */
	public abstract Criterion getCriterionFromFilter();

}
