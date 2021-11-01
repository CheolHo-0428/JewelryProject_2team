package com.ion.jewelry.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ion.jewelry.component.QnaFileHandler;
import com.ion.jewelry.model.entity.QnaBoard;
import com.ion.jewelry.model.entity.QnaBoardReply;
import com.ion.jewelry.model.entity.ReviewBoard;
import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.QnaBoardRequest;
import com.ion.jewelry.model.network.request.ReviewBoardRequest;
import com.ion.jewelry.model.network.response.QnaBoardReplyInfoResponse;
import com.ion.jewelry.model.network.response.QnaBoardReplyResponse;
import com.ion.jewelry.model.network.response.QnaBoardResponse;
import com.ion.jewelry.model.network.response.ReviewBoardResponse;
import com.ion.jewelry.repository.ItemRepository;

@Service
public class QnaBoardService extends AABaseService<QnaBoardRequest, QnaBoardResponse, QnaBoard> {
	
	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private QnaBoardReplyService replyService;
	
	private final QnaFileHandler fileHandler = new QnaFileHandler();
	
	@Override
	public Header<QnaBoardResponse> create(Header<QnaBoardRequest> request) {
		//1. 생성할 데이터를 요청
		QnaBoardRequest qnaRequest =  request.getData();
		
		//2. 요청한 데이터 -> DB에 저장
		QnaBoard qna = QnaBoard.builder()
				.title(qnaRequest.getTitle())
				.content(qnaRequest.getContent())
				.writer(qnaRequest.getWriter())
				.password(qnaRequest.getPassword())
				.privateOk(qnaRequest.getPrivateOk())
				.originFileName(qnaRequest.getOriginFileName())
				.storedFileName(qnaRequest.getStoredFileName())
				.fileSize(qnaRequest.getFileSize())
				.deleteCheck(qnaRequest.getDeleteCheck())
				.item(itemRepo.getOne(qnaRequest.getItemId()))
				.build();
		QnaBoard newQna = baseRepo.save(qna);
		
		//3. 저장된 DB데이터 응답
		QnaBoardResponse res = response(newQna);
		return Header.OK(res);
	}
	
	@Transactional
    public Header<QnaBoardResponse> createImg(Header<QnaBoardRequest> request, List<MultipartFile> files) throws Exception {
		QnaBoardRequest requestDto = request.getData();
		 
		QnaBoard board = fileHandler.parseFileInfo(requestDto, files);
		board.setItem(itemRepo.getOne(requestDto.getItemId()));
		QnaBoard newReviewBoard = baseRepo.save(board);
        
        return Header.OK(response(newReviewBoard));
    }

	@Override
	public Header<QnaBoardResponse> update(Header<QnaBoardRequest> request) {
		//1. 업데이트할 데이터를 요청
		QnaBoardRequest qnaRequest = request.getData();
		
		//2. 업데이트할 데이터 검색
		Optional<QnaBoard> optional = baseRepo.findById(qnaRequest.getId());
		
		//3. 업데이트한 데이터 -> DB저장 -> 업데이트한 데이터 응답 
		return optional
				.map(qnaBoard -> {
					if(qnaRequest.getDeleteCheck() == YesNo.YES) {
						System.out.println(qnaBoard.getStoredFileName());
						String path = qnaBoard.getStoredFileName();
						File file = new File(new File("").getAbsolutePath() + File.separator + "front\\vue-frontend\\" + File.separator + path);
						
						if (file.exists()) {
							if (file.delete()) {
								System.out.println("파일삭제 성공");
								qnaRequest.setOriginFileName(null);
								qnaRequest.setStoredFileName(null);
								qnaRequest.setFileSize(null);
								
								qnaBoard
									.setOriginFileName(null)
									.setStoredFileName(null)
									.setFileSize(null)
									.setDeleteCheck(YesNo.YES);
							} else {
								System.out.println("파일삭제 실패");
							}
						} else {
							System.out.println("파일이 존재하지 않습니다.");
						}
					}
					qnaBoard
					.setTitle(qnaRequest.getTitle())
					.setContent(qnaRequest.getContent())
					.setWriter(qnaRequest.getWriter())
					.setPassword(qnaRequest.getPassword())
					.setPrivateOk(qnaRequest.getPrivateOk())
					.setItem(itemRepo.getOne(qnaRequest.getItemId()));
					
					return qnaBoard;
				})
				.map(qnaBoard -> baseRepo.save(qnaBoard))
				.map(qnaBoard -> response(qnaBoard))
				.map(qnaBoard -> Header.OK(qnaBoard))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		// 1. 삭제할 데이터를 요청
		Optional<QnaBoard> optional = baseRepo.findById(id);
		
		// 2. 데이터 삭제 -> 삭제완료 메세지 응답
		return optional
				.map(qnaBoard -> {
					String path = qnaBoard.getStoredFileName();
					File file = new File(new File("").getAbsolutePath() + File.separator + "front\\vue-frontend\\" + File.separator + path);
					
					if (file.exists()) {
						if (file.delete()) {
							System.out.println("파일삭제 성공");
						} else {
							System.out.println("파일삭제 실패");
						}
					} else {
						System.out.println("파일이 존재하지 않습니다.");
					}
					
					baseRepo.delete(qnaBoard);
					return Header.OK();
					})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<QnaBoardResponse> selectRead(Long id) {
		// 1. 조회할 데이터 요청
		Optional<QnaBoard> optional = baseRepo.findById(id);
		
		// 2. 조회한 데이터 응답
		return optional
				.map(qnaBoard -> response(qnaBoard))
				.map(qnaBoard -> Header.OK(qnaBoard))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."));
	}

	@Override
	public Header<List<QnaBoardResponse>> allRead() {
		// 1. 조회할 전체 데이터 요청
		List<QnaBoard> qnaBoardList = baseRepo.findAll();
		
		// 2. 조회한 데이터 응답
		List<QnaBoardResponse> qnaResList = qnaBoardList.stream()
				.map(qnaBoard -> response(qnaBoard))
				.collect(Collectors.toList());
		
		return Header.OK(qnaResList);
	}

	@Override
	public Header<List<QnaBoardResponse>> pagingRead(Pageable pageable) {
		// 1. 조회할 전체 데이터 요청(페이지 정보 포함)
		Page<QnaBoard> page = baseRepo.findAll(pageable);
		
		// 2. 조회한 데이터 응답(페이지 정보 포함)
		List<QnaBoardResponse> qnaResList = page.stream()
				.map(qnaBoard -> response(qnaBoard))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(qnaResList, pagination);
	}
	
	public Header<QnaBoardReplyInfoResponse> replyInfo(Long id){
		QnaBoard qnaBoard = baseRepo.getOne(id);
		QnaBoardResponse qnaBoardResponse = response(qnaBoard);
		
		List<QnaBoardReply> replyList = qnaBoard.getQnaBoardReplyList(); 
		List<QnaBoardReplyResponse> replyResList = replyList.stream()
				.map(reply -> {
					QnaBoardReplyResponse replyRes = replyService.response(reply); 
					return replyRes;
				})
				.collect(Collectors.toList());
		
		qnaBoardResponse.setQnaBoardReplyResponseList(replyResList);
		QnaBoardReplyInfoResponse qnaBoardReplyInfoResponse = QnaBoardReplyInfoResponse.builder()
				.qnaBoardResponse(qnaBoardResponse).build();
		
		return Header.OK(qnaBoardReplyInfoResponse);
	}
	
	
	// 응답 메소드
	public QnaBoardResponse response(QnaBoard board) {
		QnaBoardResponse res = QnaBoardResponse.builder()
				.id(board.getId())
				.title(board.getTitle())
				.content(board.getContent())
				.writer(board.getWriter())
				.password(board.getPassword())
				.privateOk(board.getPrivateOk())
				.originFileName(board.getOriginFileName())
				.storedFileName(board.getStoredFileName())
				.fileSize(board.getFileSize())
				.deleteCheck(board.getDeleteCheck())
				.createdAt(board.getCreatedAt())
				.itemId(board.getItem().getId())
				.build();
				
		return res;
	}
	
}
