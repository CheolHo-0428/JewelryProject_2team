package com.ion.jewelry.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ion.jewelry.model.enums.YesNo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"noticeBoardReplyList"})
@Builder
@Accessors(chain = true)
@Entity
public class NoticeBoard extends AABaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NoticeBoardSequenceGenerator")
	@SequenceGenerator(name="NoticeBoardSequenceGenerator", sequenceName = "NoticeBoardSequence", initialValue = 1, allocationSize = 1)
	private Long id; //공지사항 번호
	
	private String title; //공지사항 제목
	
	private String content; //공지사항 내용
	
	private String writer; //공지사항 작성자
	
	private String password; //공지사항 비밀번호
	
	@Enumerated(EnumType.STRING)
	private YesNo privateOk; //공지사항 비밀글설정
	
	private String originFileName; //원본이미지파일이름
	
	private String storedFileName; //서버에저장될이미지파일이름
	
	private Long fileSize; //이미지파일크기
	
	@Enumerated(EnumType.STRING)
	private YesNo deleteCheck; //이미지파일삭제여부
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "noticeBoard")
	private List<NoticeBoardReply> noticeBoardReplyList;
	
}
