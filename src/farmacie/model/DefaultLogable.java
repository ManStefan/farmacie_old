package farmacie.model;

import java.util.Date;

import javax.persistence.Column;

public class DefaultLogable extends BasicEntity{

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
