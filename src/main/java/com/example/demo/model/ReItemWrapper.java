package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class ReItemWrapper {
	private List<RequestItem> reItems;

	public ReItemWrapper() {
		this.reItems = new ArrayList<RequestItem>();
	}

	public void addItem(RequestItem reItem) {
		this.reItems.add(reItem);
	}
	
	public List<RequestItem> getReItems() {
		return reItems;
	}

	public void setItems(List<RequestItem> reItems) {
		this.reItems = reItems;
	}

} 
