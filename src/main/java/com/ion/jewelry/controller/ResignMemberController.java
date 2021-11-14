package com.ion.jewelry.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.ResignMember;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.ResignMemberRequest;
import com.ion.jewelry.model.network.response.ResignMemberResponse;
import com.ion.jewelry.service.ResignMemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/jewelry/resignMember")
public class ResignMemberController extends 
	AABaseController<ResignMemberRequest, ResignMemberResponse, ResignMember> {
	@Autowired
	ResignMemberService resignService;

	@Override
	@GetMapping("/paging")
	public Header<List<ResignMemberResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Direction.ASC, size = 10)
			Pageable pageable) {
		log.info("페이징 데이터: {}", pageable);
		return baseService.pagingRead(pageable);
	}
	@GetMapping("/searchAccount") // http://localhost:8000/jewelry/resignMember/searchAccount?keyword=test&page=0
	public Header<List<ResignMemberResponse>> searchAccount(@PathParam("keyword")String keyword, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		return resignService.searchAccount(keyword, pageable);
	}
	@GetMapping("/searchName")// http://localhost:8000/jewelry/resignMember/searchName?keyword=test&page=0
	public Header<List<ResignMemberResponse>> searchName(@PathParam("keyword")String keyword, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		return resignService.searchName(keyword, pageable);
	}
	
}
