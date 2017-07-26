package com.epam.ta.parking.bean;

import java.io.Serializable;

public class Slot implements Serializable {

	private static final long serialVersionUID = -7766853054105065305L;

	private Integer number;

	public enum Size {
		REGULAR, LITTLE
	}

	private Size size;

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public boolean setSize(String sizeStr) {
		for (Size size : Size.values()) {
			if (size.name().equalsIgnoreCase(sizeStr)) {
				this.size = Size.valueOf(sizeStr.toUpperCase());
				return true;
			}
		}
		return false;
	}

	private Boolean isCovered;

	public Integer getNumber() {
		return number;
	}

	public Boolean getIsCovered() {
		return isCovered;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setIsCovered(Boolean isCovered) {
		this.isCovered = isCovered;
	}

	@Override
	public String toString() {
		return "Slot [number=" + number + ", isCovered=" + isCovered + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isCovered == null) ? 0 : isCovered.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		Slot other = (Slot) obj;
		if (isCovered == null) {
			if (other.isCovered != null)
				return false;
		} else if (!isCovered.equals(other.isCovered))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

}
