package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.OrderGroup;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.OrderGroupRequest;
import com.ion.jewelry.model.network.response.OrderGroupOrderDetailInfoResponse;
import com.ion.jewelry.model.network.response.OrderGroupResponse;
import com.ion.jewelry.service.OrderGroupService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/orderGroup")
public class OrderGroupController extends 
	AABaseController<OrderGroupRequest, OrderGroupResponse, OrderGroup> {
	
	@Autowired
	private OrderGroupService orderGroupService;
	
	@Override
	@GetMapping("/paging") // http://localhost:8000/jewelry/orderGroup/paging?page=0
	public Header<List<OrderGroupResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		
		log.info("페이징 데이터: {}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	//특정주문에 해당되는 주문상세들 조회
	@GetMapping("/{id}/orderDetailInfo") // http://localhost:8000/jewelry/orderGroup/1/orderDetailInfo
	public Header<OrderGroupOrderDetailInfoResponse> orderDetailInfo(@PathVariable Long id){
		log.info("read id : {}", id);
		return orderGroupService.orderDetailInfo(id);
	}
}
