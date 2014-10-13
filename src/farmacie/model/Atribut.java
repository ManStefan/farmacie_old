package farmacie.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="atribute")
public class Atribut extends DefaultLogable{
	private String nume;
	
	private List<Atribut_produs> atribute_produse;
	private CategorieAAtributelor categorieAAtributelor;

	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_categorii_ale_atributelor")
	public CategorieAAtributelor getCategorieAAtributelor() {
		return categorieAAtributelor;
	}

	public void setCategorieAAtributelor(CategorieAAtributelor categorieAAtributelor) {
		this.categorieAAtributelor = categorieAAtributelor;
	}
	
	@Column(name="nume", nullable=false, unique=true)	
	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atribut")	
	public List<Atribut_produs> getAtribute_produse() {
		return atribute_produse;
	}

	public void setAtribute_produse(List<Atribut_produs> atributeProduse) {
		atribute_produse = atributeProduse;
	}

	@Override
	public String toString() {
		return "Atribut [id=" + getId() + ", nume=" + nume + "]";
	}
	
}
