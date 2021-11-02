package com.ion.jewelry.model.network.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {
	
	private Long id; //상품번호
	
	private String name; //상품명
	
	private BigDecimal price; //상품가격
	
	private Integer stock; //상품재고
	
	private Long categoryId; //카테고리 번호
	
	private List<QnaBoardResponse> qnaBoardResponseList; // qnaboard 리스트;
	
	private List<ReviewBoardResponse> reviewBoardResponseList; // qnaboard 리스트;
	
	private List<ImageFileResponse> imageFileResponseList; // 이미지파일 리스트;
	
	private List<CartResponse> cartResponseList; // 장바구니 리스트;
	
	private List<OrderDetailResponse> orderDetailResponseList; // 주문상세 리스트;
}
