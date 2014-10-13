package farmacie.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToMany;

@Entity
@Table(name="produse")
public class Produs extends DefaultLogable{
	private String nume;
	private Double pret;
	private int cantitate;
	private String descriere;
	
	private List<Atribut_produs> atributeProdus;
	private Categorie categorie;
	private Etalon etalon;
	private Producator producator;
	private List<Poza> poza;

	@Column(name="nume", nullable=false, unique=true)	
	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	@Column(name="pret", nullable=false)	
	public Double getPret() {
		return pret;
	}

	public void setPret(Double pret) {
		this.pret = pret;
	}

	@Column(name="cantitate", nullable=false)	
	public int getCantitate() {
		return cantitate;
	}

	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}
	
	
	@Column(name="descriere")	
	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produs", cascade=CascadeType.ALL)	
	public List<Poza> getPoza() {
		return poza;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produs", cascade=CascadeType.ALL)		
	public List<Atribut_produs> getAtributeProdus() {
		return atributeProdus;
	}

	public void setAtributeProdus(List<Atribut_produs> atributeProdus) {
		this.atributeProdus = atributeProdus;
	}

	public void setPoza(List<Poza> poza) {
		this.poza = poza;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_categorie")		
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_etalon")		
	public Etalon getEtalon() {
		return etalon;
	}

	public void setEtalon(Etalon etalon) {
		this.etalon = etalon;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_producator")		
	public Producator getProducator() {
		return producator;
	}

	public void setProducator(Producator producator) {
		this.producator = producator;
	}


	@Override
	public String toString() {
		return "Produs [cantitate=" + cantitate + ", descriere=" + descriere
				+ ", id=" + getId() + ", nume=" + nume + ", pret=" + pret + "]";
	}



	protected String creeatDe;
	protected String modificatDe;
	protected Date dataCreeare;
	protected Date dataModificare;
	
	@Column(name = "CREEAT_DE")
	public String getCreeatDe() {
		return creeatDe;
	}
	public void setCreeatDe(String creeatDe) {
		this.creeatDe = creeatDe;
	}
	
	@Column(name = "MODIFICAT_DE")
	public String getModificatDe() {
		return modificatDe;
	}
	public void setModificatDe(String modificatDe) {
		this.modificatDe = modificatDe;
	}
	
	@Column(name = "DATA_CREEARE")
	public Date getDataCreeare() {
		return dataCreeare;
	}
	public void setDataCreeare(Date dataCreeare) {
		this.dataCreeare = dataCreeare;
	}
	
	@Column(name = "DATA_MODIFICARE")
	public Date getDataModificare() {
		return dataModificare;
	}
	public void setDataModificare(Date dataModificare) {
		this.dataModificare = dataModificare;
	}
	
	

}
