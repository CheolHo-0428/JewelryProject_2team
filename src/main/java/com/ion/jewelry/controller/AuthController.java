package com.ion.jewelry.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.jwt.JwtUtils;
import com.ion.jewelry.jwt.MemberSecurity;
import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.entity.Role;
import com.ion.jewelry.model.enums.ERole;
import com.ion.jewelry.model.enums.ObjectStatus;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.LoginRequest;
import com.ion.jewelry.model.network.request.MemberRequest;
import com.ion.jewelry.model.network.request.SignupRequest;
import com.ion.jewelry.model.network.response.JwtResponse;
import com.ion.jewelry.model.network.response.MemberResponse;
import com.ion.jewelry.model.network.response.MessageResponse;
import com.ion.jewelry.repository.MemberRepository;
import com.ion.jewelry.repository.RoleRepository;
import com.ion.jewelry.service.MemberService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/jewelry/auth")
public class AuthController extends AABaseController<MemberRequest, MemberResponse, Member> {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	MemberService memberService;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		// authenticataionManager.authenticate() 메소드로 검사하며 Security 내장 기능으로 수행함
		Authentication authentication = authenticationManager.authenticate(
				// UserDetailsService를 호출하는 것은 AuthTokenFilter와 동일하다 볼 수 있으며, 비밀번호 검사를 하는 추가 작업인
				// PasswordEncoder도 추가로 호출함
				new UsernamePasswordAuthenticationToken(loginRequest.getAccount(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		MemberSecurity memberDetails = (MemberSecurity) authentication.getPrincipal();
		List<String> roles = memberDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, memberDetails.getId(), memberDetails.getAccount(),
				memberDetails.getName(), memberDetails.getEmail(), memberDetails.getPhone(),
				memberDetails.getPostCode(), memberDetails.getAddress(), memberDetails.getDetailAddress(),
				memberDetails.getStatus(), roles));
	}

	/*
	 * 1) signUpRequest로부터 회원가입 정보를 받음. 2) userRepository에서 Username 존재여부를
	 * 검사함(existsByUsername) 3) 존재하지 않으면 User 객체를 신규로 생성함. 4) 권한(Role)도 생성함. 5) 권한이
	 * 있으면 해당 권한을 추가하고, 없으면 기본 권한을 부여함. 6) 회원정보를 저장하고(save), 완료 메시지를 뿌려줌.
	 */

	// AuthTokenFilter가 Request로 넘어온 정보를 가지고 로그인 여부를 검사했으면,
	// Authentication Manager에서는 입력된 Request 정보가 올바른지를 검사하는 것으로 볼 수 있음
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest){
		if (memberRepository.existsByAccount(signUpRequest.getAccount())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: 이미존재하는아이디입니다!"));
		}

		if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: 이미존재하는 이메일입니다!"));
		}

		// Create new user's account
		Member member = new Member(signUpRequest.getAccount(), signUpRequest.getName(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), signUpRequest.getPhone(),
				signUpRequest.getPostCode(), signUpRequest.getAddress(), signUpRequest.getDetailAddress());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: 권한이 없습니다."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: 권한이 없습니다."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: 권한이없습니다."));
					roles.add(userRole);
				}
			});
		}
		member.setStatus(ObjectStatus.REGISTERED);
		member.setRoles(roles);
		memberRepository.save(member);
		return ResponseEntity.ok(new MessageResponse("가입에 성공적으로 등록되었습니다."));
	}

	// 아이디찾기
	@GetMapping("/findId")
	public Header<MemberResponse> findByNameAndEmail(String name, String email) {
		return memberService.findByNameAndEmail(name, email);
	}

	// 비밀번호찾기
	@GetMapping("/findPw")
	public Header<MemberResponse> findByNameAndAccountAndEmail(String name, String account, String email) {
		return memberService.findByNameAndAccountAndEmail(name, account, email);
	}
	//비밀번호변경
//	@PutMapping("/changePw")
//	public Header<MemberResponse> changePw(@RequestBody MemberRequest request) {
//		
//		Header<MemberRequest> result = new Header<MemberRequest>();
//		result.setData(request);
//		
//		return baseService.update(result);
//	}
}
