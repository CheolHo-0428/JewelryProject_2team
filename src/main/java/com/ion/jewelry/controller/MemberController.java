package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.MemberRequest;
import com.ion.jewelry.model.network.response.MemberResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/member")
public class MemberController extends AABaseController<MemberRequest, MemberResponse, Member> {

	@Override
	@GetMapping("/paging") // http://localhost:8000//jewelry/member/paging?page=0
	public Header<List<MemberResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		
		log.info("{}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	
}
