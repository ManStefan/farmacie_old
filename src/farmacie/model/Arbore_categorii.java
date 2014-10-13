package farmacie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="arbore_categorii")
public class Arbore_categorii extends BasicEntity{
	
	private int id_cat_parinte;
	private int id_cat_copil;

	@Column(name="id_cat_parinte", nullable=false)	
	public int getId_cat_parinte() {
		return id_cat_parinte;
	}

	public void setId_cat_parinte(int idCatParinte) {
		id_cat_parinte = idCatParinte;
	}

	@Column(name="id_cat_copil", nullable=false)	
	public int getId_cat_copil() {
		return id_cat_copil;
	}

	public void setId_cat_copil(int idCatCopil) {
		id_cat_copil = idCatCopil;
	}

	@Override
	public String toString() {
		return "Arbore_categorii [id_cat_copil=" + id_cat_copil
				+ ", id_cat_parinte=" + id_cat_parinte + ", getId()=" + getId()
				+ "]";
	}
	
}
