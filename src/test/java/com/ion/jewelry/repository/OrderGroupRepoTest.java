package com.ion.jewelry.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.entity.OrderGroup;
import com.ion.jewelry.model.enums.OrderProductState;
import com.ion.jewelry.model.enums.PayAccount;
import com.ion.jewelry.model.enums.PayMethod;

public class OrderGroupRepoTest extends JewelryProjectApplicationTests{

	@Autowired
	private OrderGroupRepository orderGroupRepo;
	
	@Autowired
	private MemberRepository memberRepo;
		
	//@Test
	public void create() {
		OrderGroup orderGroup = new OrderGroup();
		orderGroup.setTotalPrice(BigDecimal.valueOf(1500000));
		orderGroup.setTotalCount(3);
		orderGroup.setDeliveryMessage("경비실에 맡겨주세요");
		orderGroup.setResipient("테스터01");
		orderGroup.setPhone("010-1111-1111");
		orderGroup.setOrderProductState(OrderProductState.READY);
		orderGroup.setPostCode("0001");
		orderGroup.setAddress("서울특별시 송파구 중대로 135");
		orderGroup.setDetailAddress("IT벤처타워 서관 12층");
		orderGroup.setPayMethod(PayMethod.BANK_TRANSFER);
		orderGroup.setDepositor("테스터01");
		orderGroup.setPayAccount(PayAccount.IBK);
		
		//String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		orderGroup.setArrivalDate(LocalDateTime.now().plusDays(2));
		
		Optional<Member> member = memberRepo.findById(1L);
		member.ifPresent(findMember -> {
			orderGroup.setMember(findMember);
		});
						
		OrderGroup newOrderGroup = orderGroupRepo.save(orderGroup);
		Assertions.assertNotNull(newOrderGroup);
	}
	
	//@Test
	//@Transactional
	public void read() {
		Optional<OrderGroup> orderGroup = orderGroupRepo.findById(2L);
		Assertions.assertTrue(orderGroup.isPresent());
		
		orderGroup.ifPresent(findOrderGroup -> {
			System.out.println("OrderGroup read값 => " + findOrderGroup.toString());
		});
	}
	
	@Test
	public void update() {
		Optional<OrderGroup> optional = orderGroupRepo.findById(1L);
		
		optional.ifPresent(orderGroup -> {
			OrderGroup updateOrderGroup = new OrderGroup()
					.setId(orderGroup.getId())
					.setTotalPrice(BigDecimal.valueOf(267000))
					.setTotalCount(orderGroup.getTotalCount())
					.setDeliveryMessage(orderGroup.getDeliveryMessage())
					.setResipient(orderGroup.getResipient())
					.setPhone(orderGroup.getPhone())
					.setOrderProductState(orderGroup.getOrderProductState())
					.setPostCode(orderGroup.getPostCode())
					.setAddress(orderGroup.getAddress())
					.setDetailAddress(orderGroup.getDetailAddress())
					.setPayAccount(orderGroup.getPayAccount())
					.setDepositor(orderGroup.getDepositor())
					.setPayMethod(orderGroup.getPayMethod())
					.setArrivalDate(orderGroup.getArrivalDate())
					.setMember(orderGroup.getMember());
			
			orderGroupRepo.save(updateOrderGroup);
		});		
	}
	
	
}
