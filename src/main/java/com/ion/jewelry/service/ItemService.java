package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ion.jewelry.model.entity.Cart;
import com.ion.jewelry.model.entity.ImageFile;
import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.entity.OrderDetail;
import com.ion.jewelry.model.entity.QnaBoard;
import com.ion.jewelry.model.entity.ReviewBoard;
import com.ion.jewelry.model.enums.ObjectStatus;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.ItemRequest;
import com.ion.jewelry.model.network.response.CartResponse;
import com.ion.jewelry.model.network.response.ImageFileResponse;
import com.ion.jewelry.model.network.response.ItemInfoResponse;
import com.ion.jewelry.model.network.response.ItemResponse;
import com.ion.jewelry.model.network.response.OrderDetailResponse;
import com.ion.jewelry.model.network.response.QnaBoardReplyResponse;
import com.ion.jewelry.model.network.response.QnaBoardResponse;
import com.ion.jewelry.model.network.response.ReviewBoardReplyResponse;
import com.ion.jewelry.model.network.response.ReviewBoardResponse;
import com.ion.jewelry.repository.CartRepository;
import com.ion.jewelry.repository.CategoryRepository;
import com.ion.jewelry.repository.ItemRepository;

@Service
public class ItemService extends AABaseService<ItemRequest, ItemResponse, Item> {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
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
	
	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Override
	public Header<ItemResponse> create(Header<ItemRequest> request) {
		//1. ????????? ???????????? ??????
		ItemRequest itemRequest = request.getData();
		
		//2. ????????? ????????? -> DB??? ??????
		Item item = Item.builder()
				.name(itemRequest.getName())
				.price(itemRequest.getPrice())
				.stock(itemRequest.getStock())
				.status(ObjectStatus.REGISTERED)
				.category(categoryRepo.getOne(itemRequest.getCategoryId()))
				.build();
		Item newItem = baseRepo.save(item);
		
		//3. ????????? DB????????? ??????
		ItemResponse res = response(newItem);
		return Header.OK(res);
	}

	@Override
	public Header<ItemResponse> update(Header<ItemRequest> request) {
		//1. ??????????????? ???????????? ??????
		ItemRequest itemRequest = request.getData();
		
		//2. ??????????????? ????????? ??????
		Optional<Item> optional = baseRepo.findById(itemRequest.getId());
		
		//3. ??????????????? ????????? -> DB?????? -> ??????????????? ????????? ?????? 
		return optional
				.map(item -> {
					item
						.setName(itemRequest.getName())
						.setPrice(itemRequest.getPrice())
						.setStock(itemRequest.getStock())
						.setStatus(itemRequest.getStatus())
						.setCategory(categoryRepo.getOne(itemRequest.getCategoryId()));
					return item;
				})
				.map(item -> baseRepo.save(item))
				.map(item -> response(item))
				.map(item -> Header.OK(item))
				.orElseGet(() -> Header.ERROR("??????????????? ???????????? ????????????"));
	}
	public Header<ItemResponse> updateStockMinus(Header<ItemRequest> request) {
		ItemRequest itemRequest = request.getData();
		Optional<Item> optional = baseRepo.findById(itemRequest.getId());
		
		return optional
				.map(item -> {
					if(item.getStock() - itemRequest.getStock() == 0) itemRequest.setStatus(ObjectStatus.UNREGISTERED);
					else itemRequest.setStatus(ObjectStatus.REGISTERED);
					item
						.setStock(item.getStock() - itemRequest.getStock())
						.setStatus(itemRequest.getStatus());
					return item;
				})
				.map(item -> baseRepo.save(item))
				.map(item -> response(item))
				.map(item -> Header.OK(item))
				.orElseGet(() -> Header.ERROR("??????????????? ???????????? ????????????"));
	}
	public Header<ItemResponse> updateStockPlus(Header<ItemRequest> request) {
		ItemRequest itemRequest = request.getData();
		Optional<Item> optional = baseRepo.findById(itemRequest.getId());
		
		return optional
				.map(item -> {
					if(item.getStock() + itemRequest.getStock() > 0) itemRequest.setStatus(ObjectStatus.REGISTERED);
					else itemRequest.setStatus(ObjectStatus.UNREGISTERED);
					item
						.setStock(item.getStock() + itemRequest.getStock())
						.setStatus(itemRequest.getStatus());
					return item;
				})
				.map(item -> baseRepo.save(item))
				.map(item -> response(item))
				.map(item -> Header.OK(item))
				.orElseGet(() -> Header.ERROR("??????????????? ???????????? ????????????"));
	}

	@Override
	public Header delete(Long id) {
		// 1. ????????? ???????????? ??????
		Optional<Item> optional = baseRepo.findById(id);
		
		// 2. ????????? ?????? -> ???????????? ????????? ??????
		return optional
				.map(item -> {
					baseRepo.delete(item);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("????????? ???????????? ????????????."));
	}

	@Override
	public Header<ItemResponse> selectRead(Long id) {
		// 1. ????????? ????????? ??????
		Optional<Item> optional = baseRepo.findById(id);
		
		// 2. ????????? ????????? ??????
		return optional
				.map(item -> response(item))
				.map(item -> Header.OK(item))
				.orElseGet(() -> Header.ERROR("???????????? ???????????? ????????????."));
	}

	@Override
	public Header<List<ItemResponse>> allRead() {
		// 1. ????????? ?????? ????????? ??????
		List<Item> itemList = baseRepo.findAll();
		
		// 2. ????????? ????????? ??????
		List<ItemResponse> itemResList = itemList.stream()
					.map(item -> response(item))
					.collect(Collectors.toList());
		return Header.OK(itemResList);
	}

	@Override
	public Header<List<ItemResponse>> pagingRead(Pageable pageable) {
		// 1. ????????? ?????? ????????? ??????(????????? ?????? ??????)
		Page<Item> page = baseRepo.findAll(pageable);
		
		// 2. ????????? ????????? ??????(????????? ?????? ??????)
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
	
	@Transactional
	public Header<List<ItemResponse>> search(String keyword, Pageable pageable) {
		Page<Item> page = itemRepo.findByNameContainsIgnoreCase(keyword, pageable);
		
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
	
	@Transactional
	public Header<List<ItemResponse>> searchId(Long keyword, Pageable pageable) {
		Page<Item> page = itemRepo.findById(keyword, pageable);
		
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
	
	@Transactional
	public Header<List<ItemResponse>> searchCategoryId(Long keyword, Pageable pageable) {
		Page<Item> page = itemRepo.findByCategoryId(keyword, pageable);
		
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
	
	@Transactional
	public Header<List<ItemResponse>> searchStatus(ObjectStatus keyword, Pageable pageable) {
		Page<Item> page = itemRepo.findByStatus(keyword, pageable);
		
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
	
	//?????????????????? ???????????? QNA, REVIEW ?????? ??????(?????? ??????)---------------------------------------------------------------------------------
	public Header<ItemInfoResponse> itemInfo(Long id){
		
		Item item = baseRepo.getOne(id);
		ItemResponse itemResponse = response(item);
		
		//?????????????????? ???????????? QNA???????????? ??????(?????? ??????)
		List<QnaBoard> qnaBoardList = item.getQnaBoardList(); 
		List<QnaBoardResponse> qnaBoardResponseList = qnaBoardList.stream()
				.map(qna -> {
					QnaBoardResponse qnaBoardResponse = qnaBoardService.response(qna);
					
					//QnaBoardReply response
					List<QnaBoardReplyResponse> qnaReplyResList = qna.getQnaBoardReplyList().stream()
							.map(reply -> qnaBoardReplyService.response(reply))
							.collect(Collectors.toList());
					
					qnaBoardResponse.setQnaBoardReplyResponseList(qnaReplyResList);
					return qnaBoardResponse;
				})
				.sorted((a, b) -> (int)(b.id - a.id))
				.collect(Collectors.toList());
		itemResponse.setQnaBoardResponseList(qnaBoardResponseList);
		
		//?????????????????? ???????????? review???????????? ??????(?????? ??????)
		List<ReviewBoard> reviewBoardList = item.getReviewBoardList();
		List<ReviewBoardResponse> reviewBoardResponseList = reviewBoardList.stream()
				.map(review -> {
					ReviewBoardResponse reviewBoardResponse = reviewBoardService.response(review);
					
					//ReviewBoardReply response
					List<ReviewBoardReplyResponse> reviewReplyResList = review.getReviewBoardReplyList().stream()
							.map(reply -> reviewBoardReplyService.response(reply))
							.collect(Collectors.toList());
					
					reviewBoardResponse.setReviewBoardReplyResponseList(reviewReplyResList);
					return reviewBoardResponse; 
				})
				.sorted((a, b) -> (int)(b.id - a.id))
				.collect(Collectors.toList());
		itemResponse.setReviewBoardResponseList(reviewBoardResponseList);
		
		//?????????????????? ???????????? ?????????????????? ??????
		List<ImageFile> imageFileList = item.getImageFileList();
		List<ImageFileResponse> imageFileResponseList = imageFileList.stream()
				.map(imageFile -> imageFileService.response(imageFile))
				.collect(Collectors.toList());
		itemResponse.setImageFileResponseList(imageFileResponseList);
		
		//?????????????????? ???????????? ???????????? ??????
		List<Cart> cartList = item.getCartList();
		List<CartResponse> cartResponseList = cartList.stream()
				.map(cart -> cartService.response(cart))
				.collect(Collectors.toList());
		itemResponse.setCartResponseList(cartResponseList);
		
		//?????????????????? ???????????? ???????????? ??????
		List<OrderDetail> orderDetailList = item.getOrderDetailList();
		List<OrderDetailResponse> orderDetailResponseList = orderDetailList.stream()
				.map(orderDetail -> orderDetailService.response(orderDetail))
				.collect(Collectors.toList());
		itemResponse.setOrderDetailResponseList(orderDetailResponseList);
		
		//????????? ????????? ?????????!
		ItemInfoResponse itemQnaBoardInfoResponse = ItemInfoResponse.builder()
				.itemResponse(itemResponse).build();
		
		return Header.OK(itemQnaBoardInfoResponse);
	}
	//--------------------------------------------------------------------------------------------------------------------------------------
	// ?????? ?????????
	public ItemResponse response(Item item) {
		ItemResponse res = ItemResponse.builder()
				.id(item.getId())
				.name(item.getName())
				.price(item.getPrice())
				.stock(item.getStock())
				.status(item.getStatus())
				.createdAt(item.getCreatedAt())
				.createdBy(item.getCreatedBy())
				.categoryId(item.getCategory().getId())
				.updatedAt(item.getUpdatedAt())
				.updatedBy(item.getUpdatedBy())
				.build();
		
		return res;
	}
	
}
