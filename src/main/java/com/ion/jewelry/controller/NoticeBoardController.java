package com.ion.jewelry.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
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
import com.ion.jewelry.model.network.request.NoticeBoardRequest;
import com.ion.jewelry.model.network.response.NoticeBoardReplyInfoResponse;
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
	
	@GetMapping("/searchTitle") // http://localhost:8000/jewelry/noticeBoard/searchTitle?keyword=test&page=0
	public Header<List<NoticeBoardResponse>> searchTitle(@PathParam("keyword")String keyword, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		return boardService.searchTitle(keyword, pageable);
	}
	@GetMapping("/searchWriter")
	public Header<List<NoticeBoardResponse>> searchWriter(@PathParam("keyword")String keyword, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		return boardService.searchWriter(keyword, pageable);
	}

	// ???????????? ????????? ?????? ??????
	@GetMapping("/{id}/replyInfo") // http://localhost:8000/jewelry/noticeBoard/1/replyInfo
	public Header<NoticeBoardReplyInfoResponse> replyInfo(@PathVariable Long id) {
		return boardService.replyInfo(id);
	}

	@PreAuthorize("hasRole('ADMIN')")
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

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/reg")
	public Header<NoticeBoardResponse> create(@RequestBody NoticeBoardRequest request) {
		Header<NoticeBoardRequest> result = new Header<NoticeBoardRequest>();
		result.setData(request);
		
		return baseService.create(result);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public Header delete(@PathVariable Long id) {
		return baseService.delete(id);
	}

	@PreAuthorize("hasRole('ADMIN')")
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

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update")
	public Header<NoticeBoardResponse> update(@RequestBody NoticeBoardRequest request) {

		Header<NoticeBoardRequest> result = new Header<NoticeBoardRequest>();
		result.setData(request);

		return baseService.update(result);
	}

}