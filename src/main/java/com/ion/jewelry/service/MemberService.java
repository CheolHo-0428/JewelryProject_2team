package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.entity.OrderGroup;
import com.ion.jewelry.model.enums.ObjectStatus;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.MemberRequest;
import com.ion.jewelry.model.network.response.MemberGroupOrderInfoResponse;
import com.ion.jewelry.model.network.response.MemberResponse;
import com.ion.jewelry.model.network.response.OrderDetailResponse;
import com.ion.jewelry.model.network.response.OrderGroupResponse;
import com.ion.jewelry.repository.MemberRepository;

@Service
public class MemberService extends AABaseService<MemberRequest, MemberResponse, Member> {
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	private OrderGroupService orderGroupService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Override
	public Header<MemberResponse> create(Header<MemberRequest> request) {
		MemberRequest memberRequest = request.getData(); 
				
		Member member = Member.builder()
				.account(memberRequest.getAccount())
				.name(memberRequest.getName())
				.password(memberRequest.getPassword())
				.email(memberRequest.getEmail())
				.phone(memberRequest.getPhone())
				.postCode(memberRequest.getPostCode())
				.address(memberRequest.getAddress())
				.detailAddress(memberRequest.getDetailAddress())
				.status(ObjectStatus.REGISTERED)
				//.unregDate(null)
				.build();
		
		Member newMember = baseRepo.save(member);
		
		return Header.OK(response(newMember));
	}

	@Override
	public Header<MemberResponse> update(Header<MemberRequest> request) {
		MemberRequest memberRequest = request.getData();
		
		Optional<Member> optional = baseRepo.findById(memberRequest.getId());
			
		return optional
				.map(member -> {
					member
						.setId(memberRequest.getId())
						.setAccount(memberRequest.getAccount())
						.setPassword(memberRequest.getPassword())
						.setEmail(memberRequest.getEmail())
						.setPhone(memberRequest.getPhone())
						.setPostCode(memberRequest.getPostCode())
						.setAddress(memberRequest.getAddress())
						.setDetailAddress(memberRequest.getDetailAddress())
						.setStatus(memberRequest.getStatus());
					
					return member;
				})
				.map(member -> baseRepo.save(member))
				.map(member -> response(member))
				.map(member -> Header.OK(member))
				.orElseGet(() -> Header.ERROR("??????????????? ???????????? ????????????."));
	}

	@Override
	public Header delete(Long id) {
		Optional<Member> optional = baseRepo.findById(id);
		
		return optional
				.map(member -> {
					baseRepo.delete(member);
					return Header.OK();
					})
				.orElseGet(() -> Header.ERROR("????????? ???????????? ????????????."));
	}

	@Override
	public Header<MemberResponse> selectRead(Long id) {
		Optional<Member> optional = baseRepo.findById(id);
		
		return optional
				.map(member -> response(member))
				.map(member -> Header.OK(member))
				.orElseGet(() -> Header.ERROR("???????????? ???????????? ????????????."))
				;
	}

	@Override
	public Header<List<MemberResponse>> allRead() {
		List<Member> memberList = baseRepo.findAll();
		
		List<MemberResponse> memberResList = memberList.stream()
				.map(member -> response(member))
				.collect(Collectors.toList());
		
		return Header.OK(memberResList);
	}

	@Override
	public Header<List<MemberResponse>> pagingRead(Pageable pageable) {
		Page<Member> page = baseRepo.findAll(pageable);
		
		List<MemberResponse> memberResList = page.stream()
				.map(member -> response(member))
				.collect(Collectors.toList());
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(memberResList, pagination);
	}
	
	//??????????????? ???????????? ??????????????? ??????
	public Header<MemberGroupOrderInfoResponse> orderGroupInfo(Long id){
		Member member = baseRepo.getOne(id);
		MemberResponse memberResponse = response(member);
		
		List<OrderGroup> orderGroupList = member.getOrderGroupList(); 
		List<OrderGroupResponse> orderGroupResList = orderGroupList.stream()
				.map(orderGroup -> {
					OrderGroupResponse orderGroupResponse = orderGroupService.response(orderGroup);
					
					//OrderDetail response
					List<OrderDetailResponse> orderDetailResList = orderGroup.getOrderDetailList().stream()
							.map(orderDetail -> orderDetailService.response(orderDetail))
							.collect(Collectors.toList());
					
					orderGroupResponse.setOrderDetailResponseList(orderDetailResList);
					return orderGroupResponse;
					})
				.sorted((a, b) -> (int)(b.id - a.id))
				.collect(Collectors.toList());
		
		memberResponse.setOrderGroupList(orderGroupResList);
		
		MemberGroupOrderInfoResponse memberGroupOrderInfoResponse = MemberGroupOrderInfoResponse.builder()
				.memberResponse(memberResponse).build();
		
		return Header.OK(memberGroupOrderInfoResponse);
	}
	
	public MemberResponse response(Member member) {
		MemberResponse res = MemberResponse.builder()
				.id(member.getId())
				.account(member.getAccount())
				.name(member.getName())
				.password(member.getPassword())
				.email(member.getEmail())
				.phone(member.getPhone())
				.postCode(member.getPostCode())
				.address(member.getAddress())
				.detailAddress(member.getDetailAddress())
				.status(member.getStatus())
				.createdAt(member.getCreatedAt())
				.adminMessage(member.getAdminMessage())
				//.unregDate(member.getUnregDate())
				.build();
		
		return res;
	}

	public Header<MemberResponse> findByNameAndEmail(String name,String email) {
		Optional<Member> optional = memberRepository.findByNameAndEmail(name,email);
		return optional
				.map(member -> response(member))
				.map(member -> Header.OK(member))
				.orElseGet(() -> Header.ERROR("???????????? ???????????? ????????????."))
				;
	}

	public Header<MemberResponse> findByNameAndAccountAndEmail(String name, String account, String email) {
		Optional<Member> optional = memberRepository.findByNameAndAccountAndEmail(name,account,email);
		return optional
				.map(member -> response(member))
				.map(member -> Header.OK(member))
				.orElseGet(() -> Header.ERROR("???????????? ???????????? ????????????."))
				;
	}
	public Header<MemberResponse> updatePassword(Header<MemberRequest> request) {
		
		MemberRequest memberRequest = request.getData();
		
		Optional<Member> optional = memberRepository.findByAccount(memberRequest.getAccount());
			
		return optional
				.map(member -> {
					member
						.setPassword(memberRequest.getPassword());
					return member;
				})
				.map(member -> memberRepository.save(member))
				.map(member -> response(member))
				.map(member -> Header.OK(member))
				.orElseGet(() -> Header.ERROR("??????????????? ???????????? ????????????."));
	}

	public Header<MemberResponse> findByAccount(String account) {
		Optional<Member> optional = memberRepository.findByAccount(account);
		return optional
				.map(member -> response(member))
				.map(member -> Header.OK(member))
				.orElseGet(() -> Header.ERROR("???????????? ???????????? ????????????."))
				;
	}

	public Header<MemberResponse> updateModify(Header<MemberRequest> request) {
		MemberRequest memberRequest = request.getData();
		
		Optional<Member> optional = memberRepository.findByAccount(memberRequest.getAccount());
			
		return optional
				.map(member -> {
					member
						.setName(memberRequest.getName())
						.setPassword(memberRequest.getPassword())
						.setPhone(memberRequest.getPhone())
						.setPostCode(memberRequest.getPostCode())
						.setAddress(memberRequest.getAddress())
						.setDetailAddress(memberRequest.getDetailAddress());
					
					return member;
				})
				.map(member -> memberRepository.save(member))
				.map(member -> response(member))
				.map(member -> Header.OK(member))
				.orElseGet(() -> Header.ERROR("??????????????? ???????????? ????????????."));
	}
	@Transactional
	public Header<List<MemberResponse>> searchAccount(String keyword, Pageable pageable) {
		Page<Member> page = memberRepository.findByAccountContaining(keyword, pageable);
		
		List<MemberResponse> admemberList = page.stream()
				.map(member -> response(member))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(admemberList, pagination);
	}
	@Transactional
	public Header<List<MemberResponse>> searchName(String keyword, Pageable pageable) {
		Page<Member> page = memberRepository.findByNameContaining(keyword, pageable);
		
		List<MemberResponse> admemberList = page.stream()
				.map(member -> response(member))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(admemberList, pagination);
	}

	public Header<MemberResponse> updateMemo(Header<MemberRequest> result) {
		MemberRequest memberRequest = result.getData();
		
		Optional<Member> optional = memberRepository.findByAccount(memberRequest.getAccount());
		return optional
				.map(member -> {
					member
						.setAdminMessage(memberRequest.getAdminMessage());
					return member;
				})
				.map(member -> memberRepository.save(member))
				.map(member -> response(member))
				.map(member -> Header.OK(member))
				.orElseGet(() -> Header.ERROR("??????????????? ???????????? ????????????."));
	}
}
