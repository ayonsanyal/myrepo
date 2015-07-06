package com.java.orders.beans;

import java.util.List;

public class BookingItemsDTO {

	private String ordername;
	private String timeslot;
	private List<ItemsDto> items;
	private String orderdate;

	public String getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(String timeslot) {
		this.timeslot = timeslot;
	}

	

	

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public List<ItemsDto> getItems() {
		return items;
	}

	public void setItems(List<ItemsDto> items) {
		this.items = items;
	}

	public String getOrdername() {
		return ordername;
	}

	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}

}
