package com.java.orders.service;

import com.java.orders.beans.BookingItemsDTO;
import com.java.orders.beans.ItemsDto;
import com.java.orders.model.BookingDetails;

import java.util.List;

public interface BookingConfirmationService {

	public void addBookingItems(BookingItemsDTO bookingorderitems);

	public List<BookingDetails> getListOfBookedItems(BookingItemsDTO bookingorderitems);
	
	public List<ItemsDto> getListofItems(BookingItemsDTO bookingdetails);
}
