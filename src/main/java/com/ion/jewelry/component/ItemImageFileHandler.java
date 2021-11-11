package com.ion.jewelry.component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ion.jewelry.model.entity.ImageFile;
import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.request.ImageFileRequest;
import com.ion.jewelry.repository.ImageFileRepository;
import com.ion.jewelry.repository.ItemRepository;

@Component
public class ItemImageFileHandler {
	
	@Autowired
	private ItemRepository itemRepo;
		
	private ImageFile imageFile;
	
	public ImageFile parseFileInfo_single(
			ImageFileRequest requestDto, 
			List<MultipartFile> multipartFiles) throws Exception {
			
		if(!CollectionUtils.isEmpty(multipartFiles)) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateTimeFormatter = 
					DateTimeFormatter.ofPattern("yyyyMMdd");
			String current_date = now.format(dateTimeFormatter);
			
			String absolutePath = new File("").getAbsolutePath() + File.separator + "front\\vue-frontend\\" + File.separator;
			System.out.println(absolutePath);
			String path = "static\\item\\images" + File.separator + current_date;
			File file = new File(absolutePath + File.separator + path);
			
			// 디렉터리가 존재하지 않을 경우
			if(!file.exists()) {
				boolean wasSuccessful = file.mkdir();
				
				// 디렉터리 생성에 실패했을 경우
				if(!wasSuccessful)
					System.out.println("file: was not successful");
			}
			
			for(int i = 0; i < multipartFiles.size(); i++) {
				MultipartFile multipartFile = multipartFiles.get(i);
				
				String originalFileExtension;
				String contentType = multipartFile.getContentType();
				
				// 확장자명이 존재하지 않을 경우 처리 x
				if(ObjectUtils.isEmpty(contentType)) {
					break;
				}
				// 확장자가 jpeg, png인 파일들만 받아서 처리
				else {
					if(contentType.contains("image/jpeg")) {
						originalFileExtension = ".jpg";
					} else if(contentType.contains("image/png")) {
						originalFileExtension = ".png";
					} else { //다른 확장자일 경우 처리 x
						break;
					}
				}
				// 파일명 중복 피하고자 나노초까지 얻어와 지정
				String new_file_name = System.nanoTime() + originalFileExtension;
				
				imageFile = ImageFile.builder()
						.originFileName(multipartFile.getOriginalFilename())
						.storedFileName(path + File.separator + new_file_name)
						.storedThumbnail(path + File.separator + new_file_name)
						.delegateThumbnail(requestDto.getDelegateThumbnail())
						.fileSize(multipartFile.getSize())
						.deleteCheck(YesNo.NO)
						.item(itemRepo.getOne(requestDto.getItemId()))
						.build();
				
				
								
				file = new File(absolutePath + path + File.separator + new_file_name);
				multipartFile.transferTo(file);
			}
		}
		
		return imageFile;
	}
	
	public List<ImageFile> parseFileInfo(
			ImageFileRequest requestDto, 
			List<MultipartFile> multipartFiles) throws Exception {
		
		List<ImageFile> fileList = new ArrayList<ImageFile>();
		
		if(!CollectionUtils.isEmpty(multipartFiles)) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateTimeFormatter = 
					DateTimeFormatter.ofPattern("yyyyMMdd");
			String current_date = now.format(dateTimeFormatter);
			
			String absolutePath = new File("").getAbsolutePath() + File.separator + "front\\vue-frontend\\" + File.separator;
			System.out.println(absolutePath);
			String path = "static\\item\\images" + File.separator + current_date;
			File file = new File(absolutePath + File.separator + path);
			
			// 디렉터리가 존재하지 않을 경우
			if(!file.exists()) {
				boolean wasSuccessful = file.mkdir();
				
				// 디렉터리 생성에 실패했을 경우
				if(!wasSuccessful)
					System.out.println("file: was not successful");
			}
			
			for(int i = 0; i < multipartFiles.size(); i++) {
				MultipartFile multipartFile = multipartFiles.get(i);
				
				String originalFileExtension;
				String contentType = multipartFile.getContentType();
				
				// 확장자명이 존재하지 않을 경우 처리 x
				if(ObjectUtils.isEmpty(contentType)) {
					break;
				}
				// 확장자가 jpeg, png인 파일들만 받아서 처리
				else {
					if(contentType.contains("image/jpeg")) {
						originalFileExtension = ".jpg";
					} else if(contentType.contains("image/png")) {
						originalFileExtension = ".png";
					} else { //다른 확장자일 경우 처리 x
						break;
					}
				}
				// 파일명 중복 피하고자 나노초까지 얻어와 지정
				String new_file_name = System.nanoTime() + originalFileExtension;
				
				imageFile = ImageFile.builder()
						.originFileName(multipartFile.getOriginalFilename())
						.storedFileName(path + File.separator + new_file_name)
						.storedThumbnail(path + File.separator + new_file_name)
						.delegateThumbnail(requestDto.getDelegateThumbnailList().get(i))
						.fileSize(multipartFile.getSize())
						.deleteCheck(YesNo.NO)
						.item(itemRepo.getOne(requestDto.getItemId()))
						.build();
				
				fileList.add(imageFile);
								
				file = new File(absolutePath + path + File.separator + new_file_name);
				multipartFile.transferTo(file);
			}
		}
		
		return fileList;
	}
	
	public List<ImageFile> parseFileUpdateInfo(
			ImageFileRequest requestDto, 
			List<MultipartFile> multipartFiles) throws Exception {
		
		List<ImageFile> fileList = new ArrayList<ImageFile>();
		
		if(!CollectionUtils.isEmpty(multipartFiles)) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateTimeFormatter = 
					DateTimeFormatter.ofPattern("yyyyMMdd");
			String current_date = now.format(dateTimeFormatter);
			
			String absolutePath = new File("").getAbsolutePath() + File.separator + "front\\vue-frontend\\" + File.separator;
			System.out.println(absolutePath);
			String path = "static\\item\\images" + File.separator + current_date;
			File file = new File(absolutePath + File.separator + path);
			
			// 디렉터리가 존재하지 않을 경우
			if(!file.exists()) {
				boolean wasSuccessful = file.mkdir();
				
				// 디렉터리 생성에 실패했을 경우
				if(!wasSuccessful)
					System.out.println("file: was not successful");
			}
			
			for(int i = 0; i < multipartFiles.size(); i++) {
				MultipartFile multipartFile = multipartFiles.get(i);
				
				String originalFileExtension;
				String contentType = multipartFile.getContentType();
				
				// 확장자명이 존재하지 않을 경우 처리 x
				if(ObjectUtils.isEmpty(contentType)) {
					break;
				}
				// 확장자가 jpeg, png인 파일들만 받아서 처리
				else {
					if(contentType.contains("image/jpeg")) {
						originalFileExtension = ".jpg";
					} else if(contentType.contains("image/png")) {
						originalFileExtension = ".png";
					} else { //다른 확장자일 경우 처리 x
						break;
					}
				}
				// 파일명 중복 피하고자 나노초까지 얻어와 지정
				String new_file_name = System.nanoTime() + originalFileExtension;
				
				imageFile = ImageFile.builder()
						.id(requestDto.getIdList().get(i))
						.originFileName(multipartFile.getOriginalFilename())
						.storedFileName(path + File.separator + new_file_name)
						.storedThumbnail(path + File.separator + new_file_name)
						.delegateThumbnail(requestDto.getDelegateThumbnailList().get(i))
						.fileSize(multipartFile.getSize())
						.deleteCheck(requestDto.getDeleteCheckList().get(i))
						.item(itemRepo.getOne(requestDto.getItemId()))
						.build();
				
				fileList.add(imageFile);
								
				file = new File(absolutePath + path + File.separator + new_file_name);
				multipartFile.transferTo(file);
			}
		}
		
		return fileList;
	}
}
