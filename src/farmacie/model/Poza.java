package farmacie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="poze")
public class Poza extends DefaultLogable{
	private String poza;
	private String descriere;
	
	private Produs produs;

	@Column(name="poza", nullable=true)
	public String getPoza() {
		return poza;
	}

	public void setPoza(String poza) {
		this.poza = poza;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_prod")	
	public Produs getProdus() {
		return produs;
	}

	public void setProdus(Produs produs) {
		this.produs = produs;
	}
	
	@Column(name="descriere", nullable=true)	
	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}

	@Override
	public String toString() {
		return "Poza [descriere=" + descriere + ", id=" + getId() + ", poza=" + poza
				+ "]";
	}
}
