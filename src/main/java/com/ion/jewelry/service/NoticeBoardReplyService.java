package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ion.jewelry.model.entity.NoticeBoardReply;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.NoticeBoardReplyRequest;
import com.ion.jewelry.model.network.response.NoticeBoardReplyResponse;
import com.ion.jewelry.repository.NoticeBoardRepository;

@Service
public class NoticeBoardReplyService extends AABaseService<NoticeBoardReplyRequest, NoticeBoardReplyResponse, NoticeBoardReply> {
	
	@Autowired
	private NoticeBoardRepository boardRepo;
	
	@Override
	public Header<NoticeBoardReplyResponse> create(Header<NoticeBoardReplyRequest> request) {
		//1. 생성할 데이터를 요청
		NoticeBoardReplyRequest replyRequest = request.getData();
		
		//2. 요청한 데이터 -> DB에 저장
		NoticeBoardReply reply = NoticeBoardReply.builder()
		.writer(replyRequest.getWriter())
		.content(replyRequest.getContent())
		.noticeBoard(boardRepo.getOne(replyRequest.getNoticeBoardId()))
		.build();
		
		NoticeBoardReply newReply = baseRepo.save(reply);
		
		//3. 저장된 DB데이터 응답
		NoticeBoardReplyResponse res = response(newReply);
		return Header.OK(res);
	}

	@Override
	public Header<NoticeBoardReplyResponse> update(Header<NoticeBoardReplyRequest> request) {
		//1. 업데이트할 데이터를 요청
		NoticeBoardReplyRequest replyRequest = request.getData();
		
		//2. 업데이트할 데이터 검색
		Optional<NoticeBoardReply> optional = baseRepo.findById(replyRequest.getId());
		
		//3. 업데이트한 데이터 -> DB저장 -> 업데이트한 데이터 응답 
		return optional
				.map(reply -> {
					reply
						.setWriter(replyRequest.getWriter())
						.setContent(replyRequest.getContent());
						//.setNoticeBoard(boardRepo.getOne(replyRequest.getNoticeBoardId()));
					return reply;
				})
				.map(reply -> baseRepo.save(reply))
				.map(reply -> response(reply))
				.map(reply -> Header.OK(reply))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		// 1. 삭제할 데이터를 요청
		Optional<NoticeBoardReply> optional = baseRepo.findById(id);
		
		// 2. 데이터 삭제 -> 삭제완료 메세지 응답
		return optional
				.map(reply -> {
					baseRepo.delete(reply);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));		
	}

	@Override
	public Header<NoticeBoardReplyResponse> selectRead(Long id) {
		// 1. 조회할 데이터 요청
		Optional<NoticeBoardReply> optional = baseRepo.findById(id);
		
		// 2. 조회한 데이터 응답
		return optional
				.map(reply -> response(reply))
				.map(reply -> Header.OK(reply))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."));
	}

	@Override
	public Header<List<NoticeBoardReplyResponse>> allRead() {
		// 1. 조회할 전체 데이터 요청
		List<NoticeBoardReply> replyList = baseRepo.findAll();
		
		// 2. 조회한 데이터 응답
		List<NoticeBoardReplyResponse> replyResList = replyList.stream()
					.map(reply -> response(reply))
					.collect(Collectors.toList());
		
		return Header.OK(replyResList);
	}

	@Override
	public Header<List<NoticeBoardReplyResponse>> pagingRead(Pageable pageable) {
		// 1. 조회할 전체 데이터 요청(페이지 정보 포함)
		Page<NoticeBoardReply> page = baseRepo.findAll(pageable);
		
		// 2. 조회한 데이터 응답(페이지 정보 포함)
		List<NoticeBoardReplyResponse> replyResList = page.stream()
				.map(reply -> response(reply))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(replyResList, pagination);
	}
	
	// 응답 메소드
	public NoticeBoardReplyResponse response(NoticeBoardReply reply) {
		NoticeBoardReplyResponse res = NoticeBoardReplyResponse.builder()
					.id(reply.getId())
					.writer(reply.getWriter())
					.content(reply.getContent())
					.noticeBoardId(reply.getNoticeBoard().getId())
					.updatedAt(reply.getUpdatedAt())
					.build();
	
		return res;
	}
}
