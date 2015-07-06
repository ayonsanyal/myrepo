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
@Table(name = "Cartons")
public class Cartons implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long CartonsId;
	
	private Double volume;
	private List<ItemsOngoing> itemsongoing;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Cartons_Id")
	public long getCartonsId() {
		return CartonsId;
	}
	public void setCartonsId(long cartonsId) {
		CartonsId = cartonsId;
	}
	@Column(name="volume")
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}

	@OneToMany
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
	@JoinTable(name = "carton_items", joinColumns = @JoinColumn(name = "Item_Id"), inverseJoinColumns = @JoinColumn(name = "id"))
	public List<ItemsOngoing> getItemsongoing() {
		return itemsongoing;
	}
	public void setItemsongoing(List<ItemsOngoing> itemsongoing) {
		this.itemsongoing = itemsongoing;
	}
	
	

}
