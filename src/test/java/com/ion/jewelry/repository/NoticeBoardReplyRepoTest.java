package com.ion.jewelry.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.NoticeBoard;
import com.ion.jewelry.model.entity.NoticeBoardReply;

public class NoticeBoardReplyRepoTest extends JewelryProjectApplicationTests {
	@Autowired
	private NoticeBoardReplyRepository noticeReplyRepo;

	@Autowired
	private NoticeBoardRepository noticeBoardRepo;

	//@Test
	public void create() {
		NoticeBoardReply reply = new NoticeBoardReply();
		reply.setWriter("test02");
		reply.setContent("테스트 댓글 입니다.");

		Optional<NoticeBoard> optional = noticeBoardRepo.findById(1L);
		optional.ifPresent(noticeBoard -> {
			reply.setNoticeBoard(noticeBoard);
		});

		NoticeBoardReply newReply = noticeReplyRepo.save(reply);
		Assertions.assertNotNull(newReply);
	}

	//@Test
	public void selectRead() {
		Optional<NoticeBoardReply> optional = noticeReplyRepo.findById(1L);
		optional.ifPresent(reply -> {
			System.out.println("read data -> " + reply.toString());
		});
	}

	@Test
	public void allRead() {
		List<NoticeBoardReply> replyList = noticeReplyRepo.findAll();
		replyList.forEach(reply -> {
			System.out.println("read data -> " + reply.toString());
		});
	}
}
