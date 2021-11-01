package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ion.jewelry.model.entity.Category;
import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.CategoryRequest;
import com.ion.jewelry.model.network.response.CategoryItemInfoResponse;
import com.ion.jewelry.model.network.response.CategoryResponse;
import com.ion.jewelry.model.network.response.ItemResponse;

@Service
public class CategoryService extends AABaseService<CategoryRequest, CategoryResponse, Category> {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private QnaBoardService qnaBoardService;
	
	@Autowired
	private QnaBoardReplyService qnaBoardReplyService;
	
	@Autowired
	private ReviewBoardService reviewBoardService;
	
	@Autowired
	private ReviewBoardReplyService reviewBoardReplyService;
	
	@Override
	public Header<CategoryResponse> create(Header<CategoryRequest> request) {
		//1. 생성할 데이터를 요청
		CategoryRequest categoryRequest = request.getData();
		
		//2. 요청한 데이터 -> DB에 저장
		Category category = Category.builder()
					.categoryType(categoryRequest.getCategoryType())
					.build();
		
		Category newCategory =  baseRepo.save(category);
		
		//3. 저장된 DB데이터 응답
		CategoryResponse categoryResponse = response(newCategory);
		return Header.OK(categoryResponse);
	}

	@Override
	public Header<CategoryResponse> update(Header<CategoryRequest> request) {
		//1. 업데이트할 데이터를 요청
		CategoryRequest categoryRequest =  request.getData();
		
		//2. 업데이트할 데이터 검색
		Optional<Category> optional = baseRepo.findById(categoryRequest.getId());
		
		//3. 업데이트한 데이터 -> DB저장 -> 업데이트한 데이터 응답 
		return optional
				.map(category -> {
					category.setCategoryType(categoryRequest.getCategoryType());
					return category;
				})
				.map(category -> baseRepo.save(category))
				.map(category -> response(category))
				.map(category -> Header.OK(category))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		// 1. 삭제할 데이터를 요청
		Optional<Category> optional = baseRepo.findById(id);
		
		// 2. 데이터 삭제 -> 삭제완료 메세지 응답
		return optional
				.map(category -> {
					baseRepo.delete(category);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<CategoryResponse> selectRead(Long id) {
		// 1. 조회할 데이터 요청
		Optional<Category> optional = baseRepo.findById(id);
		
		// 2. 조회한 데이터 응답
		return optional
				.map(category -> response(category))
				.map(category -> Header.OK(category))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."));
	}

	@Override
	public Header<List<CategoryResponse>> allRead() {
		// 1. 조회할 전체 데이터 요청
		List<Category> categoryList = baseRepo.findAll();
		
		// 2. 조회한 데이터 응답
		List<CategoryResponse> categoryResList = categoryList.stream()
					.map(category -> response(category))
					.collect(Collectors.toList());
		
		return Header.OK(categoryResList);
	}

	@Override
	public Header<List<CategoryResponse>> pagingRead(Pageable pageable) {
		// 1. 조회할 전체 데이터 요청(페이지 정보 포함)
		Page<Category> page = baseRepo.findAll(pageable);
		
		// 2. 조회한 데이터 응답(페이지 정보 포함)
		List<CategoryResponse> categoryResList = page.stream()
				.map(category -> response(category))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(categoryResList, pagination);
	}
	
	//특정카테고리에 해당되는 item정보(qna, review 등등) 조회
	public Header<CategoryItemInfoResponse> itemInfo(Long id){
		
		Category category = baseRepo.getOne(id);
		CategoryResponse categoryResponse = response(category);
		
		List<Item> itemList = category.getItemList();
		List<ItemResponse> itemResList = itemList.stream()
				.map(item -> {
					ItemResponse itemResponse = itemService.response(item);
					return itemResponse;
				})
				.collect(Collectors.toList());
				
		categoryResponse.setItemResponseList(itemResList);
		CategoryItemInfoResponse categoryItemInfoResponse = CategoryItemInfoResponse.builder()
				.categoryResponse(categoryResponse).build();
		
		return Header.OK(categoryItemInfoResponse);
	}
	
	
	// 응답 메소드
	public CategoryResponse response(Category category) {
		CategoryResponse res = CategoryResponse.builder()
				.id(category.getId())
				.categoryType(category.getCategoryType())
				.build();
		
		return res;
	}
}
