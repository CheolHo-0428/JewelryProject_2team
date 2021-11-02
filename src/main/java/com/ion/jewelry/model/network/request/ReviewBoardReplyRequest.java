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
public class ReviewBoardReplyRequest {
	private Long id; // 댓글번호
	
	private String writer; //댓글작성자
	
	private String content; //댓글내용
	
	private Long reviewBoardId; // 리뷰게시판번호
	
	private LocalDateTime updatedAt;
}
