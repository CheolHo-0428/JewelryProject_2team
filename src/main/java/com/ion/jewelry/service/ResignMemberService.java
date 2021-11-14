package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.entity.ResignMember;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.ResignMemberRequest;
import com.ion.jewelry.model.network.response.MemberResponse;
import com.ion.jewelry.model.network.response.ResignMemberResponse;
import com.ion.jewelry.repository.ResignMemberRepository;

@Service
public class ResignMemberService extends 
	AABaseService<ResignMemberRequest, ResignMemberResponse, ResignMember> {
	@Autowired
	ResignMemberRepository resignRepository;
	@Override
	public Header<ResignMemberResponse> create(Header<ResignMemberRequest> request) {
		// 사용하지마세요~
		return null;
	}

	@Override
	public Header<ResignMemberResponse> update(Header<ResignMemberRequest> request) {
		// 사용하지마세요~
		return null;
	}

	@Override
	public Header delete(Long id) {
		Optional<ResignMember> optioanl = baseRepo.findById(id);		
		return optioanl
				.map(resignMember -> {
					baseRepo.delete(resignMember);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<ResignMemberResponse> selectRead(Long id) {
		Optional<ResignMember> optioanl = baseRepo.findById(id);
		return optioanl
				.map(resignMember -> response(resignMember))
				.map(resignMember -> Header.OK(resignMember))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."));
	}

	@Override
	public Header<List<ResignMemberResponse>> allRead() {
		List<ResignMember> resignMemberList = baseRepo.findAll();
		List<ResignMemberResponse> resignMemberResList = resignMemberList.stream()
				.map(resignMember -> response(resignMember))
				.collect(Collectors.toList());
		
		return Header.OK(resignMemberResList);
	}

	@Override
	public Header<List<ResignMemberResponse>> pagingRead(Pageable pageable) {
		Page<ResignMember> page = baseRepo.findAll(pageable);
		List<ResignMemberResponse> resignMemberResList = page.stream()
				.map(resignMember -> response(resignMember))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(resignMemberResList, pagination);
	}

	public ResignMemberResponse response(ResignMember resignMember) {
		ResignMemberResponse res = ResignMemberResponse.builder()
				.num(resignMember.getNum())
				.id(resignMember.getId())
				.account(resignMember.getAccount())
				.name(resignMember.getName())
				.password(resignMember.getPassword())
				.email(resignMember.getEmail())
				.phone(resignMember.getPhone())
				.postCode(resignMember.getPostCode())
				.address(resignMember.getAddress())
				.detailAddress(resignMember.getDetailAddress())
				.createdAt(resignMember.getCreatedAt())
				.createdBy(resignMember.getCreatedBy())
				.updatedAt(resignMember.getUpdatedAt())
				.updatedBy(resignMember.getUpdatedBy())
				.unregDate(resignMember.getUnregDate())
				.status(resignMember.getStatus())
				.build();
		
		return res;
	}

	public Header<List<ResignMemberResponse>> searchAccount(String keyword, Pageable pageable) {
		Page<ResignMember> page = resignRepository.findByAccountContaining(keyword, pageable);
		
		List<ResignMemberResponse> admemberList = page.stream()
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

	public Header<List<ResignMemberResponse>> searchName(String keyword, Pageable pageable) {
		Page<ResignMember> page = resignRepository.findByNameContaining(keyword, pageable);
		
		List<ResignMemberResponse> admemberList = page.stream()
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
}
