package com.epam.ta.parking.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class SlotReservation implements Serializable {

	private static final long serialVersionUID = -5023869247644296998L;

	private Integer id;
	
	private Integer vehicleId;

	private Integer slotId;

	private Timestamp start;

	private Timestamp end;

	public Integer getVehicleId() {
		return vehicleId;
	}

	public Integer getSlotId() {
		return slotId;
	}

	public Timestamp getStart() {
		return start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public void setSlotId(Integer slotId) {
		this.slotId = slotId;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SlotReservation [id=" + id + ", vehicleId=" + vehicleId + ", slotId=" + slotId + ", start=" + start
				+ ", end=" + end + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((slotId == null) ? 0 : slotId.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((vehicleId == null) ? 0 : vehicleId.hashCode());
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
		SlotReservation other = (SlotReservation) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (slotId == null) {
			if (other.slotId != null)
				return false;
		} else if (!slotId.equals(other.slotId))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (vehicleId == null) {
			if (other.vehicleId != null)
				return false;
		} else if (!vehicleId.equals(other.vehicleId))
			return false;
		return true;
	}

}
