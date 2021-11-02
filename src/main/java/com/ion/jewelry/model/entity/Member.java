package com.ion.jewelry.model.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ion.jewelry.model.enums.ObjectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"orderGroupList"})
@Builder
@Accessors(chain = true)
@Entity
//@Table(
//		uniqueConstraints = { 
//			@UniqueConstraint(columnNames = "account"),
//			@UniqueConstraint(columnNames = "email") 
//		}
//)
public class Member extends AABaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MemberSequenceGenerator")
	@SequenceGenerator(name = "MemberSequenceGenerator", sequenceName = "MemberSequence", initialValue = 1, allocationSize = 1)
	private Long id; // 회원번호(pk)

	private String account; //아이디
	
	private String name; //이름
	
	private String password; // 비밀번호
	
	private String email; // 이메일
	
	private String phone; // 전화번호

	private String postCode; // 우편번호
	
	private String address; // 주소1

	private String detailAddress; // 주소2(상세주소)
	
	@Enumerated(EnumType.STRING)
	private ObjectStatus status; // 가입상태(등록OR미동르고)
	
	//private LocalDateTime unregDate; // 해지날짜
	
	//user_roles테이블 연관관계
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<OrderGroup> orderGroupList;	//OrderGroup 테이블 연관관계 설정(1:N)
	
	public Member(String account, String name, String password, String email, String phone, String postCode,	//생성자 다가져오지않고 입력된값만 시큐리티로 넘겨야되서 생성자따로 구현
			String address, String detailAddress) {
		this.account = account;
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.postCode = postCode;
		this.address = address;
		this.detailAddress = detailAddress;
	}
}
