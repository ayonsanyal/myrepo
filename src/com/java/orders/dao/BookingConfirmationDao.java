package com.java.orders.dao;

import java.util.List;

import com.java.orders.beans.BookingItemsDTO;
import com.java.orders.model.BookingDetails;

public interface BookingConfirmationDao {

	public void addBookingItems(BookingItemsDTO bookingorderitems);

	public List<BookingDetails> getListOfBookedItems();
}
