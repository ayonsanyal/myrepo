package com.java.orders.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ItemsOngoing")
public class ItemsOngoing implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long id;
	private Double length;
	private Double width;
	private Double height;
	private String code;
	private Short quantity;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(name="Length")
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	@Column(name="Width")
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	@Column(name="Height")
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	@Column(name="Code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="Quantity")
	public Short getQuantity() {
		return quantity;
	}
	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}
	

}
