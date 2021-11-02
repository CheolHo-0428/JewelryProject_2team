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
public class QnaBoardResponse {
	
	public Long id; //Qna번호
	
	private String title; //Qna제목
	
	private String content; //Qna내용
	
	private String writer; //Qna작성자
	
	private String password; //Qna비밀번호
	
	private YesNo privateOk; //Qna비밀글설정
	
	private String originFileName; //원본이미지파일이름
	
	private String storedFileName; //서버에저장될이미지파일이름
	
	private Long fileSize; //이미지파일크기
	
	private YesNo deleteCheck; //이미지파일삭제여부
	
	private Long itemId; // 상품번호
	
	private LocalDateTime createdAt;
	
	private List<QnaBoardReplyResponse> qnaBoardReplyResponseList; //댓글 리스트
}
