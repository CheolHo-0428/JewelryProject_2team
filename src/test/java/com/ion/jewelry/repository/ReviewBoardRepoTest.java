package com.ion.jewelry.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.entity.ReviewBoard;
import com.ion.jewelry.model.enums.YesNo;

public class ReviewBoardRepoTest extends JewelryProjectApplicationTests {
	
	@Autowired
	private ReviewBoardRepository reviewRepo;
	
	@Autowired
	private ItemRepository itemRepo;
	
	//@Test
	public void create() {
		ReviewBoard reviewBoard = new ReviewBoard();
		reviewBoard.setTitle("테스트 리뷰2");
		reviewBoard.setContent("테스트 리뷰2 입니다");
		reviewBoard.setWriter("test01");
		reviewBoard.setPrivateOk(YesNo.NO);
		reviewBoard.setDeleteCheck(YesNo.NO);
		
		Optional<Item> optional = itemRepo.findById(1L);
		optional.ifPresent(item ->{
			reviewBoard.setItem(item);
		});
		
		ReviewBoard newReviewBoard = reviewRepo.save(reviewBoard);
		Assertions.assertNotNull(newReviewBoard);
	}
	
	@Test
	public void selectRead() {
		Optional<ReviewBoard> optional = reviewRepo.findById(1L);
		optional.ifPresent(reviewBoard -> {
			System.out.println("read data => " + reviewBoard.toString());
		});
	}
	
	//@Test
	public void allRead() {
		List<ReviewBoard> reviewBoardList = reviewRepo.findAll();
		reviewBoardList.forEach(reviewBoard -> {
			System.out.println("read data => " + reviewBoard.toString());
		});
	}
	
}
