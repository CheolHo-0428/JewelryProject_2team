package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ion.jewelry.model.entity.ReviewBoardReply;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.ReviewBoardReplyRequest;
import com.ion.jewelry.model.network.response.ReviewBoardReplyResponse;
import com.ion.jewelry.repository.ReviewBoardRepository;

@Service
public class ReviewBoardReplyService extends 
	AABaseService<ReviewBoardReplyRequest, ReviewBoardReplyResponse, ReviewBoardReply> {

	@Autowired
	private ReviewBoardRepository reviewBoardRepo;
	
	@Override
	public Header<ReviewBoardReplyResponse> create(Header<ReviewBoardReplyRequest> request) {
		ReviewBoardReplyRequest replyRequest = request.getData();
		
		ReviewBoardReply reviewReply = ReviewBoardReply.builder()
				.writer(replyRequest.getWriter())
				.content(replyRequest.getContent())
				.reviewBoard(reviewBoardRepo.getOne(replyRequest.getReviewBoardId()))
				.build();
		
		ReviewBoardReply newReviewReply = baseRepo.save(reviewReply);
		ReviewBoardReplyResponse replyResponse = response(newReviewReply);

		return Header.OK(replyResponse);
	}

	@Override
	public Header<ReviewBoardReplyResponse> update(Header<ReviewBoardReplyRequest> request) {
		ReviewBoardReplyRequest replyRequest = request.getData();
		
		Optional<ReviewBoardReply> optional = baseRepo.findById(replyRequest.getId());
		
		return optional
				.map(reply -> {
					reply
					//.setWriter(replyRequest.getWriter())
					.setContent(replyRequest.getContent());
					return reply;
				})
				.map(reply -> baseRepo.save(reply))
				.map(reply -> response(reply))
				.map(reply -> Header.OK(reply))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		Optional<ReviewBoardReply> optional = baseRepo.findById(id);
		
		return optional
				.map(reply -> {
					baseRepo.delete(reply);
					return Header.OK();
					})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<ReviewBoardReplyResponse> selectRead(Long id) {
		Optional<ReviewBoardReply> optioanl = baseRepo.findById(id);
	
		return optioanl
				.map(reply -> response(reply))
				.map(reply -> Header.OK(reply))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header<List<ReviewBoardReplyResponse>> allRead() {
		List<ReviewBoardReply> replyList = baseRepo.findAll();
		List<ReviewBoardReplyResponse> replyResList = replyList.stream()
				.map(reply -> response(reply))
				.collect(Collectors.toList());
		
		return Header.OK(replyResList);
	}

	@Override
	public Header<List<ReviewBoardReplyResponse>> pagingRead(Pageable pageable) {
		Page<ReviewBoardReply> page = baseRepo.findAll(pageable);
		
		List<ReviewBoardReplyResponse> replyResList = page.stream()
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
	
	//응답메소드
	public ReviewBoardReplyResponse response(ReviewBoardReply reply) {
		ReviewBoardReplyResponse res = ReviewBoardReplyResponse.builder()
				.id(reply.getId())
				.writer(reply.getWriter())
				.content(reply.getContent())
				.reviewBoardId(reply.getReviewBoard().getId())
				.build();

		return res;
	}
	
	
}
