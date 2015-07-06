package com.java.orders.dao;

import java.util.List;

import com.java.orders.beans.Carton;
import com.java.orders.model.ItemDetail;
import com.java.orders.model.VanDetails;

public interface BookingPostproccessingDao  {
	
	public List<ItemDetail> getitemdetails(List<String> itemdetail);
	
   
}
