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
@ToString(exclude = {"reviewBoard"})
@Builder
@Accessors(chain = true)
@Entity
public class ReviewBoardReply extends AABaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReviewBoardReplySequenceGenerator")
	@SequenceGenerator(name="ReviewBoardReplySequenceGenerator", sequenceName = "ReviewBoardReplySequence", initialValue = 1, allocationSize = 1)
	private Long id; // 댓글번호
	
	private String writer; //댓글작성자
	
	private String content; //댓글내용
	
	@JsonBackReference
	@ManyToOne
	private ReviewBoard reviewBoard; //리뷰번호(fk), ReviewBoard 테이블 연관관계 설정(N:1)
	
}
