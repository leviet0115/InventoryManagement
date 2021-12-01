package com.example.demo.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class RequestItem {
	
	@EmbeddedId
	RequestItemKey key; 
	
	@ManyToOne
	@MapsId("itemId")
	@JoinColumn(name="item_id") 
	Item item;
	
	@ManyToOne
	@MapsId("requestId")
	@JoinColumn(name="request_id")
	PurchaseRequest request;
	
	boolean isApproved;
	int requestQty;

	public RequestItem() {}
	
	public RequestItem(Item item, PurchaseRequest request) {
		this.item = item;
		this.request = request;
		this.key = new RequestItemKey(item.getItemId(), request.getRequestId());
		this.requestQty = 1;
		this.isApproved = false;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public PurchaseRequest getRequest() {
		return request;
	}

	public void setRequest(PurchaseRequest request) {
		this.request = request;
	}

	public int getRequestQty() {
		return requestQty;
	}

	public void setRequestQty(int requestQty) {
		this.requestQty = requestQty;
	}

	public RequestItemKey getKey() {
		return key;
	}

	public void setKey(RequestItemKey key) {
		this.key = key;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
}
