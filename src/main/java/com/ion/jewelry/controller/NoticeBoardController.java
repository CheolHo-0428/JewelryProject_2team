package com.ion.jewelry.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ion.jewelry.model.entity.NoticeBoard;
import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.NoticeBoardReplyRequest;
import com.ion.jewelry.model.network.request.NoticeBoardRequest;
import com.ion.jewelry.model.network.response.NoticeBoardReplyInfoResponse;
import com.ion.jewelry.model.network.response.NoticeBoardReplyResponse;
import com.ion.jewelry.model.network.response.NoticeBoardResponse;
import com.ion.jewelry.service.NoticeBoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/noticeBoard")
@CrossOrigin("http://localhost:8080/")
public class NoticeBoardController extends AABaseController<NoticeBoardRequest, NoticeBoardResponse, NoticeBoard> {

	@Autowired
	private NoticeBoardService boardService;

	@Override
	@GetMapping("/paging") // http://localhost:8000//jewelry/noticeBoard/paging?page=0
	public Header<List<NoticeBoardResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

		log.info("{}", pageable);
		return baseService.pagingRead(pageable);
	}

	// 공지사항 번호별 댓글 조회
	@GetMapping("/{id}/replyInfo") // http://localhost:8000/jewelry/noticeBoard/1/replyInfo
	public Header<NoticeBoardReplyInfoResponse> replyInfo(@PathVariable Long id) {
		return boardService.replyInfo(id);
	}
	@PostMapping("/regImg")
	public Header<NoticeBoardResponse> create(
			@Valid @RequestParam("title") String title,
            @Valid @RequestParam("content") String content,
            @Valid @RequestParam("writer") String writer,
            @Valid @RequestParam("file") List<MultipartFile> files
			) throws Exception {
		Header<NoticeBoardRequest> result = new Header<NoticeBoardRequest>();
		NoticeBoardRequest request = NoticeBoardRequest.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.build();
		result.setData(request);	
		return boardService.createImg(result, files);
	}
	@PostMapping("/reg")
	public Header<NoticeBoardResponse> create(@RequestBody NoticeBoardRequest request) {
		System.out.println("등록진입");
		Header<NoticeBoardRequest> result = new Header<NoticeBoardRequest>();
		result.setData(request);
		
		return baseService.create(result);
	}

	@Override
	@DeleteMapping("{id}")
	public Header delete(@PathVariable Long id) {
		return baseService.delete(id);
	}
	@PutMapping("/updateImg")
	public Header<NoticeBoardResponse> update(
			@Valid @RequestParam("title") String title,
            @Valid @RequestParam("content") String content,
            @Valid @RequestParam("writer") String writer,
            @Valid @RequestParam("delete_check") YesNo delete_check,
            @Valid @RequestParam("id") Long id,
            @Valid @RequestParam("file") List<MultipartFile> files
			) throws Exception {
		Header<NoticeBoardRequest> result = new Header<NoticeBoardRequest>();
		NoticeBoardRequest request = NoticeBoardRequest.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.id(id)
				.deleteCheck(delete_check)
				.build();
		result.setData(request);	
		return boardService.updateImg(result, files);
	}	
	@PutMapping("/update")
	public Header<NoticeBoardResponse> update(@RequestBody NoticeBoardRequest request) {

		Header<NoticeBoardRequest> result = new Header<NoticeBoardRequest>();
		result.setData(request);

		return baseService.update(result);
	}

}