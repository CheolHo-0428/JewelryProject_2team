package com.ion.jewelry.model.network.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResignMemberRequest {
	
	private Long num; // 삭제된 순서

	private Long id; // 회원번호

	private String account; //아이디
	
	private String name; //이름
	
	private String password; // 비밀번호
	
	private String email; // 이메일
	
	private String phone; // 전화번호

	private String postCode; // 우편번호
	
	private String address; // 주소1

	private String detailAddress; // 주소2(상세주소)
	
	private LocalDateTime createdAt; // 회원가입날짜
	
	private String createdBy; // 데이터를 생성한 계정
	
	private LocalDateTime updatedAt; // 데이터업데이트날짜
	
	private String updatedBy; // 데이터를 수정한 계정
	
	private LocalDateTime unregDate; // 해지날짜
	
	private String status; //상태
}
