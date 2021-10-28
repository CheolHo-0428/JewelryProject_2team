package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.Category;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.CategoryRequest;
import com.ion.jewelry.model.network.response.CategoryResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/category")
public class CategoryController extends AABaseController<CategoryRequest, CategoryResponse, Category> {

	@Override
	@GetMapping("/paging")
	public Header<List<CategoryResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		
		log.info("{}", pageable);
		return baseService.pagingRead(pageable);
	}

	
}
