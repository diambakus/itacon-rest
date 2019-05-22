package com.kikia.itacon.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Activity extends BasicEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 989891156132782598L;
	private BigDecimal price;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institution_id")
	private Institution institution;

	public Activity() {
		// TODO Auto-generated constructor stub
	}

	public Activity(String activityName, BigDecimal price) {
		super(activityName);
		this.price = price;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Institution getInstitution() {
		return this.institution;
	}

	public void setInstitution(Institution institution) {
		if (sameInstitution(institution))
			return;
		Institution formerInstituion = this.institution;
		this.institution = institution;

		if (formerInstituion != null)
			formerInstituion.removeActivity(this);
		if (this.institution != null)
			this.institution.addActivity(this);
	}

	@Override
	public int hashCode() {
		int prime = 31;
		return prime * ((getName() == null) ? 0 : getName().hashCode() % 89);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (this.getName().equals(other.getName()))
			return false;
		return true;
	}

	private boolean sameInstitution(Institution newInstitution) {
		return (this.institution == null) ? (newInstitution == null) : this.institution.equals(newInstitution);
	}

	public String toString() {
		return String.format("[%s][id=%d][name=%s][price=%s]", this.getClass().getSimpleName(), getId(), getName(),
				getPrice());
	}
}