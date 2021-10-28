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
@ToString(exclude = {"item"})
@Builder
@Accessors(chain = true)
@Entity
public class Cart extends AABaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CartSequenceGenerator")
	@SequenceGenerator(name = "CartSequenceGenerator", sequenceName = "CartSequence", initialValue = 1, allocationSize = 1)
	private Long id; //장바구니번호
	
	private Integer itemCount; //구매수량
	
	private Long memberId; //회원번호
	
	@JsonBackReference
	@ManyToOne
	private Item item; //상품번호(fk), item 테이블 연관관계 설정(N:1)

}
