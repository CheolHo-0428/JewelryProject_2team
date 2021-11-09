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
	/*
	@PutMapping("/updateImg")
	public Header<ImageFileResponse> update(
			@Valid @RequestParam("id") List<String> idList,
			@Valid @RequestParam("delegateThumbnail") List<String> tYesNoList,
			@Valid @RequestParam("deleteCheck") List<String> dYesNoList,
			@Valid @RequestParam("itemId") String itemId,
			@Valid @RequestParam("file") List<MultipartFile> files
			) throws Exception {
		System.out.println("ID 리스트=================>" + idList);
		System.out.println("썸네일 리스트=================>" + tYesNoList);
		System.out.println("삭제 리스트=================>" + dYesNoList);
		System.out.println("아이템 아이디=================>" + itemId);
		for (int i = 0; i < files.size(); i++) {
			System.out.println("파일 이름" + i + "=================>" + files.get(i).getOriginalFilename());
		}
		
		Header<ImageFileRequest> result = new Header<ImageFileRequest>();
		List<Long> numIdList = new ArrayList<Long>();
		List<YesNo> tList = new ArrayList<YesNo>();
		List<YesNo> dList = new ArrayList<YesNo>();
		
		for (int i = 0; i < files.size(); i++) {
			numIdList.add(Long.parseLong(idList.get(i)));
			tList.add(tYesNoList.get(i).equals("YES") ? YesNo.YES : YesNo.NO);
			tList.add(dYesNoList.get(i).equals("YES") ? YesNo.YES : YesNo.NO);
		}
		ImageFileRequest request = new ImageFileRequest()
				.setIdList(numIdList)
				.setDelegateThumbnailList(tList)
				.setDeleteCheckList(dList)
				.setItemId(Long.parseLong(itemId));
		
		result.setData(request);
		return imageFileService.updateImg(result, files);		
	}
	*/
	
}
