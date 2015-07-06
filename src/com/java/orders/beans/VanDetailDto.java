package com.java.orders.beans;

import java.util.List;

import com.java.orders.model.Items;

public class VanDetailDto {
	
	private String  vanNumber;
	private long bookingId;
	private List<Carton> items;
	private String timeSlot;
	private String orderdate;
	private String ordername;
	public String getVanNumber() {
		return vanNumber;
	}
	public void setVanNumber(String vanNumber) {
		this.vanNumber = vanNumber;
	}
	public long getBookingId() {
		return bookingId;
	}
	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}
	public List<Carton> getItems() {
		return items;
	}
	public void setItems(List<Carton> items) {
		this.items = items;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getOrdername() {
		return ordername;
	}
	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}
	

}
