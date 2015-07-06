package com.java.orders.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.java.orders.beans.TimeSlotDto;
import com.java.orders.dao.AvailableTimeSlotsDao;
import com.java.orders.exceptions.TimeSlotNotAvailableException;
import com.java.orders.model.TimeSlots;
import com.java.orders.service.AvailableTimeSlotService;

@Service("availabletimeslot")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AvailableTimeSlotServiceImpl implements AvailableTimeSlotService {
	static Logger log = Logger.getLogger(AvailableTimeSlotServiceImpl.class
			.getName());
	@Autowired
	AvailableTimeSlotsDao availabletimeslotdao;
	@Value("${timeslots}")
	private String[] alltimeslots;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TimeSlotDto> getAvailableTimeSlots(String orderdate,
			String todaystime) throws ParseException, TimeSlotNotAvailableException {
		log.debug("Entering the method getAvailableTimeSlots ");
		
		List<TimeSlotDto> timeslotlist =null;
		List<TimeSlotDto> timeslotdtolist = new ArrayList<>();
		List<TimeSlots> timeslots = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date bookingdate = sdf.parse(orderdate);
		Date datetoday = new Date();
		String todaysdate = sdf.format(datetoday);
		Date currentdate = sdf.parse(todaysdate);
		//To get the difference of days as booking will be accepted for only 7 days for future dates.
		int differenceInDays = (int)((bookingdate.getTime()-currentdate.getTime())/((1000 * 60 * 60 * 24)));
		
		
		TimeSlotDto availabletimeslots =null;
		TimeSlotDto availabletimeslotssameday =null;
		/* Checking for the future dates timeslots */

		if (bookingdate.compareTo(currentdate) > 0 && differenceInDays<7) {
			timeslots = availabletimeslotdao.getListofTimeSlotsFutureDate();
			if (!timeslotdtolist.isEmpty())
			{
				timeslotdtolist.clear();
			}
			if (!timeslots.isEmpty() && timeslots != null) {
				for (TimeSlots timeslot : timeslots) {
					 availabletimeslots = new TimeSlotDto();
					availabletimeslots.setTimeSlot(String.valueOf(timeslot
							.getTimeSlotStart())
							+ "-"
							+ String.valueOf(timeslot.getTimeSlotEnd()));
					
					timeslotdtolist.add(availabletimeslots);
					timeslotlist=timeslotdtolist;
				}
			}
		}
		/*
		 * Checking for the current day timeslots those are greater than
		 * timeofchecking
		 */
		else if (bookingdate.compareTo(currentdate) == 0) {

			timeslots = availabletimeslotdao.getListofTimeSlots(todaystime);
			if (!timeslots.isEmpty() && timeslots != null) {
				for (TimeSlots timeslot : timeslots) {
					 availabletimeslotssameday = new TimeSlotDto();
					availabletimeslotssameday.setTimeSlot(String.valueOf(timeslot
							.getTimeSlotStart())
							+ "-"
							+ String.valueOf(timeslot.getTimeSlotEnd()));
					
					timeslotdtolist.add(availabletimeslotssameday);
					timeslotlist=timeslotdtolist;
				}

			}
		}
		else if(bookingdate.compareTo(currentdate)<0 || differenceInDays>7)
		{
			log.error("Exception caught .Time Slot not available for this selected date"+orderdate);
			throw new TimeSlotNotAvailableException("Time Slot not available for this selected date");
			
		}
		log.debug("The method getBookedOrders is returning "
				+ timeslotlist.toString());
		return timeslotlist;

	}

}
