package com.ion.jewelry.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.entity.OrderDetail;
import com.ion.jewelry.model.entity.OrderGroup;
import com.ion.jewelry.model.enums.OrderProductState;
import com.ion.jewelry.model.enums.PayMethod;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.OrderGroupRequest;
import com.ion.jewelry.model.network.response.OrderDetailResponse;
import com.ion.jewelry.model.network.response.OrderGroupOrderDetailInfoResponse;
import com.ion.jewelry.model.network.response.OrderGroupResponse;
import com.ion.jewelry.repository.MemberRepository;
import com.ion.jewelry.repository.OrderGroupRepository;

@Service
public class OrderGroupService extends 
	AABaseService<OrderGroupRequest, OrderGroupResponse, OrderGroup> {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderGroupRepository orderGroupRepo;
	
	@Override
	public Header<OrderGroupResponse> create(Header<OrderGroupRequest> request) {
		OrderGroupRequest orderGroupRequest = request.getData();
		if(request.getData().getPayAccount() == null) {
			
			OrderGroup orderGroup = OrderGroup.builder()
					.totalPrice(orderGroupRequest.getTotalPrice())
					.totalCount(orderGroupRequest.getTotalCount())
					.deliveryMessage(orderGroupRequest.getDeliveryMessage())
					.resipient(orderGroupRequest.getResipient())
					.Phone(orderGroupRequest.getPhone())
					.orderProductState(OrderProductState.CARD)
					.postCode(orderGroupRequest.getPostCode())
					.address(orderGroupRequest.getAddress())
					.detailAddress(orderGroupRequest.getDetailAddress())
					.payMethod(PayMethod.CARD)
					.depositor(orderGroupRequest.getDepositor())
					.payAccount(orderGroupRequest.getPayAccount())
					.arrivalDate(LocalDateTime.now().plusDays(2))
					.member(memberRepo.getOne(orderGroupRequest.getMemberId()))
					.build();
					
			OrderGroup newOrderGroup = baseRepo.save(orderGroup);
			OrderGroupResponse orderGroupResponse = response(newOrderGroup);
			
			return Header.OK(orderGroupResponse);			
		}else {
			
			OrderGroup orderGroup = OrderGroup.builder().totalPrice(orderGroupRequest.getTotalPrice())
					.totalCount(orderGroupRequest.getTotalCount())
					.deliveryMessage(orderGroupRequest.getDeliveryMessage()).resipient(orderGroupRequest.getResipient())
					.Phone(orderGroupRequest.getPhone()).orderProductState(OrderProductState.BEFORE_BANK_TRANSFER)
					.postCode(orderGroupRequest.getPostCode()).address(orderGroupRequest.getAddress())
					.detailAddress(orderGroupRequest.getDetailAddress()).payMethod(PayMethod.BANK_TRANSFER)
					.depositor(orderGroupRequest.getDepositor()).payAccount(orderGroupRequest.getPayAccount())
					.arrivalDate(LocalDateTime.now().plusDays(2))
					.member(memberRepo.getOne(orderGroupRequest.getMemberId())).build();

			OrderGroup newOrderGroup = baseRepo.save(orderGroup);
			OrderGroupResponse orderGroupResponse = response(newOrderGroup);

			return Header.OK(orderGroupResponse);
		}
	}

	@Override
	public Header<OrderGroupResponse> update(Header<OrderGroupRequest> request) {
		OrderGroupRequest orderGroupRequest = request.getData();
		Optional<OrderGroup> optional = baseRepo.findById(orderGroupRequest.getId());
		
		return optional
				.map(orderGroup -> {
					orderGroup
//					.setTotalPrice(orderGroupRequest.getTotalPrice())
//					.setTotalCount(orderGroupRequest.getTotalCount())
					.setDeliveryMessage(orderGroupRequest.getDeliveryMessage())
					.setResipient(orderGroupRequest.getResipient())
					.setPhone(orderGroupRequest.getPhone())
					.setOrderProductState(orderGroupRequest.getOrderProductState())
					.setPostCode(orderGroupRequest.getPostCode())
					.setAddress(orderGroupRequest.getAddress())
					.setDetailAddress(orderGroupRequest.getDetailAddress());
					//.setPayMethod(orderGroupRequest.getPayMethod())
//					.setDepositor(orderGroupRequest.getDepositor())
//					.setPayAccount(orderGroupRequest.getPayAccount());
					//.setArrivalDate(orderGroupRequest.getArrivalDate())
					//.setMember(memberRepo.getOne(orderGroupRequest.getMemberId()));
					return orderGroup;
				})
				.map(orderGroup -> baseRepo.save(orderGroup))
				.map(orderGroup -> response(orderGroup))
				.map(orderGroup -> Header.OK(orderGroup))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		Optional<OrderGroup> optional = baseRepo.findById(id);
		
		return optional
				.map(orderGroup -> {
					baseRepo.delete(orderGroup);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<OrderGroupResponse> selectRead(Long id) {
		Optional<OrderGroup> optional = baseRepo.findById(id);
		return optional
				.map(orderGroup -> response(orderGroup))
				.map(orderGroup -> Header.OK(orderGroup))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."));
	}

	@Override
	public Header<List<OrderGroupResponse>> allRead() {
		List<OrderGroup> orderGroupList = baseRepo.findAll();
		List<OrderGroupResponse> orderGroupResList = orderGroupList.stream()
				.map(orderGroup -> response(orderGroup))
				.collect(Collectors.toList());
		return Header.OK(orderGroupResList);
	}

	@Override
	public Header<List<OrderGroupResponse>> pagingRead(Pageable pageable) {
		Page<OrderGroup> page = baseRepo.findAll(pageable);
		List<OrderGroupResponse> orderGroupResList = page.stream()
				.map(orderGroup -> response(orderGroup))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(orderGroupResList, pagination);
	}
	
	@Transactional
	public Header<List<OrderGroupResponse>> searchDate(String date1, String date2, Pageable pageable) {
		// 문자열
		String dateStr1 = date1 + "-00-00-00";
		String dateStr2 = date2 + "-23-59-59";
		// 포맷터
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
		// 문자열 -> Date
		LocalDateTime dateStart = LocalDateTime.parse(dateStr1, formatter);
		LocalDateTime dateEnd = LocalDateTime.parse(dateStr2, formatter);
		System.out.println(dateStart);
		System.out.println(dateEnd);
		
		Page<OrderGroup> page = orderGroupRepo.findByCreatedAtBetween(dateStart, dateEnd, pageable);
		
		List<OrderGroupResponse> OrderGroupResList = page.stream()
				.map(OrderGroup -> response(OrderGroup))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(OrderGroupResList, pagination);
	}
	
	@Transactional
	public Header<List<OrderGroupResponse>> searchState(OrderProductState state, Pageable pageable) {
		
		Page<OrderGroup> page = orderGroupRepo.findByOrderProductState(state, pageable);
		
		List<OrderGroupResponse> OrderGroupResList = page.stream()
				.map(OrderGroup -> response(OrderGroup))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(OrderGroupResList, pagination);
	}
	
	@Transactional
	public Header<List<OrderGroupResponse>> searchName(String name, Pageable pageable) {
		
		Page<OrderGroup> page = orderGroupRepo.findByResipientContaining(name, pageable);
		
		List<OrderGroupResponse> OrderGroupResList = page.stream()
				.map(OrderGroup -> response(OrderGroup))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(OrderGroupResList, pagination);
	}
	
	@Transactional
	public Header<List<OrderGroupResponse>> searchMember(Long id, Pageable pageable) {
		Member member = memberRepo.getOne(id);
		Page<OrderGroup> page = orderGroupRepo.findByMember(member, pageable);
		
		List<OrderGroupResponse> orderGroupResList = page.stream()
				.map(orderGroup -> response(orderGroup))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(orderGroupResList, pagination);
	}
	
	//특정주문에 해당되는 주문상세들 조회
	public Header<OrderGroupOrderDetailInfoResponse> orderDetailInfo(Long id){
		OrderGroup orderGroup = baseRepo.getOne(id);
		OrderGroupResponse orderGroupResponse = response(orderGroup);
		
		List<OrderDetail> orderDetailList = orderGroup.getOrderDetailList();
		List<OrderDetailResponse> orderDetailResList = orderDetailList.stream()
				.map(orderDetail -> {
					OrderDetailResponse orderDetailResponse = 
							orderDetailService.response(orderDetail);
					return orderDetailResponse;
					})
				.collect(Collectors.toList());
		orderGroupResponse.setOrderDetailResponseList(orderDetailResList);
		
		OrderGroupOrderDetailInfoResponse orderDetailInfoResponse = 
				OrderGroupOrderDetailInfoResponse.builder()
					.orderGroupResponse(orderGroupResponse).build();
		return Header.OK(orderDetailInfoResponse);
	}
	

	public OrderGroupResponse response(OrderGroup orderGroup) {
		OrderGroupResponse res = OrderGroupResponse.builder()
				.id(orderGroup.getId())
				.totalPrice(orderGroup.getTotalPrice())
				.totalCount(orderGroup.getTotalCount())
				.deliveryMessage(orderGroup.getDeliveryMessage())
				.resipient(orderGroup.getResipient())
				.Phone(orderGroup.getPhone())
				.orderProductState(orderGroup.getOrderProductState())
				.postCode(orderGroup.getPostCode())
				.address(orderGroup.getAddress())
				.detailAddress(orderGroup.getDetailAddress())
				.payMethod(orderGroup.getPayMethod())
				.depositor(orderGroup.getDepositor())
				.payAccount(orderGroup.getPayAccount())
				.arrivalDate(orderGroup.getArrivalDate())
				.memberId(orderGroup.getMember().getId())
				.createdAt(orderGroup.getCreatedAt())
				.build();
				
		return res;
	}
}
