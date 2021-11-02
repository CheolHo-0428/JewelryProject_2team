package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.ReviewBoardReply;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.ReviewBoardReplyRequest;
import com.ion.jewelry.model.network.response.ReviewBoardReplyResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/reviewBoardReply")
@CrossOrigin("http://localhost:8080/")
public class ReviewBoardReplyController extends 
	AABaseController<ReviewBoardReplyRequest, ReviewBoardReplyResponse, ReviewBoardReply> {
	
	@Override
	@GetMapping("/paging") //http://localhost:8000/jewelry/reviewBoardReply/paging?page=0
	public Header<List<ReviewBoardReplyResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		log.info("페이징 정보: {}", pageable);
		return baseService.pagingRead(pageable);
	}

	@PostMapping("/reg")
	public Header<ReviewBoardReplyResponse> create(@RequestBody ReviewBoardReplyRequest request) {
		
		Header<ReviewBoardReplyRequest> result = new Header<ReviewBoardReplyRequest>();
		result.setData(request);
		
		return baseService.create(result);
	}
	
	@PutMapping("/update")
	public Header<ReviewBoardReplyResponse> update(@RequestBody ReviewBoardReplyRequest request) {
		
		Header<ReviewBoardReplyRequest> result = new Header<ReviewBoardReplyRequest>();
		result.setData(request);
		
		return baseService.update(result);
	}
}
