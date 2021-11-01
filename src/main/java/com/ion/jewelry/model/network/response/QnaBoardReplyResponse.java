package com.ion.jewelry.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaBoardReplyResponse {
	private Long id; // Qna댓글번호
	
	private String writer; // Qna댓글작성자
	
	private String content; // Qna댓글내용
	
	private Long qnaBoardId; // Qna게시판 번호
}