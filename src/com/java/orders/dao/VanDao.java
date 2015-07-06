package com.java.orders.dao;

import java.util.List;

import com.java.orders.beans.Carton;
import com.java.orders.beans.VanDetailDto;
import com.java.orders.model.BookingDetails;
import com.java.orders.model.VanDetails;

public interface VanDao {
	
	public List<VanDetails> getVanDetails(String timeslot,String OrderDate,List<String> vannumber);
	public void addCartonsToVan(List<VanDetailDto> van1,List<VanDetailDto> van2,List<VanDetailDto> van3,List<VanDetailDto> van4,List<Carton> cartons);

}
