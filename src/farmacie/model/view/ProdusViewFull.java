package farmacie.model.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clasa in care pun toate entitatile care au legatura cu un anumit produs
 * @author Stefan
 *
 */

public class ProdusViewFull {

	private int id;	
	private String nume;
	private Double pret;
	private int cantitate;
	private Date data_inreg;
	private String descriere;
	private int nrCarDescriere;
	
	public ProdusViewFull(int nrCarDescriere){
		this.nrCarDescriere = nrCarDescriere;
	}
	
	private Map<String,List<String>> numeAtribute = new HashMap<String,List<String>>();
	
	private List<String> numeCategorii = new ArrayList<String>();
	
	private List<String> numeEtaloane = new ArrayList<String>();
	
	private List<String> numeProducatori = new ArrayList<String>();

	private List<String> numePoze = new ArrayList<String>();
	
	private List<String> descrierePoze = new ArrayList<String>();	

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

	public Date getData_inreg() {
		return data_inreg;
	}

	public void setData_inreg(Date dataInreg) {
		data_inreg = dataInreg;
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

	public Map<String, List<String>> getNumeAtribute() {
		return numeAtribute;
	}

	public void setNumeAtribute(Map<String, List<String>> numeAtribute) {
		this.numeAtribute = numeAtribute;
	}

	public List<String> getNumeCategorii() {
		return numeCategorii;
	}

	public void setNumeCategorii(List<String> numeCategorii) {
		this.numeCategorii = numeCategorii;
	}

	public List<String> getNumeEtaloane() {
		return numeEtaloane;
	}

	public void setNumeEtaloane(List<String> numeEtaloane) {
		this.numeEtaloane = numeEtaloane;
	}

	public List<String> getNumePoze() {
		return numePoze;
	}

	public void setNumePoze(List<String> numePoze) {
		this.numePoze = numePoze;
	}

	public List<String> getNumeProducatori() {
		return numeProducatori;
	}

	public void setNumeProducatori(List<String> numeProducatori) {
		this.numeProducatori = numeProducatori;
	}

	public List<String> getDescrierePoze() {
		return descrierePoze;
	}

	public void setDescrierePoze(List<String> descrierePoze) {
		this.descrierePoze = descrierePoze;
	}	
}
