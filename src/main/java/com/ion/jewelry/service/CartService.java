package com.ion.jewelry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ion.jewelry.model.entity.Cart;
import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.CartRequest;
import com.ion.jewelry.model.network.response.CartResponse;
import com.ion.jewelry.model.network.response.ItemResponse;
import com.ion.jewelry.repository.CartRepository;
import com.ion.jewelry.repository.ItemRepository;

@Service
public class CartService extends AABaseService<CartRequest, CartResponse, Cart> {

	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private CartRepository cartRepo;

	@Override
	public Header<CartResponse> create(Header<CartRequest> request) {
		CartRequest cartRequest = request.getData();

		Cart cart = Cart.builder().itemCount(cartRequest.getItemCount()).memberId(cartRequest.getMemberId())
				.item(itemRepo.getOne(cartRequest.getItemId())).build();

		Cart newCart = baseRepo.save(cart);
		CartResponse cartResponse = response(newCart);
		return Header.OK(cartResponse);
	}

	@Override
	public Header<CartResponse> update(Header<CartRequest> request) {
		CartRequest cartRequest = request.getData();

		Optional<Cart> optional = baseRepo.findById(cartRequest.getId());
		return optional.map(cart -> {
			cart.setItemCount(cartRequest.getItemCount()).setMemberId(cartRequest.getMemberId())
					.setItem(itemRepo.getOne(cartRequest.getItemId()));
			return cart;
		}).map(cart -> baseRepo.save(cart)).map(cart -> response(cart)).map(cart -> Header.OK(cart))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		Optional<Cart> optional = baseRepo.findById(id);

		return optional.map(cart -> {
			baseRepo.delete(cart);
			return Header.OK();
		}).orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<CartResponse> selectRead(Long id) {
		Optional<Cart> optional = baseRepo.findById(id);

		return optional.map(cart -> response(cart)).map(cart -> Header.OK(cart))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."));
	}

	@Override
	public Header<List<CartResponse>> allRead() {
		List<Cart> cartList = baseRepo.findAll();
		List<CartResponse> cartResList = cartList.stream().map(cart -> response(cart)).collect(Collectors.toList());
		return Header.OK(cartResList);
	}

	@Override
	public Header<List<CartResponse>> pagingRead(Pageable pageable) {
		Page<Cart> page = baseRepo.findAll(pageable);
		List<CartResponse> cartResList = page.stream().map(cart -> response(cart)).collect(Collectors.toList());

		Pagination pagination = Pagination.builder().totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements()).currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements()).build();

		return Header.OK(cartResList, pagination);
	}

	public CartResponse response(Cart cart) {
		CartResponse res = CartResponse.builder().id(cart.getId()).itemCount(cart.getItemCount())
				.memberId(cart.getMemberId()).itemId(cart.getItem().getId()).build();

		return res;
	}
	public Header<List<CartResponse>> selectCart(Long member_id) {
		
		List<Cart> cartList = cartRepo.findByMemberId(member_id);
		List<CartResponse> cartResList = cartList.stream().map(cart -> response(cart)).collect(Collectors.toList());
		return Header.OK(cartResList);
	}
}
