package com.java.orders.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.java.orders.beans.Carton;
import com.java.orders.beans.ItemDetailDto;
import com.java.orders.beans.VanDetailDto;
import com.java.orders.dao.VanDao;
import com.java.orders.model.Cartons;
import com.java.orders.model.ItemsOngoing;
import com.java.orders.model.VanDetails;

@Repository("vandao")
public class VanDaoImpl implements VanDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<VanDetails> getVanDetails(String timeslot, String OrderDate,
			List<String> vannumber) {
    
		Criteria criteria = sessionFactory
				.getCurrentSession().createCriteria(VanDetails.class);
		Criterion condition1= Restrictions.in("vanNumber", vannumber);
		Criterion condition2=Restrictions.eq("timeSlot", timeslot);
		Criterion condition3=Restrictions.eq("orderdate",OrderDate);
		Criterion condition4=Restrictions.and(condition2, condition3);
		Criterion condition5=Restrictions.and(condition1, condition4);
		criteria.add(condition5);
		
		List<VanDetails> vandetails = criteria.list();

		return vandetails;

	}

	public void addCartonsToVan(List<VanDetailDto> van1,List<VanDetailDto> van2,List<VanDetailDto> van3,List<VanDetailDto> van4,List<Carton> cartons)
	{   
		
		Session session = sessionFactory.getCurrentSession();
		List<ItemsOngoing> itemlistfinal = new ArrayList<ItemsOngoing>();
		List<ItemsOngoing> itemlistfinal2 = new ArrayList<ItemsOngoing>();
		List<ItemsOngoing> itemlistfinal3 = new ArrayList<ItemsOngoing>();
		List<ItemsOngoing> itemlistfinal4 = new ArrayList<ItemsOngoing>();
		
		List<Cartons> cartonslist1 = new ArrayList<>();
		List<Cartons> cartonslist2 = new ArrayList<>();
		List<Cartons> cartonslist3 = new ArrayList<>();
		List<Cartons> cartonslist4 = new ArrayList<>();
		Iterator<VanDetailDto> iteratorvan1 = van1.iterator();
		Iterator<VanDetailDto> iteratorvan2 = van2.iterator();
		Iterator<VanDetailDto> iteratorvan3 = van3.iterator();
		Iterator<VanDetailDto> iteratorvan4 = van4.iterator();
	try{	
		if(!van1.isEmpty()){
			VanDetails vandetails = new VanDetails();
			int index1=0;
		while(iteratorvan1.hasNext())
		{
			
			VanDetailDto van1dto = iteratorvan1.next();
			vandetails.setOrderdate(van1dto.getOrderdate());
			vandetails.setOrdername(van1dto.getOrdername());
			vandetails.setBookingId(van1dto.getBookingId());
			vandetails.setTimeSlot(van1dto.getTimeSlot());
			vandetails.setVanNumber(van1dto.getVanNumber());
			for(Carton carton :van1dto.getItems())
			{
				Cartons cartondata = new Cartons();
				for(ItemDetailDto itemdetaildto :carton.getItems())
				{
					ItemsOngoing itemongoing = new ItemsOngoing();
					itemongoing.setCode(itemdetaildto.getCode());
					itemongoing.setHeight(itemdetaildto.getHeight());
					itemongoing.setLength(itemdetaildto.getLength());
					itemongoing.setWidth(itemdetaildto.getWidth());
					itemongoing.setQuantity(itemdetaildto.getQuantity());
					itemlistfinal.add(itemongoing);
				}
				cartondata.setItemsongoing(itemlistfinal);
				cartondata.setVolume(carton.getVolume());
				cartonslist1.add(cartondata);
			}	
			vandetails.setItems(cartonslist1);	
		}
		session.save(vandetails);
		index1++;
		if(index1 % 20==0){
            //flush a batch of inserts and release memory:
            session.flush();
            session.clear();
        }
        
		}
		if(!van2.isEmpty()){
			VanDetails vandetails2 = new VanDetails();
			int index2=0;
		while(iteratorvan2.hasNext())
		{
			
			VanDetailDto van2dto = iteratorvan2.next();
			vandetails2.setOrderdate(van2dto.getOrderdate());
			vandetails2.setOrdername(van2dto.getOrdername());
			vandetails2.setBookingId(van2dto.getBookingId());
			vandetails2.setTimeSlot(van2dto.getTimeSlot());
			vandetails2.setVanNumber(van2dto.getVanNumber());
			for(Carton carton :van2dto.getItems())
			{
				Cartons cartondata = new Cartons();
				for(ItemDetailDto itemdetaildto :carton.getItems())
				{
					ItemsOngoing itemongoing = new ItemsOngoing();
					itemongoing.setCode(itemdetaildto.getCode());
					itemongoing.setHeight(itemdetaildto.getHeight());
					itemongoing.setLength(itemdetaildto.getLength());
					itemongoing.setWidth(itemdetaildto.getWidth());
					itemongoing.setQuantity(itemdetaildto.getQuantity());
					itemlistfinal2.add(itemongoing);
				}
				cartondata.setItemsongoing(itemlistfinal2);
				cartondata.setVolume(carton.getVolume());
				cartonslist2.add(cartondata);
			}	
			vandetails2.setItems(cartonslist2);	
		}
		session.save(vandetails2);
		index2++;
		if(index2 % 20==0){
            //flush a batch of inserts and release memory:
            session.flush();
            session.clear();
        }
        
		}
		if(!van3.isEmpty()){
			VanDetails vandetails3 = new VanDetails();
			int index3=0;
		while(iteratorvan3.hasNext())
		{
			
			VanDetailDto van3dto = iteratorvan3.next();
			vandetails3.setOrderdate(van3dto.getOrderdate());
			vandetails3.setOrdername(van3dto.getOrdername());
			vandetails3.setBookingId(van3dto.getBookingId());
			vandetails3.setTimeSlot(van3dto.getTimeSlot());
			vandetails3.setVanNumber(van3dto.getVanNumber());
			for(Carton carton :van3dto.getItems())
			{
				Cartons cartondata = new Cartons();
				for(ItemDetailDto itemdetaildto :carton.getItems())
				{
					ItemsOngoing itemongoing = new ItemsOngoing();
					itemongoing.setCode(itemdetaildto.getCode());
					itemongoing.setHeight(itemdetaildto.getHeight());
					itemongoing.setLength(itemdetaildto.getLength());
					itemongoing.setWidth(itemdetaildto.getWidth());
					itemongoing.setQuantity(itemdetaildto.getQuantity());
					itemlistfinal3.add(itemongoing);
				}
				cartondata.setItemsongoing(itemlistfinal4);
				cartondata.setVolume(carton.getVolume());
				cartonslist3.add(cartondata);
			}	
			vandetails3.setItems(cartonslist3);	
		}
		session.save(vandetails3);
		 index3++;
		if(index3 % 20==0){
            //flush a batch of inserts and release memory:
            session.flush();
            session.clear();
        }
       
		}
		if(!van4.isEmpty()){
			VanDetails vandetails4 = new VanDetails();
			int index4=0;
		while(iteratorvan4.hasNext())
		{
			
			VanDetailDto van4dto = iteratorvan4.next();
			vandetails4.setOrderdate(van4dto.getOrderdate());
			vandetails4.setOrdername(van4dto.getOrdername());
			vandetails4.setBookingId(van4dto.getBookingId());
			vandetails4.setTimeSlot(van4dto.getTimeSlot());
			vandetails4.setVanNumber(van4dto.getVanNumber());
			for(Carton carton :van4dto.getItems())
			{
				Cartons cartondata = new Cartons();
				for(ItemDetailDto itemdetaildto :carton.getItems())
				{
					ItemsOngoing itemongoing = new ItemsOngoing();
					itemongoing.setCode(itemdetaildto.getCode());
					itemongoing.setHeight(itemdetaildto.getHeight());
					itemongoing.setLength(itemdetaildto.getLength());
					itemongoing.setWidth(itemdetaildto.getWidth());
					itemongoing.setQuantity(itemdetaildto.getQuantity());
					itemlistfinal4.add(itemongoing);
				}
				cartondata.setItemsongoing(itemlistfinal4);
				cartondata.setVolume(carton.getVolume());
				cartonslist4.add(cartondata);
			}	
			vandetails4.setItems(cartonslist4);	
		}
		session.save(vandetails4);
		 index4++;
		if(index4 % 20==0){
            //flush a batch of inserts and release memory:
            session.flush();
            session.clear();
        }
       
		}
	}
	catch(HibernateException e)
	{
		e.printStackTrace();
		
	}
		
	}
	
}
