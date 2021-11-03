package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ion.jewelry.model.entity.Category;
import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.CategoryRequest;
import com.ion.jewelry.model.network.response.CartResponse;
import com.ion.jewelry.model.network.response.CategoryItemInfoResponse;
import com.ion.jewelry.model.network.response.CategoryResponse;
import com.ion.jewelry.model.network.response.ImageFileResponse;
import com.ion.jewelry.model.network.response.ItemResponse;
import com.ion.jewelry.model.network.response.OrderDetailResponse;
import com.ion.jewelry.model.network.response.QnaBoardReplyInfoResponse;
import com.ion.jewelry.model.network.response.QnaBoardReplyResponse;
import com.ion.jewelry.model.network.response.QnaBoardResponse;
import com.ion.jewelry.model.network.response.ReviewBoardReplyResponse;
import com.ion.jewelry.model.network.response.ReviewBoardResponse;

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
	
	@Autowired
	private ImageFileService imageFileService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
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
					
					//해당 아이템 QNA게시판 정보가져오기
					List<QnaBoardResponse> qnaBoardResList = item.getQnaBoardList().stream()
							.map(qnaBoard -> {
								QnaBoardResponse qnaBoardRes = qnaBoardService.response(qnaBoard);
								
								//해당 QNA댓글 정보가져오기
								List<QnaBoardReplyResponse> qnaBoardReplyResList = qnaBoard.getQnaBoardReplyList().stream()
										.map(qnaBoardReply -> qnaBoardReplyService.response(qnaBoardReply))
										.collect(Collectors.toList());
								qnaBoardRes.setQnaBoardReplyResponseList(qnaBoardReplyResList);
								return qnaBoardRes; 
								})
							.collect(Collectors.toList());
					itemResponse.setQnaBoardResponseList(qnaBoardResList);
					
					//해당 아이템 리뷰게시판 정보 가져오기
					List<ReviewBoardResponse> reviewBoardResList = item.getReviewBoardList().stream()
							.map(reviewBoard -> {
								ReviewBoardResponse reviewBoardRes = reviewBoardService.response(reviewBoard);
								
								//해당 리뷰댓글 정보 가져오기
								List<ReviewBoardReplyResponse> reviewBoardReplyResList = reviewBoard.getReviewBoardReplyList().stream()
										.map(reviewBoareReply -> reviewBoardReplyService.response(reviewBoareReply))
										.collect(Collectors.toList());
								reviewBoardRes.setReviewBoardReplyResponseList(reviewBoardReplyResList);
								return reviewBoardRes;
							})
							.collect(Collectors.toList());
					itemResponse.setReviewBoardResponseList(reviewBoardResList);
					
					//해당 아이템 이미지파일 정보 가져오기
					List<ImageFileResponse> imageFileResList = item.getImageFileList().stream()
							.map(imageFile -> imageFileService.response(imageFile))
							.collect(Collectors.toList());
					itemResponse.setImageFileResponseList(imageFileResList);
					
					//해당 아이템 주문상세내역 정보 가져오기
					List<OrderDetailResponse> orderDetailResList = item.getOrderDetailList().stream()
							.map(orderDetail -> orderDetailService.response(orderDetail))
							.collect(Collectors.toList());
					itemResponse.setOrderDetailResponseList(orderDetailResList);
					
					//해당 아이템 장바구니 정보 가져오기
					List<CartResponse> cartResList = item.getCartList().stream()
							.map(cart -> cartService.response(cart))
							.collect(Collectors.toList());
					itemResponse.setCartResponseList(cartResList);
					
					return itemResponse;
				})
				.sorted((a, b) -> b.createdAt.compareTo(a.createdAt))
				.collect(Collectors.toList());				
		categoryResponse.setItemResponseList(itemResList);
				
		//조회된 정보들 붙이기!
		CategoryItemInfoResponse categoryItemInfoResponse = CategoryItemInfoResponse.builder()
				.categoryResponse(categoryResponse).build();
		
		return Header.OK(categoryItemInfoResponse);
	}
	
	//특정카테고리에 해당되는 item정보 및 페이징 정보(qna, review 등등) 조회
		public Header<CategoryItemInfoResponse> itemPagingInfo(Long id, Pageable pageable){
			
			Category category = baseRepo.getOne(id);
			CategoryResponse categoryResponse = response(category);
			
			List<Item> totalItemList = category.getItemList();
			totalItemList.sort((a, b) -> (int)(b.id - a.id));
			int getSize = totalItemList.size();		
			int start = (int)pageable.getOffset();
			//int end = (start + pageable.getPageSize()) > getSize ? getSize : (start + pageable.getPageSize());
			int end = Math.min((start + pageable.getPageSize()), getSize);
			
			Page<Item> itemList = new PageImpl<Item>(totalItemList.subList(start, end), pageable, getSize);
			List<ItemResponse> itemResList = itemList.stream()
					.map(item -> {
						ItemResponse itemResponse = itemService.response(item);
						
						//해당 아이템 QNA게시판 정보가져오기
						List<QnaBoardResponse> qnaBoardResList = item.getQnaBoardList().stream()
								.map(qnaBoard -> {
									QnaBoardResponse qnaBoardRes = qnaBoardService.response(qnaBoard);
									
									//해당 QNA댓글 정보가져오기
									List<QnaBoardReplyResponse> qnaBoardReplyResList = qnaBoard.getQnaBoardReplyList().stream()
											.map(qnaBoardReply -> qnaBoardReplyService.response(qnaBoardReply))
											.collect(Collectors.toList());
									qnaBoardRes.setQnaBoardReplyResponseList(qnaBoardReplyResList);
									return qnaBoardRes; 
									})
								.collect(Collectors.toList());
						itemResponse.setQnaBoardResponseList(qnaBoardResList);
						
						//해당 아이템 리뷰게시판 정보 가져오기
						List<ReviewBoardResponse> reviewBoardResList = item.getReviewBoardList().stream()
								.map(reviewBoard -> {
									ReviewBoardResponse reviewBoardRes = reviewBoardService.response(reviewBoard);
									
									//해당 리뷰댓글 정보 가져오기
									List<ReviewBoardReplyResponse> reviewBoardReplyResList = reviewBoard.getReviewBoardReplyList().stream()
											.map(reviewBoareReply -> reviewBoardReplyService.response(reviewBoareReply))
											.collect(Collectors.toList());
									reviewBoardRes.setReviewBoardReplyResponseList(reviewBoardReplyResList);
									return reviewBoardRes;
								})
								.collect(Collectors.toList());
						itemResponse.setReviewBoardResponseList(reviewBoardResList);
						
						//해당 아이템 이미지파일 정보 가져오기
						List<ImageFileResponse> imageFileResList = item.getImageFileList().stream()
								.map(imageFile -> imageFileService.response(imageFile))
								.collect(Collectors.toList());
						itemResponse.setImageFileResponseList(imageFileResList);
						
						//해당 아이템 주문상세내역 정보 가져오기
						List<OrderDetailResponse> orderDetailResList = item.getOrderDetailList().stream()
								.map(orderDetail -> orderDetailService.response(orderDetail))
								.collect(Collectors.toList());
						itemResponse.setOrderDetailResponseList(orderDetailResList);
						
						//해당 아이템 장바구니 정보 가져오기
						List<CartResponse> cartResList = item.getCartList().stream()
								.map(cart -> cartService.response(cart))
								.collect(Collectors.toList());
						itemResponse.setCartResponseList(cartResList);
						
						return itemResponse;
					})
					.collect(Collectors.toList());				
			categoryResponse.setItemResponseList(itemResList);
						
			Pagination pagination = Pagination.builder()
					.totalPages(itemList.getTotalPages())
					.totalElements(itemList.getTotalElements())
					.currentPage(itemList.getNumber())
					.currentElements(itemList.getNumberOfElements())
					.build();
			
			//조회된 정보들 붙이기!
			CategoryItemInfoResponse categoryItemInfoResponse = CategoryItemInfoResponse.builder()
					.categoryResponse(categoryResponse).build();
			
			return Header.OK(categoryItemInfoResponse, pagination);
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
