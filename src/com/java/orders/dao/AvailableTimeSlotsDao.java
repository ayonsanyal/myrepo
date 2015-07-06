package com.java.orders.dao;

import java.util.List;

import com.java.orders.model.TimeSlots;

public interface AvailableTimeSlotsDao {

	public List<TimeSlots> getListofTimeSlots(String todaystimeinhours);

	public List<TimeSlots> getListofTimeSlotsFutureDate();
}
