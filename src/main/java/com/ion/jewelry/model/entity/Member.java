package com.ion.jewelry.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ion.jewelry.model.enums.MemberStatus;

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
	private MemberStatus status; // 가입상태(등록OR미동르고)
	
	private LocalDateTime unregDate; // 해지날짜
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<OrderGroup> orderGroupList;	//OrderGroup 테이블 연관관계 설정(1:N)
}
