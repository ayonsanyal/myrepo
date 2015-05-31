package com.java.orders;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.orders.beans.TimeSlotDto;
import com.java.orders.service.AvailableTimeSlotService;

@RequestMapping(value = "/loadtimeslots")
@Controller
public class TimeSlotController {
	static Logger log = Logger.getLogger(TimeSlotController.class.getName());
	@Autowired
	private AvailableTimeSlotService availabletimeslot;

	@RequestMapping(method = RequestMethod.GET, value = "/availabletimeslots")
	public ModelAndView getAvailableTimeSlots() {

		ModelAndView model = new ModelAndView("timeslot");

		return model;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/showtimeslots", headers = "accept="
			+ MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<TimeSlotDto> getAvailableTimeSlots(
			@RequestParam("orderdate") String orderdate,
			@RequestParam("todaystime") String todaystime)
			throws ParseException {
		log.debug("Entering the method getAvailableTimeSlots ");
		List<TimeSlotDto> alltimeslots = new ArrayList<TimeSlotDto>();

		if (!orderdate.isEmpty() && !todaystime.isEmpty()) {

			alltimeslots = availabletimeslot.getAvailableTimeSlots(orderdate,
					todaystime);
			if (!alltimeslots.isEmpty() && alltimeslots != null) {
				log.debug("The method getBookedOrders is returning "+alltimeslots.toString());
				return alltimeslots;
			}
		}
		
		return alltimeslots;
	}

}
