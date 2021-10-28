package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.entity.Role;
import com.ion.jewelry.model.enums.ERole;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.MemberRequest;
import com.ion.jewelry.model.network.response.MemberResponse;
import com.ion.jewelry.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/jewelry/member")
public class MemberController extends AABaseController<MemberRequest, MemberResponse, Member> {

	@Override
	@GetMapping("/paging") // http://localhost:8000//jewelry/member/paging?page=0
	public Header<List<MemberResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10) Pageable pageable) {

		log.info("{}", pageable);
		return baseService.pagingRead(pageable);
	}
	//이부분 테스트 해보시라고 만든거라 나중에 제거예정입니다!
	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/all")
	public String allAccess() {

		System.out.println("all 권한 들어옴");

		// 이코드 변경하고 싶은데.. data.sql에 넣게되면 ddl옵션을 못써서 문제이고,,
		// 실행시점 어플리케이션쪽에 실행시키고 싶은데 그방법을 아직모르겠음.. 그래서 repo에 생성자 생성해서 update되는식으로 처리했음.
		// 이 URL은 초기에 roles테이블에 데이터 넣기위해 처음에만 만들어놓으면 spring.jpa.hibernate.ddl-auto옵션이 create가 아닌이상 그대로 남아있다.
		
		Role role1 = new Role(1, ERole.ROLE_USER);
		Role role2 = new Role(2, ERole.ROLE_ADMIN);

		roleRepository.save(role1);
		roleRepository.save(role2);

		return "Public Content.";
	}

	@GetMapping("/user")						//유저권한을 넣을 경우 
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
