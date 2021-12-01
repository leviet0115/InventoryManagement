package com.example.demo.model;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Item, Long> {

}
