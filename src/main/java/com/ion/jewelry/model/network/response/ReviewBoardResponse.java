package com.ion.jewelry.model.network.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ion.jewelry.model.enums.YesNo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewBoardResponse {
	
	public Long id; //리뷰번호
	
	private String title; //리뷰제목
	
	private String content; //리뷰내용
	
	private String writer; //작성자
	
	private String password; //리뷰비밀번호
	
	private YesNo privateOk; //리뷰비밀글설정
	
	private String originFileName; //원본이미지파일이름
	
	private String storedFileName; //서버에저장될이미지파일이름
	
	private Long fileSize; //이미지파일크기
	
	private YesNo deleteCheck; //이미지파일삭제여부
	
	private Long itemId; //해당상품 번호 아이디
	
	private LocalDateTime createdAt;
	
	private List<ReviewBoardReplyResponse> reviewBoardReplyResponseList; //댓글 리스트
}
