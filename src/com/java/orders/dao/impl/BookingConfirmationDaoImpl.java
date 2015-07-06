package com.java.orders.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;

import org.hibernate.SessionFactory;
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
		Items items = new Items();

		BookingDetails bookingdetails = new BookingDetails();
		try {
			if (null != bookingitem) {
				{
					for (ItemsDto itemsdto : bookingitem.getItems()) {
						items.setName(itemsdto.getName());
						items.setPrice(Double.parseDouble(itemsdto.getPrice()));
						items.setQuantity(Short.parseShort(itemsdto
								.getQuantity()));

						itemslist.add(items);
					}
					bookingdetails.setItems(itemslist);

					bookingdetails.setTimeSlot(bookingitem.getTimeslot());
					bookingdetails.setOrdername(bookingitem.getOrdername());
					sessionFactory.getCurrentSession().saveOrUpdate(
							bookingdetails);
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("The error inside" + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<BookingDetails> getListOfBookedItems() {
		log.debug("Entering the method getListOfBookedItems ");
		List<BookingDetails> bookingdetails = null;

		{

			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(BookingDetails.class);

			bookingdetails = criteria.list();

		}
		log.debug("The method getListOfBookedItems is returning "+bookingdetails.toString());
		return bookingdetails;
	}
}
