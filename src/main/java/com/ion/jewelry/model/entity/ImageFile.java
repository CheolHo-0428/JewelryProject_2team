package com.ion.jewelry.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@ToString(exclude = {"item"})
@Builder
@Accessors(chain = true)
@Entity
public class ImageFile extends AABaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ImageFileSequenceGenerator")
	@SequenceGenerator(name = "ImageFileSequenceGenerator", sequenceName = "ImageFileSequence", initialValue = 1, allocationSize = 1)
	private Long id; //이미지번호
	
	private String originFileName; //원본파일이름
	
	private String storedFileName; //서버에저장될파일이름
	
	private String storedThumbnail; //썸네일이미지
	
	@Enumerated(EnumType.STRING)
	private YesNo delegateThumbnail; //대표썸네일여부
	
	private Long fileSize; //파일크기
	
	@Enumerated(EnumType.STRING)
	private YesNo deleteCheck; //파일삭제여부
	
	@JsonBackReference
	@ManyToOne
	private Item item; //상품번호(fk), Item 테이블 연관관계 설정(N:1)
	
}
