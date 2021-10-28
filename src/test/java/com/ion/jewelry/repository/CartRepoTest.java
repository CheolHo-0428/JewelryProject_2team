package com.ion.jewelry.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.Cart;
import com.ion.jewelry.model.entity.Item;

public class CartRepoTest extends JewelryProjectApplicationTests {

	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private ItemRepository itemRepo;
	
	//@Test
	public void create() {
		
		Cart cart = new Cart();
		cart.setItemCount(3);
		cart.setMemberId(1L);
		
		Optional<Item> optional = itemRepo.findById(1L);
		optional.ifPresent(item -> {
			cart.setItem(item);
		});
		
		Cart newCart = cartRepo.save(cart);
		Assertions.assertNotNull(newCart);
	}
	
	@Test
	@Transactional
	public void allRead() {
		List<Cart> cartList = cartRepo.findAll();
		cartList.forEach(cart -> {
			System.out.println("read Data -> " + cart.toString());
		});
	}
}
