package com.kikia.itacon.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Institution extends BasicEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6803767084313616361L;
	@JsonIgnore
	@OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Activity> activities = new HashSet<>();

	public Institution() {
	}

	public Institution(String name) {
		super(name);
	}


	public Set<Activity> getActivities() {
		return this.activities;
	}

	public void setActivities(Set<Activity> activities) {
			this.activities.clear();
			this.activities.addAll(activities);
	}

	@Override
	public int hashCode() {
		String name = getName();
		final int prime = 31;
		return prime + ((name == null) ? 0 : name.hashCode());
	}

	@Override
	public boolean equals(Object obj) {

		String name = getName();

		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;

		Institution that = (Institution) obj;

		if (!name.equals(that.getName())) {
			return false;
		}
		return true;
	}

	public void addActivity(Activity activity) {

		if (!this.activities.contains(activity)) {
			this.activities.add(activity);
			activity.setInstitution(this);
		}
	}

	public void removeActivity(Activity activity) {
		if ((this.activities != null) && this.activities.contains(activity)) {
			this.activities.remove(activity);
			activity.setInstitution(null);
		}
	}
	
	public String toString() {
		return String.format("[%s][id=%d][name=%s]", this.getClass().getSimpleName(), getId(), getName());
	}
}