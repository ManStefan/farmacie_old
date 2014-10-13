package farmacie.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="categorii_ale_atributelor")
public class CategorieAAtributelor extends DefaultLogable{

	private String nume;
	private List<Atribut> atribute;
	
	@Column(name="nume", nullable=false, unique=true)	
	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categorieAAtributelor")	
	public List<Atribut> getAtribute() {
		return atribute;
	}

	public void setAtribute(List<Atribut> atribute) {
		this.atribute = atribute;
	}

	@Override
	public String toString() {
		return "CategorieAAtributelor [nume=" + nume + ", getId()=" + getId()
				+ "]";
	}
	
}
