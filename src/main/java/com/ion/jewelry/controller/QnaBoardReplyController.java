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

import com.ion.jewelry.model.entity.QnaBoardReply;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.QnaBoardReplyRquest;
import com.ion.jewelry.model.network.response.QnaBoardReplyResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/qnaBoardReply")
@CrossOrigin("http://localhost:8080/")
public class QnaBoardReplyController extends AABaseController<QnaBoardReplyRquest, QnaBoardReplyResponse, QnaBoardReply> {
	
	@Override
	@GetMapping("/paging") // http://localhost:8000/jewelry/qnaBoardReply/paging?page=0
	public Header<List<QnaBoardReplyResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		
		log.info("페이징 정보 : {}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	@PostMapping("/reg")
	public Header<QnaBoardReplyResponse> create(@RequestBody QnaBoardReplyRquest request) {
		
		Header<QnaBoardReplyRquest> result = new Header<QnaBoardReplyRquest>();
		result.setData(request);
		
		return baseService.create(result);
	}
	
	@PutMapping("/update")
	public Header<QnaBoardReplyResponse> update(@RequestBody QnaBoardReplyRquest request) {
		
		Header<QnaBoardReplyRquest> result = new Header<QnaBoardReplyRquest>();
		result.setData(request);
		
		return baseService.update(result);
	}
}
