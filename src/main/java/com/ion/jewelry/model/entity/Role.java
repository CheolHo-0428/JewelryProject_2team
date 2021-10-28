package com.ion.jewelry.model.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ion.jewelry.model.enums.ERole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
//오버라이드 equals랑 hash코드 사용하지 않겠다.
//getter,setter대신 사용 가끔 @Data가 안먹힐때가 있는데 그때 찾기가 힘듬 ㅠ
//전체생성자 생성
//기본생성자 생성
//Lombok제공 객체 안전하게 생성하는 어노테이션
//생성한 인스턴스에 set을 붙여 사용할수 있다. ex) new member().setxx
//객체매핑
//테이블 매핑 (roles테이블로 이름 설정:role예약어라서..)
@EqualsAndHashCode(callSuper = false)	
@Data									
@AllArgsConstructor						
@NoArgsConstructor						
@Builder								
@Accessors(chain = true)				
@Entity									
@Table(name = "roles")					
public class Role extends AABaseTimeEntity{
	@Id
	//@SequenceGenerator는 시퀀스 생성기를 설정하는 애노테이션입니다. 
	//DB에서 생성한 시퀀스를 맵핑하고 맵핑된 시퀀스를 통해 시퀀스를 생성하는 역할을 합니다.
	@GeneratedValue(strategy = GenerationType.SEQUENCE, // 사용할 전략을 시퀀스로 선택
	generator = "roles_SEQ_GEN") // 식별자 생성기를 설정해 놓은것
	@SequenceGenerator(name = "roles_SEQ_GEN", // 시퀀스 제너레이터 이름
			sequenceName = "SEQ_roles", // 시퀀스 이름
			initialValue = 1, // 시작값
			allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
	)
	private Integer id;			//role 테이블 번호

	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private ERole name;			//권한이름
}
