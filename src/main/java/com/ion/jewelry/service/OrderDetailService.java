package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ion.jewelry.model.entity.OrderDetail;
import com.ion.jewelry.model.enums.OrderProductState;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.OrderDetailRequest;
import com.ion.jewelry.model.network.response.OrderDetailResponse;
import com.ion.jewelry.repository.ItemRepository;
import com.ion.jewelry.repository.OrderGroupRepository;

@Service
public class OrderDetailService extends 
	AABaseService<OrderDetailRequest, OrderDetailResponse, OrderDetail> {
	
	@Autowired
	private OrderGroupRepository orderGroupRepo;
	
	@Autowired
	private ItemRepository itemRepo;
	
	@Override
	public Header<OrderDetailResponse> create(Header<OrderDetailRequest> request) {
		OrderDetailRequest orderDetailRequest = request.getData();
		if(request.getData().getOrderProductState() != null) {
			OrderDetail orderDetail = OrderDetail.builder()
					.orderCount(orderDetailRequest.getOrderCount())
					.orderPrice(orderDetailRequest.getOrderPrice())
					.orderProductState(OrderProductState.CARD)
					.orderGroup(orderGroupRepo.getOne(orderDetailRequest.getOrderGroupId()))
					.item(itemRepo.getOne(orderDetailRequest.getItemId()))
					.build();
			
			OrderDetail newOrderDetail = baseRepo.save(orderDetail);
			OrderDetailResponse orderDetailResponse = response(newOrderDetail);
			return Header.OK(orderDetailResponse);	
		}else {
		OrderDetail orderDetail = OrderDetail.builder()
				.orderCount(orderDetailRequest.getOrderCount())
				.orderPrice(orderDetailRequest.getOrderPrice())
				.orderProductState(OrderProductState.BEFORE_BANK_TRANSFER)
				.orderGroup(orderGroupRepo.getOne(orderDetailRequest.getOrderGroupId()))
				.item(itemRepo.getOne(orderDetailRequest.getItemId()))
				.build();
		
		OrderDetail newOrderDetail = baseRepo.save(orderDetail);
		OrderDetailResponse orderDetailResponse = response(newOrderDetail);
		return Header.OK(orderDetailResponse);
		}
	}

	@Override
	public Header<OrderDetailResponse> update(Header<OrderDetailRequest> request) {
		OrderDetailRequest orderDetailRequest = request.getData();
		Optional<OrderDetail> optional = baseRepo.findById(orderDetailRequest.getId());
		return optional
				.map(orderDetail -> {
					orderDetail
					.setOrderCount(orderDetailRequest.getOrderCount())
					.setOrderPrice(orderDetailRequest.getOrderPrice())
					.setOrderProductState(orderDetailRequest.getOrderProductState());
					//.setOrderGroup(orderGroupRepo.getOne(orderDetailRequest.getOrderGroupId()))
					//.setItem(itemRepo.getOne(orderDetailRequest.getItemId()));
					return orderDetail;
				})
				.map(orderDetail -> baseRepo.save(orderDetail))
				.map(orderDetail -> response(orderDetail))
				.map(orderDetail -> Header.OK(orderDetail))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		Optional<OrderDetail> optional = baseRepo.findById(id);
		
		return optional
				.map(orderDetail -> {
					baseRepo.delete(orderDetail);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<OrderDetailResponse> selectRead(Long id) {
		Optional<OrderDetail> optional = baseRepo.findById(id);
		return optional
				.map(orderDetail -> response(orderDetail))
				.map(orderDetail -> Header.OK(orderDetail))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."));
	}

	@Override
	public Header<List<OrderDetailResponse>> allRead() {
		List<OrderDetail> orderDetailList = baseRepo.findAll();
		
		List<OrderDetailResponse> orderDetailResList = orderDetailList.stream()
				.map(orderDetail -> response(orderDetail))
				.collect(Collectors.toList());
		
		return Header.OK(orderDetailResList);
	}

	@Override
	public Header<List<OrderDetailResponse>> pagingRead(Pageable pageable) {
		Page<OrderDetail> page = baseRepo.findAll(pageable);
		List<OrderDetailResponse> orderDetailResList = page.stream()
				.map(orderDetail -> response(orderDetail))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(orderDetailResList, pagination);
	}
	
	public OrderDetailResponse response(OrderDetail orderDetail) {
		OrderDetailResponse res = OrderDetailResponse.builder()
				.id(orderDetail.getId())
				.orderCount(orderDetail.getOrderCount())
				.orderPrice(orderDetail.getOrderPrice())
				.orderProductState(orderDetail.getOrderProductState())
				.orderGroupId(orderDetail.getOrderGroup().getId())
				.itemId(orderDetail.getItem().getId())
				.build();
		
		return res;
	}

	
}
