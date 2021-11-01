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

import com.ion.jewelry.model.entity.ReviewBoard;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.ReviewBoardReplyInfoResponse;
import com.ion.jewelry.model.network.request.ReviewBoardRequest;
import com.ion.jewelry.model.network.response.ReviewBoardResponse;
import com.ion.jewelry.service.ReviewBoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/reviewBoard")
public class ReviewBoardController extends 
		AABaseController<ReviewBoardRequest, ReviewBoardResponse, ReviewBoard> {

	@Autowired
	private ReviewBoardService reviewService;
	
	@Override
	@GetMapping("/paging") // http://localhost:8000/jewelry/reviewBoard/paging?page=0
	public Header<List<ReviewBoardResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		
		log.info("페이징 정보: {}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	//특정 리뷰에 해당하는 댓글들 조회
	@GetMapping("/{id}/replyInfo") // http://localhost:8000/jewelry/reviewBoard/1/replyInfo
	public Header<ReviewBoardReplyInfoResponse> replyInfo(@PathVariable Long id){
		log.info("read id: {}", id);
		return reviewService.replyInfo(id);
	}
}
