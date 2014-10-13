package farmacie.model.view;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa in care pun toate entitatile care au legatura cu un anumit produs
 * @author Stefan
 *
 */

public class ProdusViewLite {

	private int id;	
	private String nume;
	private Double pret;
	private int cantitate;
	private String descriere;
	private int nrCarDescriere;
	
	public ProdusViewLite(int nrCarDescriere){
		this.nrCarDescriere = nrCarDescriere;
	}

	private List<String> numePoze = new ArrayList<String>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public Double getPret() {
		return pret;
	}

	public void setPret(Double pret) {
		this.pret = pret;
	}

	public int getCantitate() {
		return cantitate;
	}

	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}

	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		if (nrCarDescriere > 0 && descriere.length() > nrCarDescriere){
			int pozSpace = descriere.indexOf(" ", nrCarDescriere);
			descriere = descriere.substring(0, pozSpace) + "...";
		}		
		this.descriere = descriere;
	}

	public List<String> getNumePoze() {
		return numePoze;
	}

	public void setNumePoze(List<String> numePoze) {
		this.numePoze = numePoze;
	}
	
}
