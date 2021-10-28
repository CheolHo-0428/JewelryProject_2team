package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.NoticeBoard;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.NoticeBoardRequest;
import com.ion.jewelry.model.network.response.NoticeBoardResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/noticeBoard")
@CrossOrigin("http://localhost:8080/")
public class NoticeBoardController extends 
		AABaseController<NoticeBoardRequest, NoticeBoardResponse, NoticeBoard>{

	@Override
	@GetMapping("/paging") // http://localhost:8000//jewelry/noticeBoard/paging?page=0
	public Header<List<NoticeBoardResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10)
			Pageable pageable) {
		
		log.info("{}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	@PostMapping("/reg")
	public Header<NoticeBoardResponse> create(@RequestBody NoticeBoardRequest request) {
		
		Header<NoticeBoardRequest> result = new Header<NoticeBoardRequest>();
		result.setData(request);
		
		return baseService.create(result);
	}

	@Override
	@DeleteMapping("{id}")
	public Header delete(@PathVariable Long id) {
		
		return baseService.delete(id);
	}
	
	@PutMapping("/update")
	public Header<NoticeBoardResponse> update(@RequestBody NoticeBoardRequest request) {
		
		Header<NoticeBoardRequest> result = new Header<NoticeBoardRequest>();
		result.setData(request);
		
		return baseService.update(result);
	}

}
