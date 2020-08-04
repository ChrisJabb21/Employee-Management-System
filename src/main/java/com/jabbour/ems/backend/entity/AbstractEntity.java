package com.jabbour.ems.backend.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/***
 * The common superclass 
 * that the entities will inherit and 
 * not generate a table
 * 
 * it define how objects ids are generated 
 * and how object equality is determined.
 * @author chris jabbour
 *
 *more on abstract classes
 *https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
 */
@MappedSuperclass
public abstract class AbstractEntity {
	
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	private Long id;
	public Long getId() {
		return id;
	}
	public boolean isPersisted() {
		return id != null;
	}
	@Override
	public int hashCode() {
		if(getId() != null) {
			return getId().hashCode();
		}
		return super.hashCode();
	}
	@Override
	/**Check if object passed equals the object
	 * that invokes the equals method
	 * @param obj parameter of the superclass Object.
	 * @return a boolean value true or false 
	 * based on if the both objects are equal to each other
	 */
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		AbstractEntity other = (AbstractEntity) obj;
		if(getId() == null || other.getId() == null)
		{ 
			return false;
		}
		return getId().equals(other.getId());
	}		
}
