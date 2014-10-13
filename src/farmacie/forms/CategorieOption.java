package farmacie.forms;

import farmacie.model.Categorie;

public class CategorieOption {
	private boolean selected = false;
	private Categorie categorie;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
}
