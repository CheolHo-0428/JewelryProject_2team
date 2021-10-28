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
import com.ion.jewelry.model.enums.CategoryType;

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
@ToString(exclude = {"itemList"})
@Builder
@Accessors(chain = true)
@Entity
public class Category extends AABaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CategorySequenceGenerator")
	@SequenceGenerator(name = "CategorySequenceGenerator", sequenceName = "CategorySequence", initialValue = 1, allocationSize = 1)
	private Long id; //카테고리번호
	
	@Enumerated(EnumType.STRING)
	private CategoryType categoryType; //카테고리 타입
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	private List<Item> itemList; // item 테이블 연관관계 설정(1:N)
	
}
