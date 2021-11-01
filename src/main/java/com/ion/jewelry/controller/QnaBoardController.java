package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.QnaBoard;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.QnaBoardRequest;
import com.ion.jewelry.model.network.response.QnaBoardReplyInfoResponse;
import com.ion.jewelry.model.network.response.QnaBoardResponse;
import com.ion.jewelry.service.QnaBoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/qnaBoard")
public class QnaBoardController extends AABaseController<QnaBoardRequest, QnaBoardResponse, QnaBoard> {
	
	@Autowired
	private QnaBoardService qnaService;
	
	@Override
	@GetMapping("/paging") // http://localhost:8000/jewelry/qnaBoard/paging?page=0
	public Header<List<QnaBoardResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		log.info("페이징 정보: {}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	@GetMapping("/{id}/replyInfo") // http://localhost:8000/jewelry/qnaBoard/1/replyInfo
	public Header<QnaBoardReplyInfoResponse> replyInfo(@PathVariable Long id){
		log.info("read id : {}", id);
		return qnaService.replyInfo(id);
	}

	
}
