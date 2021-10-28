package com.ion.jewelry.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@ToString(exclude = {"qnaBoard"})
@Builder
@Accessors(chain = true)
@Entity
public class QnaBoardReply extends AABaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QnaBoardReplySequenceGenerator")
	@SequenceGenerator(name="QnaBoardReplySequenceGenerator", sequenceName = "QnaBoardReplySequence", initialValue = 1, allocationSize = 1)
	private Long id; // Qna댓글번호
	
	private String writer; // Qna댓글작성자
	
	private String content; // Qna댓글내용
	
	@JsonBackReference
	@ManyToOne
	private QnaBoard qnaBoard; //Qna번호(fk), QnaBoard 테이블 연관관계 설정(N:1)
	
}
