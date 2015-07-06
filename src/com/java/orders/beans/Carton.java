package com.java.orders.beans;

import java.util.List;

public class Carton {
	
	private List<ItemDetailDto> items;
	private Double volume;
	public List<ItemDetailDto> getItems() {
		return items;
	}
	public void setItems(List<ItemDetailDto> items) {
		this.items = items;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	
	

}
