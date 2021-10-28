package com.ion.jewelry.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.NoticeBoard;
import com.ion.jewelry.model.enums.YesNo;

public class NoticeBoardRepoTest extends JewelryProjectApplicationTests {
	@Autowired
	private NoticeBoardRepository noticeRepo;

	//@Test
	public void create() {
		NoticeBoard noticeBoard = new NoticeBoard();
		noticeBoard.setTitle("테스트 공지사항 02 ");
		noticeBoard.setContent("테스트 공지사항 02 입니다");
		noticeBoard.setWriter("admin02");
		noticeBoard.setPrivateOk(YesNo.NO);
		noticeBoard.setDeleteCheck(YesNo.NO);


		NoticeBoard newNoticeBoard = noticeRepo.save(noticeBoard);
		Assertions.assertNotNull(newNoticeBoard);
	}

	//@Test
	public void selectRead() {
		Optional<NoticeBoard> optional = noticeRepo.findById(1L);
		optional.ifPresent(noticeBoard -> {
			System.out.println("read data => " + noticeBoard.toString());
		});
	}

	@Test
	public void allRead() {
		List<NoticeBoard> noticeBoardList = noticeRepo.findAll();
		noticeBoardList.forEach(noticeBoard -> {
			System.out.println("read data => " + noticeBoard.toString());
		});
	}
}
