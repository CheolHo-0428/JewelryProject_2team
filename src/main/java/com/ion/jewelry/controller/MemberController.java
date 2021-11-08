package com.ion.jewelry.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.ItemRequest;
import com.ion.jewelry.model.network.request.MemberRequest;
import com.ion.jewelry.model.network.response.ItemResponse;
import com.ion.jewelry.model.network.response.MemberGroupOrderInfoResponse;
import com.ion.jewelry.model.network.response.MemberResponse;
import com.ion.jewelry.repository.RoleRepository;
import com.ion.jewelry.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/jewelry/member")
public class MemberController extends AABaseController<MemberRequest, MemberResponse, Member> {
	@Autowired
	MemberService memberService;
	
	@Override
	@GetMapping("/paging") // http://localhost:8000//jewelry/member/paging?page=0
	public Header<List<MemberResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10) Pageable pageable) {
		log.info("{}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	//특정회원에 해당되는 주문정보들 조회
	@GetMapping("/{id}/orderGroupInfo") // http://localhost:8000/jewelry/member/1/orderGroupInfo
	public Header<MemberGroupOrderInfoResponse> orderGroupInfo(@PathVariable Long id){
		log.info("read id: {}", id);
		return memberService.orderGroupInfo(id);
	}
	@GetMapping("/searchAccount") // http://localhost:8000/jewelry/member/searchMemberNo?keyword=test&page=0
	public Header<List<MemberResponse>> searchAccount(@PathParam("keyword")String keyword, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		return memberService.searchAccount(keyword, pageable);
	}
	@GetMapping("/searchName")// http://localhost:8000/jewelry/member/searchName?keyword=test&page=0
	public Header<List<MemberResponse>> searchName(@PathParam("keyword")String keyword, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		return memberService.searchName(keyword, pageable);
	}
	@PutMapping("/update")
	public Header<MemberResponse> update(@RequestBody MemberRequest request) {

		Header<MemberRequest> result = new Header<MemberRequest>();
		result.setData(request);

		return memberService.updateMemo(result);
		
	}
	
	//이부분 테스트 해보시라고 만든거라 나중에 제거예정입니다!
	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/all")
	public String allAccess() {

		System.out.println("all 권한 들어옴");

		return "Public Content.";
	}

	@PostMapping("/user")						//유저권한을 넣을 경우 
	@PreAuthorize("hasRole('USER')")			//USER가아닌 권한의 이용자 들어올시 forbidden 호출
	public String userAccess() {				//이런식으로 화면 연결되는  URL호출하면 됩니다.
												//enum의 ROLE_USER인데 왜 USER만 적은 이유는 hasRole()자체에서 ROLE이 들어가 있기때문에 이렇게 명명해서 데이터 가져옵니다.
		System.out.println("user call~");		

		return "User Content.";
	}

	@GetMapping("/admin")						//관리자권한을 넣는경우
	@PreAuthorize("hasRole('ADMIN')")			//ADMIN이 아닌 권한의 이용자 들어올시 forbidden 호출
	public String adminAccess() {

		System.out.println("admin !!!");

		return "Admin Board.";
	}

}
