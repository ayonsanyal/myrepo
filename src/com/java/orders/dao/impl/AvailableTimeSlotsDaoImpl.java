package com.java.orders.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.java.orders.dao.AvailableTimeSlotsDao;
import com.java.orders.model.TimeSlots;

@Repository("orderconfirmationdao")
public class AvailableTimeSlotsDaoImpl implements AvailableTimeSlotsDao {
	static Logger log = Logger.getLogger(AvailableTimeSlotsDaoImpl.class.getName());
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<TimeSlots> getListofTimeSlotsFutureDate()
			throws HibernateException {
		log.debug("Entering the method getListofTimeSlotsFutureDate ");
		List<TimeSlots> timeslots = null;

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				TimeSlots.class);

		timeslots = criteria.list();
		log.debug("The method getBookedOrders is returning "+timeslots.toString());
		return timeslots;
	}

	@SuppressWarnings("unchecked")
	public List<TimeSlots> getListofTimeSlots(String todaystimeinhours)throws HibernateException {
		log.debug("Entering the method getListofTimeSlots ");
		List<TimeSlots> timeslots = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				TimeSlots.class);
		criteria.add(Restrictions.ge("timeSlotStart", Integer.parseInt(todaystimeinhours)));
		timeslots = criteria.list();
		log.debug("The method getBookedOrders is returning "+timeslots.toString());
		return timeslots;
	}
}
