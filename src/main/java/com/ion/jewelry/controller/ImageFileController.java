package com.ion.jewelry.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ion.jewelry.model.entity.ImageFile;
import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.request.ImageFileRequest;
import com.ion.jewelry.model.network.response.ImageFileResponse;
import com.ion.jewelry.service.ImageFileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jewelry/imageFile")
@CrossOrigin("http://localhost:8080/")
public class ImageFileController extends 
	AABaseController<ImageFileRequest, ImageFileResponse, ImageFile> {
	
	@Autowired
	private ImageFileService imageFileService;
	
	@Override
	@GetMapping("/paging") // http://localhost:8000/jewelry/imageFile/paging?page=0
	public Header<List<ImageFileResponse>> pagingRead(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
			Pageable pageable) {
		
		log.info("페이징 데이터: {}", pageable);
		return baseService.pagingRead(pageable);
	}

	@PostMapping("/regImg") //http://localhost:8000/jewelry/imageFile/reImg?files=""
	public Header<List<ImageFileResponse>> create(
			@Valid @RequestParam("delegateThumbnail") List<String> yesNo,
			@Valid @RequestParam("itemId") String itemId,
			@Valid @RequestParam("file") List<MultipartFile> files
			) throws Exception {
		Header<ImageFileRequest> result = new Header<ImageFileRequest>();
		ImageFileRequest request = new ImageFileRequest();
		
		System.out.println("*************************" + yesNo);
		
		List<YesNo> yesNoList = yesNo.stream()
				.map(yn -> 
					yn.equals("YES") ? YesNo.YES : YesNo.NO
				)
				.collect(Collectors.toList());
		
		request.setDelegateThumbnailList(yesNoList);
		request.setItemId(Long.parseLong(itemId));
		
		result.setData(request);
				
		return imageFileService.createImg(result, files);
	}
	
	@PutMapping("/updateImg")
	public Header<ImageFileResponse> update(
			@Valid @RequestParam("id") String id,
			@Valid @RequestParam("delegateThumbnail") String delegateThumbnail,
			@Valid @RequestParam("deleteCheck") String deleteCheck,
			@Valid @RequestParam("itemId") String itemId,
			@Valid @RequestParam("file") List<MultipartFile> files
			) throws Exception {
		System.out.println("ID 리스트===================>" + Long.parseLong(id));
		System.out.println("썸네일 리스트=================>" + delegateThumbnail);
		System.out.println("삭제리스트===================>" + deleteCheck);
		System.out.println("아이템 아이디=================>" + Long.parseLong(itemId));
		System.out.println("파일 이름====================>" + files.get(0).getOriginalFilename());

		Header<ImageFileRequest> result = new Header<ImageFileRequest>();

		ImageFileRequest request = new ImageFileRequest()
				.setId(Long.parseLong(id))
				.setDelegateThumbnail(delegateThumbnail.equals("YES") ? YesNo.YES : YesNo.NO)
				.setDeleteCheck(deleteCheck.equals("YES") ? YesNo.YES : YesNo.NO)
				.setItemId(Long.parseLong(itemId));
		
		result.setData(request);
		return imageFileService.updateImg(result, files);		
	}
	
	@PutMapping("/update")
	public Header<ImageFileResponse> update(
			@Valid @RequestParam("id") String id,
			@Valid @RequestParam("delegateThumbnail") String delegateThumbnail,
			@Valid @RequestParam("deleteCheck") String deleteCheck,
			@Valid @RequestParam("itemId") String itemId
			) throws Exception {

		Header<ImageFileRequest> result = new Header<ImageFileRequest>();
		
		ImageFileRequest request = new ImageFileRequest()
				.setId(Long.parseLong(id))
				.setDelegateThumbnail(delegateThumbnail.equals("YES") ? YesNo.YES : YesNo.NO)
				.setDeleteCheck(deleteCheck.equals("YES") ? YesNo.YES : YesNo.NO)
				.setItemId(Long.parseLong(itemId));
		result.setData(request);
		
		return baseService.update(result);
	}
}
