package com.java.orders.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "TIMESLOTS")
public class TimeSlots implements Serializable {
	private static final long serialVersionUID = 1L;
	private long Id;
	private Integer TimeSlotStart;

	private Integer TimeSlotEnd;
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TIMESLOT_ID")

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}
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
