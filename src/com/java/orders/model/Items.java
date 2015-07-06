package com.java.orders.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Column;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "ITEM")
public class Items implements Serializable {

	private long itemId;
	private String name;
	private Short quantity;
	private Double price;
	private String code;

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEMS_ID", nullable = false, insertable = false, updatable = false)
	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	@Column(name = "Quantity")
	public Short getQuantity() {
		return quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}

	@Column(name = "Price")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "itemcode")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
