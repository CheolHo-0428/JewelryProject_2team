package com.ion.jewelry.controller;

import java.util.List;

import javax.websocket.server.PathParam;

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
import com.ion.jewelry.model.enums.ObjectStatus;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.CartRequest;
import com.ion.jewelry.model.network.response.CartResponse;
import com.ion.jewelry.model.network.response.ItemResponse;
import com.ion.jewelry.service.CartService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/jewelry/cart")
public class CartController extends AABaseController<CartRequest, CartResponse, Cart> {
	@Autowired
	CartService cartService;
	
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
	public Header<List<CartResponse>> findByMemberId(@PathParam("member_id") Long member_id,@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		return cartService.findByMemberId(member_id,pageable);
	}
	//장바구니등록
	@PostMapping("/reg")
	public Header<CartResponse> create(@RequestBody CartRequest request) {
		Header<CartRequest> result = new Header<CartRequest>();

		result.setData(request);
		
		return baseService.create(result);
	}	
}
