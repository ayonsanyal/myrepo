package com.java.orders.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEMDETAIL")
public class ItemDetail implements Serializable {
	
	private long itemdetailId;
	private Double length;
	private Double width;
	private Double height;
	private String code;
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEMDETAIL_ID")
    public long getItemdetailId() {
		return itemdetailId;
	}
	public void setItemdetailId(long itemdetailId) {
		this.itemdetailId = itemdetailId;
	}
	
	@Column(name="LENGTH")
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	
	@Column(name="WIDTH")
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
	
	@Column(name="ITEMCODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	

}
