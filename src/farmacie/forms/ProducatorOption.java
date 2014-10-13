package farmacie.forms;

import farmacie.model.Producator;

public class ProducatorOption {
	private boolean selected = false;
	private Producator producator;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Producator getProducator() {
		return producator;
	}
	public void setProducator(Producator producator) {
		this.producator = producator;
	}
	
	
}
