package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.ResignMember;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.ResignMemberRequest;
import com.ion.jewelry.model.network.response.ResignMemberResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/resignMember")
public class ResignMemberController extends 
	AABaseController<ResignMemberRequest, ResignMemberResponse, ResignMember> {

	@Override
	@GetMapping("/paging")
	public Header<List<ResignMemberResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Direction.ASC, size = 10)
			Pageable pageable) {
		log.info("페이징 데이터: {}", pageable);
		return baseService.pagingRead(pageable);
	}

	
}
