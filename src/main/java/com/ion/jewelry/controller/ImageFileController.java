package com.ion.jewelry.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public Header<ImageFileResponse> create(
			@Valid @RequestParam("delegateThumbnail") String yesNo,
			@Valid @RequestParam("itemId") String itemId,
			@Valid @RequestParam("file") List<MultipartFile> files
			) throws Exception {
		Header<ImageFileRequest> result = new Header<ImageFileRequest>();
		ImageFileRequest request = new ImageFileRequest();
		
		request.setDelegateThumbnail(yesNo.equals("YES") ? YesNo.YES : YesNo.NO);
		request.setItemId(Long.parseLong(itemId));
		
		result.setData(request);
		return imageFileService.createImg(result, files);
	}
	
	@PutMapping("/updateImg")
	public Header<ImageFileResponse> update(
			@Valid @RequestParam("id") Long id,
			@Valid @RequestParam("delegateThumbnail") YesNo ThumYesNo,
			@Valid @RequestParam("delete_check") YesNo dcYesNo,
			@Valid @RequestParam("itemId") Long itemId,
			@Valid @RequestParam("file") List<MultipartFile> files
			) throws Exception {
		Header<ImageFileRequest> result = new Header<ImageFileRequest>();
		ImageFileRequest request = ImageFileRequest.builder()
				.id(id)
				.delegateThumbnail(ThumYesNo)
				.delegateThumbnail(dcYesNo)
				.itemId(itemId)
				.build();
		result.setData(request);
		return imageFileService.updateImg(result, files);		
	}
	
	
}
