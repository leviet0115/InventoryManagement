package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**For populate database
import com.example.demo.model.Brand;
import com.example.demo.model.Category;
import com.example.demo.model.Item;
**/

import com.example.demo.model.BrandRepository;

import com.example.demo.model.CategoryRepository;
import com.example.demo.model.ItemRepository;
import com.example.demo.model.CCUserRepository;


@SpringBootApplication
public class FinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BrandRepository bRepo, CategoryRepository cRepo, ItemRepository iRepo, CCUserRepository ccUserRepo) {
		return (args) -> {
			/*
			Brand brand1 = new Brand("Royal Canin");
			Brand brand2 = new Brand("No brand");
			Brand brand3 = new Brand("Compact care");
			bRepo.save(brand1);
			bRepo.save(brand2);
			bRepo.save(brand3);
			
			Category cate1 = new Category("Food");
			Category cate2 = new Category("Toy");
			Category cate3 = new Category("Hygiene");
			cRepo.save(cate1);
			cRepo.save(cate2);
			cRepo.save(cate3);
			
			
			Item item1 = new Item("Chicken nugget", 12, 100, "10/02/2022", cate1, brand1);
			Item item2 = new Item("Salmon snack", 1, 50, "15/03/2022", cate1, brand1);
			Item item3 = new Item("Unscented cat sand", 5, 15000, "", cate3, brand3);
			Item item4 = new Item("Plastic mouse", 1, 50, "", cate2, brand2);
			iRepo.save(item1);
			iRepo.save(item2);
			iRepo.save(item3);
			iRepo.save(item4);
			*/
		};
	}
}
