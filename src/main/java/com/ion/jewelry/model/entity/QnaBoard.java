package com.ion.jewelry.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@ToString(exclude = {"item", "qnaBoardReplyList"})
@Builder
@Accessors(chain = true)
@Entity
public class QnaBoard extends AABaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QnaBoardSequenceGenerator")
	@SequenceGenerator(name="QnaBoardSequenceGenerator", sequenceName = "QnaBoardSequence", initialValue = 1, allocationSize = 1)
	private Long id; //Qna번호
	
	private String title; //Qna제목
	
	private String content; //Qna내용
	
	private String writer; //Qna작성자
	
	private String password; //Qna비밀번호
	
	@Enumerated(EnumType.STRING)
	private YesNo privateOk; //Qna비밀글설정
	
	private String originFileName; //원본이미지파일이름
	
	private String storedFileName; //서버에저장될이미지파일이름
	
	private Long fileSize; //이미지파일크기
	
	@Enumerated(EnumType.STRING)
	private YesNo deleteCheck; //이미지파일삭제여부
	
	@JsonBackReference
	@ManyToOne
	private Item item; //상품번호(fk), item 테이블 연관관계 설정(N:1)
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "qnaBoard", cascade = CascadeType.ALL)
	private List<QnaBoardReply> qnaBoardReplyList;
}
