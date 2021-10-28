package com.ion.jewelry.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.ReviewBoard;
import com.ion.jewelry.model.entity.ReviewBoardReply;

public class ReviewBoardReplyRepoTest extends JewelryProjectApplicationTests {
	
	@Autowired
	private ReviewBoardReplyRepository reviewReplyRepo;
	
	@Autowired
	private ReviewBoardRepository reviewBoardRepo;
	
	//@Test
	public void create() {
		ReviewBoardReply reply = new ReviewBoardReply();
		reply.setWriter("test01");
		reply.setContent("테스트 댓글 입니다.");
		
		Optional<ReviewBoard> optional = reviewBoardRepo.findById(1L);
		optional.ifPresent(reviewBoard -> {
			reply.setReviewBoard(reviewBoard);
		});
		
		ReviewBoardReply newReply = reviewReplyRepo.save(reply);
		Assertions.assertNotNull(newReply);
	}
	
	//@Test
	public void selectRead() {
		Optional<ReviewBoardReply> optional = reviewReplyRepo.findById(1L);
		optional.ifPresent(reply -> {
			System.out.println("read data -> " + reply.toString());
		});
	}
	
	@Test
	public void allRead() {
		List<ReviewBoardReply> replyList = reviewReplyRepo.findAll();
		replyList.forEach(reply -> {
			System.out.println("read data -> " + reply.toString());
		});
	}
}
