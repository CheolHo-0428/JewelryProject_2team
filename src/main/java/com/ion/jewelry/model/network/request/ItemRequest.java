package com.ion.jewelry.model.network.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ion.jewelry.model.enums.ObjectStatus;

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
	
	private ObjectStatus status; // 상품등록상태
	
	private Long categoryId; //카테고리 번호
	
	private LocalDateTime createdAt;
}
