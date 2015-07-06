package com.java.orders;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.java.orders.beans.BookedItemDto;
import com.java.orders.service.BookingConfirmationService;
import com.java.orders.model.BookingDetails;
import com.java.orders.beans.BookingItemsDTO;

@RestController
@RequestMapping("/booking")
public class BookingController {

	static Logger log = Logger.getLogger(BookingController.class.getName());
	@Autowired
	BookingConfirmationService bookingconfirmationservice;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/bookingorders", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<BookedItemDto> getBookedOrders(
			@RequestBody BookingItemsDTO bookingitem) {
		log.debug("Entering the method getBookedOrders ");
		if (bookingitem != null) {
			bookingconfirmationservice.addBookingItems(bookingitem);

		}

		List<BookingDetails> bookingdetails = bookingconfirmationservice
				.getListOfBookedItems();

		BookedItemDto bookeditemdto = new BookedItemDto();
		List<BookedItemDto> listbookeditemdto = new ArrayList<BookedItemDto>();
		if (null != bookingdetails && !bookingdetails.isEmpty()) {
			for (BookingDetails bookingdetailsstore : bookingdetails) {

				bookeditemdto.setBookingId(String.valueOf(bookingdetailsstore
						.getBookingId()));

				bookeditemdto.setTimeSlot(bookingdetailsstore.getTimeSlot());
				bookeditemdto.setOrderdate(bookingdetailsstore.getOrderdate());
				listbookeditemdto.add(bookeditemdto);
			}
		}
		log.debug("The method is returning "+listbookeditemdto.toString());
		return listbookeditemdto;

	}

}
