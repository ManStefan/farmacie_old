package farmacie.forms;

import farmacie.model.Etalon;

public class EtalonOption {
	private boolean selected = false;
	private Etalon etalon;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Etalon getEtalon() {
		return etalon;
	}
	public void setEtalon(Etalon etalon) {
		this.etalon = etalon;
	}
}
