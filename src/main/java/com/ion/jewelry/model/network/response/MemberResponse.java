package com.ion.jewelry.model.network.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ion.jewelry.model.enums.ObjectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {
	
	private Long id; // 회원번호(pk)
	
	private String account; //아이디
	
	private String name; //이름
	
	private String password; // 비밀번호
	
	private String email; // 이메일
	
	private String phone; // 전화번호
	
	private String postCode; // 우편번호
	
	private String address; // 주소1
	
	private String detailAddress; // 주소2(상세주소)
	
	private ObjectStatus status; // 가입상태(등록OR미등록)
	
	private LocalDateTime createdAt;
	
	private String adminMessage; //관리자 메모
	
	//private LocalDateTime unregDate; // 해지날짜
	
	private List<OrderGroupResponse> orderGroupList; //회원별 주문정보 리스트
}
