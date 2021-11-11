package com.ion.jewelry.model.entity;

import java.math.BigDecimal;
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
import com.ion.jewelry.model.enums.ObjectStatus;

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
@ToString(exclude = {"orderDetailList", "category", "cartList", "imageFileList", "reviewBoardList", "qnaBoardList"})
@Builder
@Accessors(chain = true)
@Entity
public class Item extends AABaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ItemSequenceGenerator")
	@SequenceGenerator(name="ItemSequenceGenerator", sequenceName = "ItemSequence", initialValue = 1, allocationSize = 1)
	public Long id; //상품번호
	
	private String name; //상품명
	
	private BigDecimal price; //상품가격
	
	private Integer stock; //상품재고
	
	@Enumerated(EnumType.STRING)
	private ObjectStatus status; // 상품등록상태
	
	@JsonBackReference
	@ManyToOne
	private Category category; //카테고리번호(fk), category 테이블 연관관계 설정(N:1)
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
	private List<OrderDetail> orderDetailList; // OrderDetail 테이블 연관관계 설정(1:N)
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL)
	private List<Cart> cartList; // Cart 테이블 연관관계 설정(1:N)
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL)
	private List<ImageFile> imageFileList; // ImageFile 테이블 연관관계 설정(1:N)
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL)
	private List<ReviewBoard> reviewBoardList; // ReviewBoard 테이블 연관관계 설정(1:N)
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL)
	private List<QnaBoard> qnaBoardList; // QnaBoard 테이블 연관관계 설정(1:N)
}
