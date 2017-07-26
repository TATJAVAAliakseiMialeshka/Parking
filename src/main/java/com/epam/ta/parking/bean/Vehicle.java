package com.epam.ta.parking.bean;

import java.io.Serializable;

public class Vehicle implements Serializable {

	private static final long serialVersionUID = 2738557403853397130L;

	private Integer id;

	private String regNumber;

	private Type type;

	public enum Type {
		CAR, MOTOCYCLE;
		
		@Override
		public String toString(){
			return this.name().toLowerCase();
			
		}
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean setType(String typeStr) {
		for (Type type : Type.values()) {
			if (type.name().equalsIgnoreCase(typeStr)) {
				this.type = Type.valueOf(typeStr.toUpperCase());
				return true;
			}
		}
		return false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", regNumber=" + regNumber + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((regNumber == null) ? 0 : regNumber.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (regNumber == null) {
			if (other.regNumber != null)
				return false;
		} else if (!regNumber.equals(other.regNumber))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
