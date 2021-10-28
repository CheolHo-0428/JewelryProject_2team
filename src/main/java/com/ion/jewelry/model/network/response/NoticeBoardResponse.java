package com.ion.jewelry.model.network.response;

import java.time.LocalDateTime;

import com.ion.jewelry.model.enums.YesNo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeBoardResponse {
	
	private Long id; //공지사항 번호
	
	private String title; //공지사항 제목
	
	private String content; //공지사항 내용
	
	private String writer; //공지사항 작성자
	
	private String password; //공지사항 비밀번호
	
	private YesNo privateOk; //공지사항 비밀글설정
	
	private String originFileName; //원본이미지파일이름
	
	private String storedFileName; //서버에저장될이미지파일이름
	
	private Long fileSize; //이미지파일크기
	
	private YesNo deleteCheck; //이미지파일삭제여부
	
	private LocalDateTime createdAt, updatedAt;
}
