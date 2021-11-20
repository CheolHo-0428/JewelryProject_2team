package com.ion.jewelry.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderProductState {
	//enum(id, title, description)
	BEFORE_BANK_TRANSFER(0,"입금전", "전체금액 입금전"),
	READY(1, "배송준비증", "상품 배송준비중"),
	SHIPPING(2, "배송중", "상품 배송중"),
	COMPLETE(3, "배송완료", "상품 배송완료"),
	CARD(4,"카드결제완료","카드결제완료");
	
	private Integer id;
	
	private String title;
	
	private String description;
	
}
