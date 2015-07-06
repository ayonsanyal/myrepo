package com.java.orders.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.java.orders.beans.BookingItemsDTO;
import com.java.orders.beans.ItemsDto;
import com.java.orders.dao.BookingConfirmationDao;
import com.java.orders.model.BookingDetails;
import com.java.orders.service.BookingConfirmationService;

@Service("bookingconfirmationservice")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BookingConfirmationServiceImpl implements
		BookingConfirmationService {
	
	static Logger log = Logger.getLogger(BookingConfirmationServiceImpl.class.getName());
	@Autowired
	private BookingConfirmationDao bookingconfirmationdao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addBookingItems(BookingItemsDTO bookingorderitems) {
		log.debug("Entering the method addBookingItems ");
		bookingconfirmationdao.addBookingItems(bookingorderitems);

	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BookingDetails> getListOfBookedItems(BookingItemsDTO bookingorderitems) {
		return bookingconfirmationdao.getListOfBookedItems(bookingorderitems);
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ItemsDto> getListofItems(BookingItemsDTO bookingdetails)
	{
		return bookingconfirmationdao.getListOfItems(bookingdetails);
	}
	
}
