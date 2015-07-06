package com.java.orders.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.java.orders.beans.TimeSlotDto;
import com.java.orders.dao.AvailableTimeSlotsDao;
import com.java.orders.model.TimeSlots;
import com.java.orders.service.AvailableTimeSlotService;

@Service("availabletimeslot")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AvailableTimeSlotServiceImpl implements AvailableTimeSlotService {
	static Logger log = Logger.getLogger(AvailableTimeSlotServiceImpl.class
			.getName());
	@Autowired
	AvailableTimeSlotsDao availabletimeslotdao;
	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TimeSlotDto> getAvailableTimeSlots(String orderdate,
			String todaystime) throws ParseException {
		log.debug("Entering the method getAvailableTimeSlots ");
		TimeSlotDto availabletimeslots = new TimeSlotDto();
		List<TimeSlotDto> timeslotdtolist = new ArrayList<>();
		List<TimeSlots> timeslots = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date bookingdate = sdf.parse(orderdate);
		Date datetoday = new Date();
		String todaysdate = sdf.format(datetoday);
		Date currentdate = sdf.parse(todaysdate);

		/* Checking for the future dates timeslots */

		if (bookingdate.compareTo(currentdate) > 0) {
			timeslots = availabletimeslotdao.getListofTimeSlotsFutureDate();
			if (!timeslots.isEmpty() && timeslots != null) {
				for (TimeSlots timeslot : timeslots) {
					availabletimeslots.setTimeSlot(String.valueOf(timeslot
							.getTimeSlotStart())
							+ "-"
							+ String.valueOf(timeslot.getTimeSlotEnd()));
					timeslotdtolist.add(availabletimeslots);
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

					availabletimeslots.setTimeSlot(String.valueOf(timeslot
							.getTimeSlotStart())
							+ "-"
							+ String.valueOf(timeslot.getTimeSlotEnd()));
					timeslotdtolist.add(availabletimeslots);
				}

			}
		}
		log.debug("The method getBookedOrders is returning "
				+ timeslotdtolist.toString());
		return timeslotdtolist;

	}

}
