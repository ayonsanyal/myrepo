package com.java.orders.service;
import java.util.List;

import com.java.orders.beans.ItemsDto;
import com.java.orders.model.BookingDetails;
public interface BookingPostProcessorService {
	
	public void fillupcartonwithitems(List<BookingDetails> bookingdetails,List<ItemsDto> itemsdto) throws Exception;

}
