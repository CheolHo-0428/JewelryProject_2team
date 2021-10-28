package com.ion.jewelry.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.Category;
import com.ion.jewelry.model.entity.Item;

public class ItemRepositoryTest extends JewelryProjectApplicationTests {
	
	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	//@Test
	public void create() {
		Item item = new Item();
		item.setName("Flow of water R");
		item.setPrice(BigDecimal.valueOf(89000));
		item.setStock(100);
		Optional<Category> optional = categoryRepo.findById(4L);
		optional.ifPresent(category -> {
			item.setCategory(category);
		});
		Item newItem = itemRepo.save(item);
		Assertions.assertNotNull(newItem);
		
		Item item1 = new Item();
		item1.setName("Purple jam R");
		item1.setPrice(BigDecimal.valueOf(54000));
		item1.setStock(100);
		Optional<Category> optional1 = categoryRepo.findById(4L);
		optional1.ifPresent(category -> {
			item1.setCategory(category);
		});
		Item newItem1 = itemRepo.save(item1);
		Assertions.assertNotNull(newItem1);
	}
	
	//@Test
	//@Transactional
	public void selectRead() {
		Optional<Item> optional = itemRepo.findById(1L);
		optional.ifPresent(item -> {
			System.out.println("read data => " + item.toString());
		});
	}
	
	@Test
	@Transactional
	public void allRead() {
		List<Item> itemList = itemRepo.findAll();
		itemList.forEach(item -> {
			System.out.println("read data => " + item.toString());
		});
	}
}
