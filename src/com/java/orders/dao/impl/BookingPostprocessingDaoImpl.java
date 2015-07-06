package com.java.orders.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.java.orders.dao.BookingPostproccessingDao;
import com.java.orders.model.ItemDetail;

@Repository("bookingpostprocessingdao")
public class BookingPostprocessingDaoImpl implements BookingPostproccessingDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<ItemDetail> getitemdetails(List itemdetails)throws HibernateException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ItemDetail.class);
		Criterion criterion1 = Restrictions.in("code", itemdetails);
		criteria.add(criterion1);
		List<ItemDetail> itemdetaillist =criteria.list();
		return itemdetaillist;

	}

}
