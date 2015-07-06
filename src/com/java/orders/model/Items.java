package com.java.orders.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "ITEMS")
public class Items implements Serializable {

	private long itemId;
	private String name;
	private Short quantity;
	private Double price;
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEM_ID")
	public long getItemId() {
		return itemId;
	}

	
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
    @Column(name="Quantity")
		public Short getQuantity() {
		return quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}
@Column(name="Price")
	public Double getPrice() {
		return price;
	}

	
	public void setPrice(Double price) {
		this.price = price;
	}
@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
