package com.ion.jewelry.jwt;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.enums.ObjectStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// 시큐리티 객체 저장하는 vo
// 로그인 정보 객체로, 사용자 이름을 가지고 실제 등록된 사용자인지를 확인한 후 로그인 정보를 객체로 생성함
// 실제 인증 작업을 수행해서 이상유무를 판단 후, 이상이 없으면 Authentication을 생성함(setAuthentication)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberSecurity implements UserDetails  {  // UserDetails - Security 내장 객체임
	private static final long serialVersionUID = 1L;

	private Long id;
	private String account;
	private String name;
	private String email;
	private String phone;
	private String postCode;
	private String address;
	private String detailAddress;
	
	@Enumerated(EnumType.STRING)
	private ObjectStatus status; // 가입상태(등록OR미등록)
	
	//private LocalDateTime unregDate; // 해지날짜

	@JsonIgnore
	private String password;			//비밀번호는 보이면 안되므로 JsonIgnore로 막음

	private Collection<? extends GrantedAuthority> authorities;
	//생성자생성(필요한 데이터만)
	public MemberSecurity(Long id, String account, String name, String email, String phone, String postCode,
			String address, String detailAddress, ObjectStatus status,
			List<GrantedAuthority> authorities,String password) {
			this.id = id;
			this.account = account;
			this.name = name;
			this.email = email;
			this.phone = phone;
			this.postCode = postCode;
			this.address = address;
			this.detailAddress = detailAddress;
			this.status = status;
			this.authorities = authorities;
			this.password = password;
	}
	
	public static MemberSecurity build(Member member) {
		List<GrantedAuthority> authorities = member.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new MemberSecurity(
				member.getId(), 
				member.getAccount(), 
				member.getName(),
				member.getEmail(),
				member.getPhone(),
				member.getPostCode(),
				member.getAddress(),
				member.getDetailAddress(),
				member.getStatus(),
				//member.getUnregDate(),
				authorities,
				member.getPassword());
	}
	
	//equal과 hash 오버라이드
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MemberSecurity user = (MemberSecurity) o;
		return Objects.equals(id, user.id);
	}
	
	//자바에서 제공하는 user정의 클래스에서 override
	@Override
	public String getUsername() {
		return account;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
