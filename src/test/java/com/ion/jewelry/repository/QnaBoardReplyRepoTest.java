package com.ion.jewelry.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.QnaBoard;
import com.ion.jewelry.model.entity.QnaBoardReply;

public class QnaBoardReplyRepoTest extends JewelryProjectApplicationTests {

	@Autowired
	private QnaBoardReplyRepository qnaReplyRepo;

	@Autowired
	private QnaBoardRepository qnaBoardRepo;

	//@Test
	public void create() {
		QnaBoardReply reply = new QnaBoardReply();
		reply.setWriter("test02");
		reply.setContent("테스트 댓글 입니다.");

		Optional<QnaBoard> optional = qnaBoardRepo.findById(1L);
		optional.ifPresent(qnaBoard -> {
			reply.setQnaBoard(qnaBoard);
		});

		QnaBoardReply newReply = qnaReplyRepo.save(reply);
		Assertions.assertNotNull(newReply);
	}

	//@Test
	public void selectRead() {
		Optional<QnaBoardReply> optional = qnaReplyRepo.findById(1L);
		optional.ifPresent(reply -> {
			System.out.println("read data -> " + reply.toString());
		});
	}

	@Test
	public void allRead() {
		List<QnaBoardReply> replyList = qnaReplyRepo.findAll();
		replyList.forEach(reply -> {
			System.out.println("read data -> " + reply.toString());
		});
	}
}
