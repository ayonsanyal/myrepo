package com.java.orders.service;

import java.text.ParseException;
import java.util.List;

import com.java.orders.beans.TimeSlotDto;
import com.java.orders.exceptions.TimeSlotNotAvailableException;

public interface AvailableTimeSlotService {
	
	public List<TimeSlotDto> getAvailableTimeSlots(String orderdate,String todaystime) throws ParseException, TimeSlotNotAvailableException;
	

}
