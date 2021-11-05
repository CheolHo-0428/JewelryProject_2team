package com.ion.jewelry.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.enums.ObjectStatus;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.ItemRequest;
import com.ion.jewelry.model.network.request.NoticeBoardRequest;
import com.ion.jewelry.model.network.response.ItemInfoResponse;
import com.ion.jewelry.model.network.response.ItemResponse;
import com.ion.jewelry.model.network.response.NoticeBoardResponse;
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
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 8)
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
	
	@GetMapping("/search")
	public Header<List<ItemResponse>> search(
			@PathParam("keyword")String keyword, 
			@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) 
			Pageable pageable) {
		return itemService.search(keyword, pageable);
	}
	
	@GetMapping("/searchId")
	public Header<List<ItemResponse>> searchId(
			@PathParam("keyword") Long keyword, 
			@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) 
			Pageable pageable) {
		return itemService.searchId(keyword, pageable);
	}
	
	@GetMapping("/searchCategoryId")
	public Header<List<ItemResponse>> searchCategoryId(
			@PathParam("keyword") Long keyword, 
			@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) 
			Pageable pageable) {
		return itemService.searchCategoryId(keyword, pageable);
	}
	
	@GetMapping("/searchStatus")
	public Header<List<ItemResponse>> searchStatus(
			@PathParam("keyword")ObjectStatus keyword, 
			@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) 
			Pageable pageable) {
		return itemService.searchStatus(keyword, pageable);
	}

	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/reg")
	public Header<ItemResponse> create(@RequestBody ItemRequest request) {
		Header<ItemRequest> result = new Header<ItemRequest>();
		result.setData(request);
		
		return baseService.create(result);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update")
	public Header<ItemResponse> update(@RequestBody ItemRequest request) {

		Header<ItemRequest> result = new Header<ItemRequest>();
		result.setData(request);

		return baseService.update(result);
	}
}
