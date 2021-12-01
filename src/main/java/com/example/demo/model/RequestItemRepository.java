package com.example.demo.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RequestItemRepository extends CrudRepository<RequestItem, Long> {
	
	@Query(value = "SELECT u FROM RequestItem u WHERE u.key.itemId = ?1 and u.key.requestId = ?2")
	public Optional<RequestItem> findItemByKey(Long itemId, Long requestId);
}

