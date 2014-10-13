package farmacie.forms;

import farmacie.model.Atribut;

public class AtributOption {
	private boolean selected = false;
	private Atribut atribut;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Atribut getAtribut() {
		return atribut;
	}
	public void setAtribut(Atribut atribut) {
		this.atribut = atribut;
	} 
	
	
	
}
