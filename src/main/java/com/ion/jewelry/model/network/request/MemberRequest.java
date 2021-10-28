package com.ion.jewelry.model.network.request;

import java.time.LocalDateTime;

import com.ion.jewelry.model.enums.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequest {
	
	private Long id; // 회원번호(pk)
	
	private String account; //아이디
	
	private String name; //이름
	
	private String password; // 비밀번호
	
	private String email; // 이메일
	
	private String phone; // 전화번호
	
	private String postCode; // 우편번호
	
	private String address; // 주소1
	
	private String detailAddress; // 주소2(상세주소)
	
	private MemberStatus status; // 가입상태(등록OR미동르고)
	
	private LocalDateTime unregDate; // 해지날짜
}
