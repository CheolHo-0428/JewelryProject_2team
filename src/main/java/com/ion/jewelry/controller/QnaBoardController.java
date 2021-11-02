package com.ion.jewelry.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ion.jewelry.model.entity.QnaBoard;
import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.QnaBoardRequest;
import com.ion.jewelry.model.network.response.QnaBoardReplyInfoResponse;
import com.ion.jewelry.model.network.response.QnaBoardResponse;
import com.ion.jewelry.service.QnaBoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/qnaBoard")
@CrossOrigin("http://localhost:8080/")
public class QnaBoardController extends AABaseController<QnaBoardRequest, QnaBoardResponse, QnaBoard> {
	
	@Autowired
	private QnaBoardService qnaService;
	
	@Override
	@GetMapping("/paging") // http://localhost:8000/jewelry/qnaBoard/paging?page=0
	public Header<List<QnaBoardResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		log.info("페이징 정보: {}", pageable);
		return baseService.pagingRead(pageable);
	}
	
	@GetMapping("/{id}/replyInfo") // http://localhost:8000/jewelry/qnaBoard/1/replyInfo
	public Header<QnaBoardReplyInfoResponse> replyInfo(@PathVariable Long id){
		log.info("read id : {}", id);
		return qnaService.replyInfo(id);
	}
	
	@PostMapping("/reg")
	public Header<QnaBoardResponse> create(@RequestBody QnaBoardRequest request) {
		
		Header<QnaBoardRequest> result = new Header<QnaBoardRequest>();
		result.setData(request);
		
		return baseService.create(result);
	}

	@PostMapping("/regImg")
	public Header<QnaBoardResponse> create(
			@Valid @RequestParam("title") String title,
            @Valid @RequestParam("content") String content,
            @Valid @RequestParam("writer") String writer,
            @Valid @RequestParam("item") Long itemId,
            @Valid @RequestParam("password") String password,
            @Valid @RequestParam("file") List<MultipartFile> files
			) throws Exception {
		Header<QnaBoardRequest> result = new Header<QnaBoardRequest>();
		QnaBoardRequest request = QnaBoardRequest.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.itemId(itemId)
				.password(password)
				.build();
		result.setData(request);
		return qnaService.createImg(result, files);
	}
	
	@PutMapping("/updateImg")
	public Header<QnaBoardResponse> updateImg(
			@Valid @RequestParam("title") String title,
            @Valid @RequestParam("content") String content,
            @Valid @RequestParam("writer") String writer,
            @Valid @RequestParam("delete_check") YesNo delete_check,
            @Valid @RequestParam("id") Long id,
            @Valid @RequestParam("item_id") Long itemId,
            @Valid @RequestParam("password") String password,
            @Valid @RequestParam("file") List<MultipartFile> files
			) throws Exception {
		Header<QnaBoardRequest> result = new Header<QnaBoardRequest>();
		QnaBoardRequest request = QnaBoardRequest.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.id(id)
				.deleteCheck(delete_check)
				.itemId(itemId)
				.password(password)
				.build();
		result.setData(request);	
		return qnaService.updateImg(result, files);
	}
	@PutMapping("/update")
	public Header<QnaBoardResponse> update(@RequestBody QnaBoardRequest request) {

		Header<QnaBoardRequest> result = new Header<QnaBoardRequest>();
		result.setData(request);

		return baseService.update(result);
	}
}
