package com.ion.jewelry.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
	
	private Long id; //장바구니번호
	
	private Integer itemCount; //구매수량
	
	private Long memberId; //회원번호
	
	private Long itemId; //해당 상품 번호
}
