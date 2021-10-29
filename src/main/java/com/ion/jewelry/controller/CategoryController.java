package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.Category;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.CategoryRequest;
import com.ion.jewelry.model.network.response.CategoryItemInfoResponse;
import com.ion.jewelry.model.network.response.CategoryResponse;
import com.ion.jewelry.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/category")
public class CategoryController extends AABaseController<CategoryRequest, CategoryResponse, Category> {
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	@GetMapping("/paging") // http://localhost:8000/jewelry/category/paging?page=0
	public Header<List<CategoryResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		
		log.info("{}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	@GetMapping("{id}/itemInfo") // http://localhost:8000/jewelry/category/1/itemInfo
	public Header<CategoryItemInfoResponse> itemInfo(@PathVariable Long id){
		log.info("read id: {}", id);
		return categoryService.itemInfo(id);
	}
	
}
