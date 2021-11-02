package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ion.jewelry.model.entity.OrderDetail;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.OrderDetailRequest;
import com.ion.jewelry.model.network.response.OrderDetailResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/orderDetail")
public class OrderDetailController extends 
	AABaseController<OrderDetailRequest, OrderDetailResponse, OrderDetail> {

	@Override
	@GetMapping("/paging")
	public Header<List<OrderDetailResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		
		log.info("페이징 데이터 : {}", pageable);
		return baseService.pagingRead(pageable);
	}
}
