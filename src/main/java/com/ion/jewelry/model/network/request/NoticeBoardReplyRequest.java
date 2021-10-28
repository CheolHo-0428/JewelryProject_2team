package com.ion.jewelry.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeBoardReplyRequest {
	
	private Long id; // 공지사항 댓글번호
	
	private String writer; // 공지사항 댓글작성자
	
	private String content; // 공지사항 댓글내용
	
	private Long noticeBoardId; // 공지사항 번호
}
