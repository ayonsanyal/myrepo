package com.java.orders.dao;

import java.util.List;

import com.java.orders.beans.BookingItemsDTO;
import com.java.orders.beans.ItemsDto;
import com.java.orders.model.BookingDetails;


public interface BookingConfirmationDao {

	public void addBookingItems(BookingItemsDTO bookingorderitems);

	public List<BookingDetails> getListOfBookedItems(BookingItemsDTO bookingorderitems);
	public List<ItemsDto> getListOfItems(BookingItemsDTO bookingdetails);
}
