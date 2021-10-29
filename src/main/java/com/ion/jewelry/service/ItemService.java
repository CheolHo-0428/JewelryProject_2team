package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.ItemRequest;
import com.ion.jewelry.model.network.response.ItemResponse;
import com.ion.jewelry.repository.CategoryRepository;

@Service
public class ItemService extends AABaseService<ItemRequest, ItemResponse, Item> {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public Header<ItemResponse> create(Header<ItemRequest> request) {
		//1. 생성할 데이터를 요청
		ItemRequest itemRequest = request.getData();
		
		//2. 요청한 데이터 -> DB에 저장
		Item item = Item.builder()
				.name(itemRequest.getName())
				.price(itemRequest.getPrice())
				.stock(itemRequest.getStock())
				.category(categoryRepo.getOne(itemRequest.getCategoryId()))
				.build();
		Item newItem = baseRepo.save(item);
		
		//3. 저장된 DB데이터 응답
		ItemResponse res = response(newItem);
		return Header.OK(res);
	}

	@Override
	public Header<ItemResponse> update(Header<ItemRequest> request) {
		//1. 업데이트할 데이터를 요청
		ItemRequest itemRequest = request.getData();
		
		//2. 업데이트할 데이터 검색
		Optional<Item> optional = baseRepo.findById(itemRequest.getId());
		
		//3. 업데이트한 데이터 -> DB저장 -> 업데이트한 데이터 응답 
		return optional
				.map(item -> {
					item
						.setName(itemRequest.getName())
						.setPrice(itemRequest.getPrice())
						.setStock(itemRequest.getStock())
						.setCategory(categoryRepo.getOne(itemRequest.getCategoryId()));
					return item;
				})
				.map(item -> baseRepo.save(item))
				.map(item -> response(item))
				.map(item -> Header.OK(item))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다"));
	}

	@Override
	public Header delete(Long id) {
		// 1. 삭제할 데이터를 요청
		Optional<Item> optional = baseRepo.findById(id);
		
		// 2. 데이터 삭제 -> 삭제완료 메세지 응답
		return optional
				.map(item -> {
					baseRepo.delete(item);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<ItemResponse> selectRead(Long id) {
		// 1. 조회할 데이터 요청
		Optional<Item> optional = baseRepo.findById(id);
		
		// 2. 조회한 데이터 응답
		return optional
				.map(item -> response(item))
				.map(item -> Header.OK(item))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."));
	}

	@Override
	public Header<List<ItemResponse>> allRead() {
		// 1. 조회할 전체 데이터 요청
		List<Item> itemList = baseRepo.findAll();
		
		// 2. 조회한 데이터 응답
		List<ItemResponse> itemResList = itemList.stream()
					.map(item -> response(item))
					.collect(Collectors.toList());
		return Header.OK(itemResList);
	}

	@Override
	public Header<List<ItemResponse>> pagingRead(Pageable pageable) {
		// 1. 조회할 전체 데이터 요청(페이지 정보 포함)
		Page<Item> page = baseRepo.findAll(pageable);
		
		// 2. 조회한 데이터 응답(페이지 정보 포함)
		List<ItemResponse> itemResList = page.stream()
				.map(item -> response(item))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(itemResList, pagination);
	}
	
	// 응답 메소드
	public ItemResponse response(Item item) {
		ItemResponse res = ItemResponse.builder()
				.id(item.getId())
				.name(item.getName())
				.price(item.getPrice())
				.stock(item.getStock())
				.categoryId(item.getCategory().getId())
				.build();
		
		return res;
	}
	
}
