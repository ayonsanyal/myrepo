package com.java.orders.service;

import java.util.List;

import com.java.orders.beans.Carton;
import com.java.orders.beans.VanDetailDto;
import com.java.orders.model.VanDetails;

public interface VanService {
	
	public List<VanDetails> getVanDetails(String timeslot,String OrderDate);
	
	public void addCartonsToVan(List<VanDetailDto> van1,List<VanDetailDto> van2,List<VanDetailDto> van3,List<VanDetailDto> van4,List<Carton> cartons);

}
