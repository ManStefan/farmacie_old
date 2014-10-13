package farmacie.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasicEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5155317036414204969L;
	private int id;	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BasicEntity))
			return false;
		BasicEntity other = (BasicEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
	
	
}
