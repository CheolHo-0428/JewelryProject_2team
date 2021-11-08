package com.ion.jewelry.model.network.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.ion.jewelry.model.enums.OrderProductState;
import com.ion.jewelry.model.enums.PayAccount;
import com.ion.jewelry.model.enums.PayMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderGroupResponse {
	public Long id;  //주문번호
	
	private BigDecimal totalPrice; //총금액
	
	private Integer totalCount; //총수량
	
	private String deliveryMessage; //요청사항
	
	private String resipient; //수령인
	
	private String Phone; //전화번호
	
	private OrderProductState orderProductState; //주문상태
	
	private String postCode; //우편번호
	
	private String address; //주소1
	
	private String detailAddress; //주소2(상세주소)
	
	private PayMethod payMethod; //결제방식(계좌이체)
	
	private String depositor; //입금자명
	
	private PayAccount payAccount; //입금계좌

	private LocalDateTime arrivalDate; //상품 도착예정일
	
	private Long memberId; //해당 주문 회원아이디
	
	private LocalDateTime createdAt;
	
	List<OrderDetailResponse> orderDetailResponseList; //주문상세 리스트
}
