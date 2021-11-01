package com.ion.jewelry.model.network.response;

import java.math.BigDecimal;

import com.ion.jewelry.model.enums.OrderProductState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
	
	private Long id; //주문상세번호
	
	private Integer orderCount; //상품 구매수량
	
	private BigDecimal orderPrice; //상품 구매가격
	
	private OrderProductState orderProductState; //상품 주문상태
	
	private Long orderGroupId; // 주문정보 id
	
	private Long itemId; // 주문상품 id
}
