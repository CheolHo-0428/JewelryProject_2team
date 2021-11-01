package com.ion.jewelry.model.network.request;

import com.ion.jewelry.model.enums.CategoryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

	private Long id; //카테고리번호
	
	private CategoryType categoryType;
}
