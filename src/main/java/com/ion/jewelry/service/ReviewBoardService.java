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

import com.ion.jewelry.component.ReviewFileHandler;
import com.ion.jewelry.model.entity.ReviewBoard;
import com.ion.jewelry.model.entity.ReviewBoardReply;
import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.ReviewBoardReplyInfoResponse;
import com.ion.jewelry.model.network.request.ReviewBoardRequest;
import com.ion.jewelry.model.network.response.ReviewBoardReplyResponse;
import com.ion.jewelry.model.network.response.ReviewBoardResponse;
import com.ion.jewelry.repository.ItemRepository;

@Service
public class ReviewBoardService extends 
	AABaseService<ReviewBoardRequest, ReviewBoardResponse, ReviewBoard> {

	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private ReviewBoardReplyService replyService;
	
	private final ReviewFileHandler fileHandler = new ReviewFileHandler();
	
	@Override
	public Header<ReviewBoardResponse> create(Header<ReviewBoardRequest> request) {
		ReviewBoardRequest reviewRequest = request.getData();
		
		ReviewBoard reviewBoard = ReviewBoard.builder()
				.title(reviewRequest.getTitle())
				.content(reviewRequest.getContent())
				.writer(reviewRequest.getWriter())
				.password(reviewRequest.getPassword())
				.privateOk(reviewRequest.getPrivateOk())
				.originFileName(reviewRequest.getOriginFileName())
				.storedFileName(reviewRequest.getStoredFileName())
				.fileSize(reviewRequest.getFileSize())
				.deleteCheck(reviewRequest.getDeleteCheck())
				.item(itemRepo.getOne(reviewRequest.getItemId()))
				.build();
				
		ReviewBoard newReviewBoard = baseRepo.save(reviewBoard);
		ReviewBoardResponse reviewResponse = response(newReviewBoard); 
		
		return Header.OK(reviewResponse);
	}
	
	@Transactional
    public Header<ReviewBoardResponse> createImg(Header<ReviewBoardRequest> request, List<MultipartFile> files) throws Exception {
		ReviewBoardRequest requestDto = request.getData();
		
		ReviewBoard board = fileHandler.parseFileInfo(requestDto, files);
		board.setItem(itemRepo.getOne(requestDto.getItemId()));
		ReviewBoard newReviewBoard = baseRepo.save(board);
        
        return Header.OK(response(newReviewBoard));
    }
	
	@Transactional
    public Header<ReviewBoardResponse> updateImg(Header<ReviewBoardRequest> request, List<MultipartFile> files) throws Exception {
		ReviewBoardRequest reviewRequest = request.getData();
		Optional<ReviewBoard> optional = baseRepo.findById(reviewRequest.getId());
		
		ReviewBoard reviewBoard = fileHandler.parseFileInfo(reviewRequest, files);
        
        return optional
    			.map(board -> {
    				System.out.println("!!!" + reviewRequest.getDeleteCheck());
    				if(reviewRequest.getDeleteCheck() == YesNo.YES) {
    					String path = board.getStoredFileName();
    					
    					File file = new File(new File("").getAbsolutePath() + File.separator + "front\\vue-frontend\\" + File.separator + path);
    					System.out.println("!!!" + path);
    					if (file.exists()) {
    						if (file.delete()) {
    							System.out.println("파일삭제 성공");
    							reviewRequest.setOriginFileName(null);
    							reviewRequest.setStoredFileName(null);
    							reviewRequest.setFileSize(null);
    							
    							board
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
    				board
    					.setTitle(reviewRequest.getTitle())
    					.setContent(reviewRequest.getContent())
    					.setWriter(reviewRequest.getWriter())
    					.setPassword(reviewRequest.getPassword())
    					.setPrivateOk(reviewRequest.getPrivateOk())
    					.setOriginFileName(reviewBoard.getOriginFileName())
    					.setStoredFileName(reviewBoard.getStoredFileName())
    					.setFileSize(reviewBoard.getFileSize());
    			return board;
    			})
    			.map(board -> baseRepo.save(board))
    			.map(board -> response(board))
    			.map(board -> Header.OK(board))
    			.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
    }

	@Override
	public Header<ReviewBoardResponse> update(Header<ReviewBoardRequest> request) {
		ReviewBoardRequest reviewRequest = request.getData();
		Optional<ReviewBoard> optional = baseRepo.findById(reviewRequest.getId());
		
		return optional
				.map(review -> {
					if(reviewRequest.getDeleteCheck() == YesNo.YES) {
						System.out.println(review.getStoredFileName());
						String path = review.getStoredFileName();
						File file = new File(new File("").getAbsolutePath() + File.separator + "front\\vue-frontend\\" + File.separator + path);
						
						if (file.exists()) {
							if (file.delete()) {
								System.out.println("파일삭제 성공");
								reviewRequest.setOriginFileName(null);
								reviewRequest.setStoredFileName(null);
								reviewRequest.setFileSize(null);
								
								review
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
					review
					.setTitle(reviewRequest.getTitle())
					.setContent(reviewRequest.getContent())
					.setWriter(reviewRequest.getWriter())
					.setPassword(reviewRequest.getPassword())
					.setPrivateOk(reviewRequest.getPrivateOk())
					.setItem(itemRepo.getOne(reviewRequest.getItemId()));
					
					return review;
				})
				.map(review -> baseRepo.save(review))
				.map(review -> response(review))
				.map(review -> Header.OK(review))
				.orElseGet(() -> Header.ERROR("없데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		Optional<ReviewBoard> optional = baseRepo.findById(id);
		
		return optional
				.map(review -> {
					String path = review.getStoredFileName();
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
					
					baseRepo.delete(review);
					return Header.OK();
					})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<ReviewBoardResponse> selectRead(Long id) {
		Optional<ReviewBoard> optioanl = baseRepo.findById(id);
		
		return optioanl
				.map(review -> response(review))
				.map(review -> Header.OK(review))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 업습니다."))
				;
	}

	@Override
	public Header<List<ReviewBoardResponse>> allRead() {
		List<ReviewBoard> reviewList = baseRepo.findAll();
		
		List<ReviewBoardResponse> reviewResList = reviewList.stream()
				.map(review -> response(review))
				.collect(Collectors.toList());
		
		return Header.OK(reviewResList);
	}

	@Override
	public Header<List<ReviewBoardResponse>> pagingRead(Pageable pageable) {
		Page<ReviewBoard> page = baseRepo.findAll(pageable);
		
		List<ReviewBoardResponse> reviewResList = page.stream()
				.map(review -> response(review))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(reviewResList, pagination);
	}
	
	//특정리뷰에 해당하는 댓글들 조회
	public Header<ReviewBoardReplyInfoResponse> replyInfo(Long id){
		ReviewBoard reviewBoard = baseRepo.getOne(id);
		ReviewBoardResponse reviewBoardResponse = response(reviewBoard);
		
		List<ReviewBoardReply> reviewBoardList = reviewBoard.getReviewBoardReplyList();
		List<ReviewBoardReplyResponse> reviewBoardResponseList = reviewBoardList.stream()
				.map(reply -> replyService.response(reply))
				.sorted((a, b) -> (int)(b.id - a.id))
				.collect(Collectors.toList());
		
		reviewBoardResponse.setReviewBoardReplyResponseList(reviewBoardResponseList);
		ReviewBoardReplyInfoResponse reviewBoardReplyInfoResponse = 
				ReviewBoardReplyInfoResponse.builder()
				.reviewBoardResponse(reviewBoardResponse).build();
		
		return Header.OK(reviewBoardReplyInfoResponse);
	}
	
	public ReviewBoardResponse response(ReviewBoard board) {
		ReviewBoardResponse res = ReviewBoardResponse.builder()
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
