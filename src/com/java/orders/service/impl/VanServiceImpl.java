package com.java.orders.service.impl;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.java.orders.beans.Carton;
import com.java.orders.beans.VanDetailDto;
import com.java.orders.dao.VanDao;
import com.java.orders.model.VanDetails;
import com.java.orders.service.VanService;

@Service("vanservice")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
public class VanServiceImpl implements VanService {

	String[] vannumbers = { "Van1", "Van2", "Van3", "Van4" };
	List vanNumberList =null;

	@Autowired
	private VanDao vandao;
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@SuppressWarnings("unchecked")
	public List<VanDetails> getVanDetails(String timeslot, String OrderDate) {
		vanNumberList = Arrays.asList(vannumbers);

		return vandao.getVanDetails(timeslot, OrderDate, vanNumberList);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addCartonsToVan(List<VanDetailDto> van1,List<VanDetailDto> van2,List<VanDetailDto> van3,List<VanDetailDto> van4,List<Carton> cartons)
	{
		vandao.addCartonsToVan(van1, van2, van3, van4, cartons);
		
	}

		
}
