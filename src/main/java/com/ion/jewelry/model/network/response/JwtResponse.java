package com.ion.jewelry.model.network.response;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ion.jewelry.model.enums.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JwtResponse {
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String account;
	private String name;
	private String email;
	private String phone;
	private String postCode;
	private String address;
	private String detailAddress;
	private List<String> roles;
	
	@Enumerated(EnumType.STRING)
	private MemberStatus status; // 가입상태(등록OR미등록)
	
	private LocalDateTime unregDate; // 해지날짜
	//JWT 생성자 생성
	public JwtResponse(String token, Long id, String account, String name, String email, String phone, String postCode,
			String address, String detailAddress,MemberStatus status, List<String> roles, LocalDateTime unregDate) {
		
		this.token = token;
		this.id = id;
		this.account = account;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.postCode = postCode;
		this.address = address;
		this.detailAddress = detailAddress;
		this.status= status;
		this.roles = roles;
		this.unregDate = unregDate;
		
	}
}
