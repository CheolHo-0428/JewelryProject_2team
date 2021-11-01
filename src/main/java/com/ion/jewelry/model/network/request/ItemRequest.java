package com.ion.jewelry.model.network.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequest {

	private Long id; //상품번호
	
	private String name; //상품명
	
	private BigDecimal price; //상품가격
	
	private Integer stock; //상품재고
	
	private Long categoryId; //카테고리 번호
}
