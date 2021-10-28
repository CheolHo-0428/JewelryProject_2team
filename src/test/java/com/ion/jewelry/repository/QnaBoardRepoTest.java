package com.ion.jewelry.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.entity.QnaBoard;
import com.ion.jewelry.model.enums.YesNo;

public class QnaBoardRepoTest extends JewelryProjectApplicationTests {

	@Autowired
	private QnaBoardRepository qnaRepo;

	@Autowired
	private ItemRepository itemRepo;

	//@Test
	public void create() {
		QnaBoard qnaBoard = new QnaBoard();
		qnaBoard.setTitle("테스트 Qna 2");
		qnaBoard.setContent("테스트 Qna 2 입니다");
		qnaBoard.setWriter("test02");
		qnaBoard.setPrivateOk(YesNo.NO);
		qnaBoard.setDeleteCheck(YesNo.NO);

		Optional<Item> optional = itemRepo.findById(1L);
		optional.ifPresent(item -> {
			qnaBoard.setItem(item);
		});

		QnaBoard newQnaBoard = qnaRepo.save(qnaBoard);
		Assertions.assertNotNull(newQnaBoard);
	}

	//@Test
	public void selectRead() {
		Optional<QnaBoard> optional = qnaRepo.findById(1L);
		optional.ifPresent(qnaBoard -> {
			System.out.println("read data => " + qnaBoard.toString());
		});
	}

	@Test
	public void allRead() {
		List<QnaBoard> qnaBoardList = qnaRepo.findAll();
		qnaBoardList.forEach(qnaBoard -> {
			System.out.println("read data => " + qnaBoard.toString());
		});
	}
}
