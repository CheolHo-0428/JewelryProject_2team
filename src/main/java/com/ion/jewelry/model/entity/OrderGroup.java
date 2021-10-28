package com.ion.jewelry.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ion.jewelry.model.enums.OrderProductState;
import com.ion.jewelry.model.enums.PayAccount;
import com.ion.jewelry.model.enums.PayMethod;

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
@ToString(exclude = {"member", "orderDetailList"})
@Builder
@Accessors(chain = true)
@Entity
public class OrderGroup extends AABaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderGroupSequenceGenerator")
	@SequenceGenerator(name = "OrderGroupSequenceGenerator", sequenceName = "OrderGroupSequence", initialValue = 1, allocationSize = 1)
	private Long id;  //주문번호
	
	private BigDecimal totalPrice; //총금액
	
	private Integer totalCount; //총수량
	
	private String deliveryMessage; //요청사항
	
	private String resipient; //수령인
	
	private String Phone; //전화번호
	
	@Enumerated(EnumType.STRING)
	private OrderProductState orderProductState; //주문상태
	
	private String postCode; //우편번호
	
	private String address; //주소1
	
	private String detailAddress; //주소2(상세주소)
	
	@Enumerated(EnumType.STRING)
	private PayMethod payMethod; //결제방식(계좌이체)
	
	private String depositor; //입금자명
	
	@Enumerated(EnumType.STRING)
	private PayAccount payAccount; //입금계좌

	private LocalDateTime arrivalDate; //상품 도착예정일
	
	@JsonBackReference
	@ManyToOne
	private Member member; // 회원번호(fk), Member 테이블 연관관계 설정(N:1)
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderGroup")
	private List<OrderDetail> orderDetailList; // OrderDetail 테이블 연관관계 설정(1:N)
	
}
