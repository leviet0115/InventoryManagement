package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PurchaseRequest {
	//column with no relation
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long requestId;
	private String creator;
	

	//date columns
	private String createdTime;
	private String sentTime;
	private String approvedTime;
	private String purchasedTime;
	//column with relation
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "request")
	private List<RequestItem> requestItems;

	public PurchaseRequest() {
		this.createdTime = printDateTime(LocalDateTime.now()); 
		this.creator = "little kitty";
	}

	public String printDateTime(LocalDateTime ldt) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm" );
		return ldt.format(dateFormatter);
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public List<RequestItem> getRequestItems() {
		return requestItems;
	}

	public void setRequestItems(List<RequestItem> requestItems) {
		this.requestItems = requestItems;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getSentTime() {
		return sentTime;
	}

	public void setSentTime(String sentTime) {
		this.sentTime = sentTime;
	}

	public String getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	public String getPurchasedTime() {
		return purchasedTime;
	}

	public void setPurchasedTime(String purchasedTime) {
		this.purchasedTime = purchasedTime;
	}
	
}
