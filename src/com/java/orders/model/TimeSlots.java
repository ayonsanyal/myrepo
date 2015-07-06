package com.java.orders.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "TIMESLOTS")
public class TimeSlots implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer TimeSlotStart;

	private Integer TimeSlotEnd;

	@Column(name = "TIMESLOT_START")
	public Integer getTimeSlotStart() {
		return TimeSlotStart;
	}

	public void setTimeSlotStart(Integer timeSlotStart) {
		TimeSlotStart = timeSlotStart;
	}

	@Column(name = "TIMESLOT_END")
	public Integer getTimeSlotEnd() {
		return TimeSlotEnd;
	}

	public void setTimeSlotEnd(Integer timeSlotEnd) {
		TimeSlotEnd = timeSlotEnd;
	}

}
