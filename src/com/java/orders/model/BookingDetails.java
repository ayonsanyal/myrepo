package com.java.orders.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "BookingDetail")
public class BookingDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private long bookingId;
	private List<Items> items;
	private String timeSlot;
	private String orderdate;
	private String ordername;
	private String timestamp;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOOKING_ID")
	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	@Column(name = "TIMESLOT")
	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Column(name = "BOOKING_DATE")
	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	@Column(name = "ORDER_NAME")
	public String getOrdername() {
		return ordername;
	}

	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}
	
	@OneToMany
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
	
	@JoinTable(name = "bookingDetail_item", joinColumns = @JoinColumn(name = "ITEM_ID"), inverseJoinColumns = @JoinColumn(name = "ITEMS_ID"))

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}
   @Column(name="TIMEOFBOOKING")
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
