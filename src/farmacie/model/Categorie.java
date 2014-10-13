package farmacie.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="categorii")
public class Categorie extends DefaultLogable{
	private String nume;
	private int nivel;
	private int frunza;
	
	private List<Produs> produse;	
		
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categorie")		
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

	@Column(name="nivel", nullable=false)	
	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	@Column(name="frunza", nullable=false)	
	public int getFrunza() {
		return frunza;
	}

	public void setFrunza(int frunza) {
		this.frunza = frunza;
	}

	@Override
	public String toString() {
		return "Categorie [frunza=" + frunza + ", nivel=" + nivel + ", nume="
				+ nume + ", getId()=" + getId() + "]";
	}

}
