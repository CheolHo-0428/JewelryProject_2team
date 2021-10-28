package com.ion.jewelry.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.enums.MemberStatus;

public class MemberRepoTest extends JewelryProjectApplicationTests {
	
	@Autowired
	private MemberRepository repo;
	
	//@Test
	public void create() {
		/*
		Member member = Member.builder()
				.id("test01")
				.password("12345")
				.email("test01@gamil.com")
				.phone("010-1111-1111")
				.postCode("0001")
				.address("서울특별시서울 송파구 중대로 135")
				.detailAddress("IT벤처타워 서관 12층")
				.status(MemberStatus.REGISTERED)
				.createdAt(LocalDateTime.now())
				.build();
		*/
		Member member = new Member();
				member.setAccount("test03");
				member.setName("테스터03");
				member.setPassword("12345");
				member.setEmail("test03@gamil.com");
				member.setPhone("010-1111-3333");
				member.setPostCode("0003");
				member.setAddress("서울특별시 송파구 중대로 135");
				member.setDetailAddress("IT벤처타워 서관 12층");
				member.setStatus(MemberStatus.REGISTERED);
				
		System.out.println("계정: " + member.getAccount() );
		
		Member newMember = repo.save(member);
		Assertions.assertNotNull(newMember);
	}
	
	//@Test
	public void selectRead() {
		Optional<Member> member = repo.findById(1L);
		Assertions.assertTrue(member.isPresent());
		
		member.ifPresent(readMember -> {
			System.out.println("read member => " + readMember.toString());
		});
	}
	
	@Test
	public void selectAll() {
		List<Member> memberList = repo.findAll();
		Assertions.assertNotNull(memberList);
		
		memberList.forEach(member -> {
			System.out.println("member read =>" + member);
		});
		
	}
	
	
}
