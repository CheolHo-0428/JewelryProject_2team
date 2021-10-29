package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.ItemRequest;
import com.ion.jewelry.model.network.response.ItemResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/item")
public class ItemController extends AABaseController<ItemRequest, ItemResponse, Item> {

	@Override
	@GetMapping("/paging") // http://localhost:8000/jewelry/item/paging?page=0
	public Header<List<ItemResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		
		log.info("페이징처리 정보: {}", pageable);
		return baseService.pagingRead(pageable);
	}

	
}
