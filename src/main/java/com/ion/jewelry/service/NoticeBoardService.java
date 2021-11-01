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

import com.ion.jewelry.component.FileHandler;
import com.ion.jewelry.model.entity.NoticeBoard;
import com.ion.jewelry.model.entity.NoticeBoardReply;
import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.NoticeBoardRequest;
import com.ion.jewelry.model.network.response.NoticeBoardReplyInfoResponse;
import com.ion.jewelry.model.network.response.NoticeBoardReplyResponse;
import com.ion.jewelry.model.network.response.NoticeBoardResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeBoardService extends 
	AABaseService<NoticeBoardRequest, NoticeBoardResponse, NoticeBoard> {
	
	//기본 Repository는 baseRepo!!!!
	
	@Autowired
	private NoticeBoardReplyService replyService;
	
	private final FileHandler fileHandler;
	
	@Override
	public Header<NoticeBoardResponse> create(Header<NoticeBoardRequest> request) {
		NoticeBoardRequest noticeRequest = request.getData();
		
		NoticeBoard board = NoticeBoard.builder()
					.title(noticeRequest.getTitle())
					.content(noticeRequest.getContent())
					.writer(noticeRequest.getWriter())
					.password(noticeRequest.getPassword())
					.privateOk(noticeRequest.getPrivateOk())
					.originFileName(noticeRequest.getOriginFileName())
					.storedFileName(noticeRequest.getStoredFileName())
					.fileSize(noticeRequest.getFileSize())
					.deleteCheck(noticeRequest.getDeleteCheck())
					.build();
		
		NoticeBoard newNoticeBoard = baseRepo.save(board);
		
		return Header.OK(response(newNoticeBoard));
	}
	
	@Transactional
    public Header<NoticeBoardResponse> createImg(Header<NoticeBoardRequest> request, List<MultipartFile> files) throws Exception {
		NoticeBoardRequest requestDto = request.getData();
		
        NoticeBoard board = fileHandler.parseFileInfo(requestDto, files);
        NoticeBoard newNoticeBoard = baseRepo.save(board);
        
        return Header.OK(response(newNoticeBoard));
    }

	@Override
	public Header<NoticeBoardResponse> update(Header<NoticeBoardRequest> request) {
		NoticeBoardRequest noticeRequest = request.getData();
		
		Optional<NoticeBoard> optional = baseRepo.findById(noticeRequest.getId());
		
		return optional
			.map(board -> {
				if(noticeRequest.getDeleteCheck() == YesNo.YES) {
					System.out.println(noticeRequest.getDeleteCheck());
					String path = board.getStoredFileName();
					File file = new File(new File("").getAbsolutePath() + File.separator + "front\\vue-frontend\\" + File.separator + path);
					
					if (file.exists()) {
						if (file.delete()) {
							System.out.println("파일삭제 성공");
							noticeRequest.setOriginFileName(null);
							noticeRequest.setStoredFileName(null);
							noticeRequest.setFileSize(null);
							
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
					.setTitle(noticeRequest.getTitle())
					.setContent(noticeRequest.getContent())
					.setWriter(noticeRequest.getWriter())
					.setPassword(noticeRequest.getPassword())
					.setPrivateOk(noticeRequest.getPrivateOk());
			return board;
			})
			.map(board -> baseRepo.save(board))
			.map(board -> response(board))
			.map(board -> Header.OK(board))
			.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		Optional<NoticeBoard> optional = baseRepo.findById(id);
		
		return optional
				.map(board -> {
					String path = board.getStoredFileName();
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
					
					baseRepo.delete(board);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("삭제할 데이터가 없습니다."));
	}

	@Override
	public Header<NoticeBoardResponse> selectRead(Long id) {
		Optional<NoticeBoard> optional = baseRepo.findById(id);
		
		return optional
					.map(board -> response(board))
					.map(board -> Header.OK(board))
					.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."));
	}

	@Override
	public Header<List<NoticeBoardResponse>> allRead() {
		List<NoticeBoard> boardList = baseRepo.findAll();
		
		List<NoticeBoardResponse> resBoardList = boardList.stream()
					.map(board -> response(board))
					.sorted((a, b) -> (int)(b.id - a.id))
					.collect(Collectors.toList());
		
		return Header.OK(resBoardList);
	}
	
	@Override
	public Header<List<NoticeBoardResponse>> pagingRead(Pageable pageable) {
		Page<NoticeBoard> page = baseRepo.findAll(pageable);
		
		List<NoticeBoardResponse> boardResList = page.stream()
					.map(board -> response(board))
					.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(boardResList, pagination);
	}
	
	public Header<NoticeBoardReplyInfoResponse> replyInfo(Long id){
		// Board를 찾는다.
		NoticeBoard noticeBoard = baseRepo.getOne(id);
		NoticeBoardResponse noticeBoardResponse = response(noticeBoard);
		
		// 위에서 찾은 Board의 reply를 찾는다.
		List<NoticeBoardReply> replyList = noticeBoard.getNoticeBoardReplyList();
		List<NoticeBoardReplyResponse> replyResList = replyList.stream()
					.map(reply -> {
						NoticeBoardReplyResponse replyRes = 
								Header.OK(replyService.response(reply)).getData();
						return replyRes;
					})
					.sorted((a, b) -> (int)(b.id - a.id))
					.collect(Collectors.toList());
		
		noticeBoardResponse.setNoticeBoardReplyResponseList(replyResList);
		
		NoticeBoardReplyInfoResponse noticeBoardReplyInfoResponse
			= NoticeBoardReplyInfoResponse.builder()
				.noticeBoardResponse(noticeBoardResponse)
				.build();
				
		return Header.OK(noticeBoardReplyInfoResponse);
	}
	
	
	public NoticeBoardResponse response(NoticeBoard board) {

		
		NoticeBoardResponse res = NoticeBoardResponse.builder()
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
				.build();
		
		return res;
	}
	
}
