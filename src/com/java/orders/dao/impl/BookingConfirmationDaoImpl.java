package com.java.orders.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.java.orders.beans.BookingItemsDTO;
import com.java.orders.beans.ItemsDto;
import com.java.orders.dao.BookingConfirmationDao;
import com.java.orders.model.BookingDetails;
import com.java.orders.model.Items;

@Repository("bookingconfirmationdao")
public class BookingConfirmationDaoImpl implements BookingConfirmationDao {
	static Logger log = Logger.getLogger(BookingConfirmationDaoImpl.class
			.getName());
	@Autowired
	private SessionFactory sessionFactory;

	public void addBookingItems(BookingItemsDTO bookingitem)
			throws HibernateException {
		log.debug("Entering the method addBookingItems ");
		List<Items> itemslist = new ArrayList<Items>();
		

		BookingDetails bookingdetails = new BookingDetails();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null != bookingitem) {
				{
					for (ItemsDto itemsdto : bookingitem.getItems()) {
						Items items = new Items();
						items.setName(itemsdto.getName());
						items.setPrice(Double.parseDouble(itemsdto.getPrice()));
						items.setQuantity(Short.parseShort(itemsdto
								.getQuantity()));
						items.setCode(itemsdto.getCode());
						itemslist.add(items);
					}
					bookingdetails.setItems(itemslist);
					bookingdetails.setOrderdate(bookingitem.getOrderdate());
					bookingdetails.setTimeSlot(bookingitem.getTimeslot());
					bookingdetails.setOrdername(bookingitem.getOrdername());
					bookingdetails.setTimestamp(bookingitem.getTimestamp());
					session.save(bookingdetails);

				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("The error inside" + e.getMessage());
		}

	}

	@SuppressWarnings({ "unchecked" })
	public List<BookingDetails> getListOfBookedItems(
			BookingItemsDTO bookingorderitems) {
		log.debug("Entering the method getListOfBookedItems ");
		List<BookingDetails> bookingdetails = null;

		{
			Criteria criteria = null;

			criteria = sessionFactory.getCurrentSession().createCriteria(
					BookingDetails.class);

			Criterion criteria1 = Restrictions.and(Restrictions.eq("timeSlot",
					bookingorderitems.getTimeslot()), Restrictions.eq(
					"timestamp", bookingorderitems.getTimestamp()));
			Criterion criteria2 = Restrictions.and(
					Restrictions.eq("orderdate",
							bookingorderitems.getOrderdate()),
					Restrictions.eq("ordername",
							bookingorderitems.getOrdername()));
			criteria.add(Restrictions.and(criteria1, criteria2));

			bookingdetails = criteria.list();

		}
		log.debug("The method getListOfBookedItems is returning "
				+ bookingdetails.toString());
		return bookingdetails;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ItemsDto> getListOfItems(BookingItemsDTO bookingdetails)
	{
		
		Criteria criteria = null;
		
		criteria = sessionFactory.getCurrentSession().createCriteria(
				BookingDetails.class);

		Criterion criteria1 = Restrictions.and(Restrictions.eq("timeSlot",
				bookingdetails.getTimeslot()), Restrictions.eq(
				"timestamp", bookingdetails.getTimestamp()));
		Criterion criteria2 = Restrictions.and(
				Restrictions.eq("orderdate",
						bookingdetails.getOrderdate()),
				Restrictions.eq("ordername",
						bookingdetails.getOrdername()));
		criteria.add(Restrictions.and(criteria1, criteria2));
       List<ItemsDto> itemdtolist = new ArrayList<>();
		List<BookingDetails> bookingdetailslist = null;
		bookingdetailslist= criteria.list();
		for(Iterator<BookingDetails> iterator=bookingdetailslist.iterator();iterator.hasNext();  )
		{
			BookingDetails bookingdetailsobject = iterator.next();
			for(Items items :bookingdetailsobject.getItems() )
			{
				ItemsDto itemsdto = new ItemsDto();
				itemsdto.setCode(items.getCode());
				itemsdto.setName(items.getName());
				itemsdto.setPrice(String.valueOf(items.getPrice()));
				itemsdto.setQuantity(String.valueOf(items.getQuantity()));
				itemdtolist.add(itemsdto);
			}
		}
		return itemdtolist;
	}

	
}
