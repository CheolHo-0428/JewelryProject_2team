package com.ion.jewelry.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.Cart;
import com.ion.jewelry.model.entity.ImageFile;
import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.CartRequest;
import com.ion.jewelry.model.network.response.CartResponse;
import com.ion.jewelry.repository.CartRepository;
import com.ion.jewelry.repository.ImageFileRepository;
import com.ion.jewelry.repository.ItemRepository;
import com.ion.jewelry.service.CartService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/jewelry/cart")
public class CartController extends AABaseController<CartRequest, CartResponse, Cart> {
	@Autowired
	CartService cartService;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	ImageFileRepository imageFileRepository;
	
	@Override
	@GetMapping("/paging")
	public Header<List<CartResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		log.info("페이징 데이터 {}", pageable);
		return baseService.pagingRead(pageable);
	}
	//장바구니 조회
	@GetMapping("/selectCart")
	public Header<List<CartResponse>> selectCart(Long member_id) {
		return cartService.selectCart(member_id);
	}
	//장바구니등록
	@PostMapping("/reg")
	public Header<CartResponse> reg(@RequestBody CartRequest request) {
		
		Header<CartRequest> result = new Header<CartRequest>();
		if(cartRepository.existsByMemberIdAndItemId(request.getMemberId(),request.getItemId())==false){
			System.out.println("requestMemberId"+request.getMemberId());
			System.out.println("request.getItemId"+request.getItemId());
			result.setData(request);
			return baseService.create(result);
		}else {
			Optional<Cart> cart = cartRepository.findByMemberIdAndItemId(request.getMemberId(),request.getItemId());
			request.setId(cart.get().getId());
			System.out.println("cartGetId"+cart.get().getId());
			request.setItemCount(cart.get().getItemCount()+request.getItemCount());
			System.out.println("cart++"+cart.get().getItemCount()+request.getItemCount());
			result.setData(request);
			return cartService.update(result);
		}
	}	
}
