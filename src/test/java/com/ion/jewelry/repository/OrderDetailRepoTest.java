package com.ion.jewelry.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.entity.OrderDetail;
import com.ion.jewelry.model.entity.OrderGroup;
import com.ion.jewelry.model.enums.OrderProductState;

public class OrderDetailRepoTest extends JewelryProjectApplicationTests {
	
	@Autowired
	private OrderDetailRepository orderDetailRepo;
	
	@Autowired
	private OrderGroupRepository orderGroupRepo;
	
	@Autowired
	private ItemRepository itemRepo;
	
	//@Test
	public void create() {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderCount(3);
		orderDetail.setOrderProductState(OrderProductState.BEFORE_BANK_TRANSFER);
		
		Optional<Item> optional1 = itemRepo.findById(1L);
		optional1.ifPresent(item -> {
			orderDetail.setOrderPrice(item.getPrice().multiply(new BigDecimal(3)));
			orderDetail.setItem(item);
		});
		
		Optional<OrderGroup> optional2 = orderGroupRepo.findById(2L);
		optional2.ifPresent(orderGroup -> {
			orderDetail.setOrderGroup(orderGroup);
		});
		
		OrderDetail newOrderDetail = orderDetailRepo.save(orderDetail);
		Assertions.assertNotNull(newOrderDetail);
	}
	
	@Test
	@Transactional
	public void selectRead() {
		Optional<OrderDetail> optional = orderDetailRepo.findById(1L);
		Assertions.assertTrue(optional.isPresent());
		
		optional.ifPresent(orderDetail -> {
			System.out.println("read data => " + orderDetail.toString());
		});
	}
}
