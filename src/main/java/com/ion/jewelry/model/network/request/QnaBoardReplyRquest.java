package com.ion.jewelry.model.network.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaBoardReplyRquest {
	private Long id; // Qna댓글번호
	
	private String writer; // Qna댓글작성자
	
	private String content; // Qna댓글내용
	
	private Long qnaBoardId; // Qna게시판 번호
	
	private LocalDateTime updatedAt;
}
