package com.java.orders.service;

import java.text.ParseException;
import java.util.List;

import com.java.orders.beans.TimeSlotDto;

public interface AvailableTimeSlotService {
	
	public List<TimeSlotDto> getAvailableTimeSlots(String orderdate,String todaystime) throws ParseException;
	

}
