package farmacie.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="etaloane")
public class Etalon extends DefaultLogable{
	private String nume;
	
	private List<Produs> produse;	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "etalon")		
	public List<Produs> getProduse() {
		return produse;
	}

	public void setProduse(List<Produs> produse) {
		this.produse = produse;
	}
	
	@Column(name="nume", nullable=false, unique=true)	
	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	@Override
	public String toString() {
		return "Etalon [nume=" + nume + ", getId()=" + getId() + "]";
	}
	
}
