package com.java.orders;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/*import org.springframework.web.bind.annotation.RequestParam;*/
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.orders.beans.RequestBean;
import com.java.orders.beans.TimeSlotDto;
import com.java.orders.exceptions.TimeSlotNotAvailableException;
import com.java.orders.service.AvailableTimeSlotService;

@RequestMapping(value = "/order")
@Controller
public class AvailableTimeSlotController {
	static Logger log = Logger.getLogger(AvailableTimeSlotController.class.getName());
	@Autowired
	private AvailableTimeSlotService availabletimeslot;

	@RequestMapping(method = RequestMethod.GET, value = "/homepage")
	public ModelAndView getAvailableTimeSlots() {

		ModelAndView model = new ModelAndView("timeslot");

		return model;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/timeslots",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<TimeSlotDto> getAvailableTimeSlots(@RequestBody RequestBean requestbean )
			throws ParseException, Exception {
				log.debug("Entering the method getAvailableTimeSlots ");
		List<TimeSlotDto> alltimeslots = new ArrayList<TimeSlotDto>();
        if(requestbean!=null)
        	
		if (!requestbean.getTodaysTime().isEmpty() && !requestbean.getOrderDate().isEmpty()) {

			alltimeslots = availabletimeslot.getAvailableTimeSlots(requestbean.getOrderDate(),
					requestbean.getTodaysTime());
			if (!alltimeslots.isEmpty() && alltimeslots != null) {
				log.debug("The method getBookedOrders is returning "+alltimeslots.toString());
				return alltimeslots;
			}
		}
		
		return alltimeslots;
	}

}
