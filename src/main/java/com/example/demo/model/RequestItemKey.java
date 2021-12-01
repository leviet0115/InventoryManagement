package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RequestItemKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1106409085770906773L;

	@Column(name = "item_id")
	Long itemId;
	
	@Column(name = "request_id")
	Long requestId;
	
	public RequestItemKey() {}
	
	public RequestItemKey(Long itemId, Long requestId) {
		this.itemId = itemId;
		this.requestId = requestId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, requestId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestItemKey other = (RequestItemKey) obj;
		return Objects.equals(itemId, other.itemId) && Objects.equals(requestId, other.requestId);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
