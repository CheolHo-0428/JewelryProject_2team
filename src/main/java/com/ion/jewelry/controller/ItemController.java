package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.ItemRequest;
import com.ion.jewelry.model.network.response.ItemInfoResponse;
import com.ion.jewelry.model.network.response.ItemResponse;
import com.ion.jewelry.service.ItemService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/item")
@CrossOrigin("http://localhost:8080/")
public class ItemController extends AABaseController<ItemRequest, ItemResponse, Item> {
	
	@Autowired
	private ItemService itemService;
	
	@Override
	@GetMapping("/paging") // http://localhost:8000/jewelry/item/paging?page=0
	public Header<List<ItemResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10)
			Pageable pageable) {
		
		log.info("페이징처리 정보: {}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	//특정아이템에 해당되는 QNA, REVIEW 게시글들 조회(댓글 포함)
	@GetMapping("{id}/itemInfo") // http://localhost:8000/jewelry/item/1/itemInfo
	public Header<ItemInfoResponse> qnaInfo(@PathVariable Long id){
		log.info("read id: {}", id);
		return itemService.itemInfo(id);
	}
}
