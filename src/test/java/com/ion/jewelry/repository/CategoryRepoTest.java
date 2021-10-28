package com.ion.jewelry.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.Category;
import com.ion.jewelry.model.enums.CategoryType;

public class CategoryRepoTest extends JewelryProjectApplicationTests{
	@Autowired
	private CategoryRepository repo;
	
	//@Test
	public void create() {
		/*
		Category category = Category.builder()
				.categoryType(CategoryType.BRACELET)
				.build();
		
		Category newCategory = repo.save(category);
		Assertions.assertNotNull(newCategory);
		*/
		
		Category category1 = Category.builder()
				.categoryType(CategoryType.EARRINGS)
				.build();
		Category newCategory1 = repo.save(category1);
		Assertions.assertNotNull(newCategory1);
		
		Category category2 = Category.builder()
				.categoryType(CategoryType.NECKLACE)
				.build();
		Category newCategory2 = repo.save(category2);
		Assertions.assertNotNull(newCategory2);
		
		Category category3 = Category.builder()
				.categoryType(CategoryType.RING)
				.build();
		Category newCategory3 = repo.save(category3);
		Assertions.assertNotNull(newCategory3);
	}
	
	//@Test
	public void selectRead() {
		Optional<Category> optional = repo.findById(1L);
		Assertions.assertTrue(optional.isPresent());
		
		optional.ifPresent(findCategory -> {
			System.out.println("read Data => " + findCategory.toString());
		});
		
	}
	
	@Test
	public void allRead() {
		List<Category> categoryList = repo.findAll();
		Assertions.assertNotNull(categoryList);
		
		categoryList.forEach(category -> {
			System.out.println("read Data => " + category.toString());
		});
		
	}
}
