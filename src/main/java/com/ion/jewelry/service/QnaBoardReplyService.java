package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ion.jewelry.model.entity.QnaBoardReply;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.QnaBoardReplyRquest;
import com.ion.jewelry.model.network.response.QnaBoardReplyResponse;
import com.ion.jewelry.repository.QnaBoardRepository;

@Service
public class QnaBoardReplyService extends AABaseService<QnaBoardReplyRquest, QnaBoardReplyResponse, QnaBoardReply> {
	
	@Autowired
	private QnaBoardRepository qnaBoardRepo;
	
	@Override
	public Header<QnaBoardReplyResponse> create(Header<QnaBoardReplyRquest> request) {
		QnaBoardReplyRquest replyRquest = request.getData();
		
		QnaBoardReply qnaReply = QnaBoardReply.builder()
				.writer(replyRquest.getWriter())
				.content(replyRquest.getContent())
				.qnaBoard(qnaBoardRepo.getOne(replyRquest.getQnaBoardId()))
				.build();
		QnaBoardReply newQnaReply = baseRepo.save(qnaReply);
		
		QnaBoardReplyResponse res = response(newQnaReply);
		return Header.OK(res);
	}

	@Override
	public Header<QnaBoardReplyResponse> update(Header<QnaBoardReplyRquest> request) {
		QnaBoardReplyRquest replyRquest = request.getData();
		
		Optional<QnaBoardReply> optional = baseRepo.findById(replyRquest.getId());
		
		return optional
				.map(reply -> {
					reply
					//.setWriter(replyRquest.getWriter())
					.setContent(replyRquest.getContent());
					return reply;
				})
				.map(reply -> baseRepo.save(reply))
				.map(reply -> response(reply))
				.map(reply -> Header.OK(reply))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		Optional<QnaBoardReply> optional = baseRepo.findById(id);
		
		return optional
				.map(reply ->{
					baseRepo.delete(reply);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<QnaBoardReplyResponse> selectRead(Long id) {
		Optional<QnaBoardReply> optional = baseRepo.findById(id);
		
		return optional
				.map(reply -> response(reply))
				.map(reply -> Header.OK(reply))
				.orElseGet(() -> Header.ERROR("조회할 데이터가 없습니다."));
	}

	@Override
	public Header<List<QnaBoardReplyResponse>> allRead() {
		List<QnaBoardReply> replyList = baseRepo.findAll();
		
		List<QnaBoardReplyResponse> replyResList = replyList.stream()
				.map(reply -> response(reply))
				.collect(Collectors.toList());
		return Header.OK(replyResList);
	}

	@Override
	public Header<List<QnaBoardReplyResponse>> pagingRead(Pageable pageable) {
		Page<QnaBoardReply> page = baseRepo.findAll(pageable);
		
		List<QnaBoardReplyResponse> replyResList = page.stream()
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
	
	public QnaBoardReplyResponse response(QnaBoardReply reply) {
		QnaBoardReplyResponse res = QnaBoardReplyResponse.builder()
				.id(reply.getId())
				.writer(reply.getWriter())
				.content(reply.getContent())
				.qnaBoardId(reply.getQnaBoard().getId())
				.build();
		
		return res;
	}
	
	
}
