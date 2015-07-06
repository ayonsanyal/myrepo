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

import com.java.orders.beans.Carton;

@Entity
@Table(name = "VANDETAILS")
public class VanDetails implements Serializable {
	private long id;
	private String vanNumber;
	private long bookingId;
	private List<Cartons> items;
	private String timeSlot;
	private String orderdate;
	private String ordername;

	private static final long serialVersionUID = 1L;

	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "BookingId")
	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	@Column(name = "TimeSlot")
	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Column(name = "OrderDate")
	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	@Column(name = "OrderName")
	public String getOrdername() {
		return ordername;
	}

	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}

	@OneToMany
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
	@JoinTable(name = "van_cartons", joinColumns = @JoinColumn(name = "Carton_Id"), inverseJoinColumns = @JoinColumn(name = "Cartons_Id"))
	public List<Cartons> getItems() {
		return items;
	}

	public void setItems(List<Cartons> items) {
		this.items = items;
	}
	
	@Column(name="VanNumber")
	public String getVanNumber() {
		return vanNumber;
	}

	public void setVanNumber(String vanNumber) {
		this.vanNumber = vanNumber;
	}

}
