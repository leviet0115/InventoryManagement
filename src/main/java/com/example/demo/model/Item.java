package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Item {
	// columns with no relation
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long itemId; 
	private String name;
	private int stockQty;
	private int weight;
	private String createdDate;
	private String expiryDate;
	@Column(nullable = true)
	private String image;

	// columns with relation
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "brandId")
	private Brand brand;

	@OneToMany (cascade = CascadeType.ALL, mappedBy = "item")
	List<RequestItem> requestItems;

	public Item() {
		this.createdDate = printDate(LocalDateTime.now());
		this.stockQty = 0;
	}

	public Item(String name, int stockQty, int weight, String expiryDate,
			Category category, Brand brand) {
		this.name = name;
		this.stockQty = stockQty;
		this.weight = weight;
		this.createdDate = printDate(LocalDateTime.now());
		this.expiryDate = expiryDate;
		this.category = category;
		this.brand = brand;
	}



	public static String printDate(LocalDateTime ldt) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return ldt.format(dateFormatter);
	}

	public Item(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStockQty() {
		return stockQty;
	}

	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public List<RequestItem> getRequestItems() {
		return requestItems;
	}

	public void setRequestItems(List<RequestItem> requestItems) {
		this.requestItems = requestItems;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public void addStock(int newStock) {
		this.stockQty += newStock;
	}

	@Transient
	public String getPhotosImagePath() {
		if (image == null || itemId == null) return null;
		return "https://raw.githubusercontent.com/leviet0115/InventoryManagement/Master/" + itemId + "/" + image;
	}

}




