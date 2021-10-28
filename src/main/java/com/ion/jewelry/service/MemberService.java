package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.enums.MemberStatus;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.MemberRequest;
import com.ion.jewelry.model.network.response.MemberResponse;

@Service
public class MemberService extends AABaseService<MemberRequest, MemberResponse, Member> {

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
				.status(MemberStatus.REGISTERED)
				.unregDate(null)
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
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		Optional<Member> optional = baseRepo.findById(id);
		
		return optional
				.map(member -> {
					baseRepo.delete(member);
					return Header.OK();
					})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<MemberResponse> selectRead(Long id) {
		Optional<Member> optional = baseRepo.findById(id);
		
		return optional
				.map(member -> response(member))
				.map(member -> Header.OK(member))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."))
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
				.unregDate(member.getUnregDate())
				.build();
		
		return res;
	}
	
}
