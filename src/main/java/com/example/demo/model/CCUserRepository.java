package com.example.demo.model;
import org.springframework.data.repository.CrudRepository;

public interface CCUserRepository extends CrudRepository<CatComUser, Long>{
	CatComUser findByUsername(String username);
}
