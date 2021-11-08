package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@CrossOrigin("http://localhost:8080/")
public class OrderGroupController extends 
	AABaseController<OrderGroupRequest, OrderGroupResponse, OrderGroup> {
	
	@Autowired
	private OrderGroupService orderGroupService;
	
	@Override
	@GetMapping("/paging") // http://localhost:8000/jewelry/orderGroup/paging?page=0
	public Header<List<OrderGroupResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10)
			Pageable pageable) {
		
		log.info("페이징 데이터: {}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	@GetMapping("/{id}/searchMember") // member별 paging처리 // http://localhost:8000/jewelry/orderGroup/41/searchMember?page=0
	public Header<List<OrderGroupResponse>> searchMember(@PathVariable Long id, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		System.out.println("memId : " + id);
		return orderGroupService.searchMember(id, pageable);
	}
	
	//특정주문에 해당되는 주문상세들 조회
	@GetMapping("/{id}/orderDetailInfo") // http://localhost:8000/jewelry/orderGroup/1/orderDetailInfo
	public Header<OrderGroupOrderDetailInfoResponse> orderDetailInfo(@PathVariable Long id){
		log.info("read id : {}", id);
		return orderGroupService.orderDetailInfo(id);
	}
	
	@PostMapping("/reg")
	public Header<OrderGroupResponse> create(@RequestBody OrderGroupRequest request) {
		Header<OrderGroupRequest> result = new Header<OrderGroupRequest>();
		result.setData(request);
		
		return baseService.create(result);
	}
	
	@PutMapping("/update")
	public Header<OrderGroupResponse> update(@RequestBody OrderGroupRequest request) {

		Header<OrderGroupRequest> result = new Header<OrderGroupRequest>();
		result.setData(request);

		return baseService.update(result);
	}

}
