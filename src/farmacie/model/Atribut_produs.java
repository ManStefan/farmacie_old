package farmacie.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="atribute_produse")
public class Atribut_produs extends BasicEntity{

	private Atribut atribut;
	private Produs produs;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_atribut")	
	public Atribut getAtribut() {
		return atribut;
	}
	public void setAtribut(Atribut atribut) {
		this.atribut = atribut;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_prod")		
	public Produs getProdus() {
		return produs;
	}
	public void setProdus(Produs produs) {
		this.produs = produs;
	}
	@Override
	public String toString() {
		return "Atribut_produs [atribut=" + atribut + ", produs=" + produs
				+ ", getId()=" + getId() + "]";
	}
	
	
}
