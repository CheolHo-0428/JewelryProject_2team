package com.ion.jewelry.model.network.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ion.jewelry.model.enums.ObjectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {
	
	public Long id; //상품번호
	
	private String name; //상품명
	
	private BigDecimal price; //상품가격
	
	private Integer stock; //상품재고
	
	private ObjectStatus status; // 상품등록상태
	
	private Long categoryId; //카테고리 번호
	
	public LocalDateTime createdAt;
	
	public String createdBy;
	
	public LocalDateTime updatedAt;
	
	public String updatedBy;
	
	private List<QnaBoardResponse> qnaBoardResponseList; // qnaboard 리스트;
	
	private List<ReviewBoardResponse> reviewBoardResponseList; // qnaboard 리스트;
	
	private List<ImageFileResponse> imageFileResponseList; // 이미지파일 리스트;
	
	private List<CartResponse> cartResponseList; // 장바구니 리스트;
	
	private List<OrderDetailResponse> orderDetailResponseList; // 주문상세 리스트;
}
