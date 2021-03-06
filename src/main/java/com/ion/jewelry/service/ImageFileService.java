package com.ion.jewelry.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ion.jewelry.component.ItemImageFileHandler;
import com.ion.jewelry.model.entity.ImageFile;
import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.ImageFileRequest;
import com.ion.jewelry.model.network.response.ImageFileResponse;
import com.ion.jewelry.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageFileService extends AABaseService<ImageFileRequest, ImageFileResponse, ImageFile> {
	
	@Autowired
	private ItemRepository itemRepo;
	
	private final ItemImageFileHandler fileHandler;
		
	@Override
	public Header<ImageFileResponse> create(Header<ImageFileRequest> request) {
		ImageFileRequest imageFileRequest = request.getData();
		ImageFile imageFile = ImageFile.builder()
				.originFileName(imageFileRequest.getOriginFileName())
				.storedFileName(imageFileRequest.getStoredFileName())
				.storedThumbnail(imageFileRequest.getStoredThumbnail())
				.delegateThumbnail(imageFileRequest.getDelegateThumbnail())
				.fileSize(imageFileRequest.getFileSize())
				.deleteCheck(YesNo.NO)
				.item(itemRepo.getOne(imageFileRequest.getItemId()))
				.build();
		
		ImageFile newImageFile = baseRepo.save(imageFile);
		ImageFileResponse imageFileResponse = response(newImageFile);
		
		return Header.OK(imageFileResponse);
	}
	
	@Transactional
	public Header<List<ImageFileResponse>> createImg(Header<ImageFileRequest> request, List<MultipartFile> files) throws Exception {
		ImageFileRequest imageFileRequest = request.getData();
		List<ImageFile> imageFileList = fileHandler.parseFileInfo(imageFileRequest, files);
		List<ImageFileResponse> imageFileResList = new ArrayList<ImageFileResponse>();
		
		if(!imageFileList.isEmpty()) {
			for(ImageFile image : imageFileList) {
				ImageFile newImageFile = baseRepo.save(image);
				ImageFileResponse imageFileResponse = response(newImageFile);
				imageFileResList.add(imageFileResponse);
			}		
		}
		return Header.OK(imageFileResList);
	}
	
	@Transactional
	public Header<ImageFileResponse> updateImg(Header<ImageFileRequest> request, List<MultipartFile> files) throws Exception {		
		ImageFileRequest imageFileRequest = request.getData();
		Optional<ImageFile> optional = baseRepo.findById(imageFileRequest.getId());
		
		ImageFile imageFile = fileHandler.parseFileInfo_single(imageFileRequest, files);
				
		return optional
				.map(image -> {
					if(imageFileRequest.getDeleteCheck() == YesNo.YES) {
						String path = image.getStoredFileName();
						
						File file = new File(new File("").getAbsolutePath() + File.separator + "front\\vue-frontend\\" + File.separator + path);
						if(file.exists()) {
							if(file.delete()) {
								//delete(imageFileRequest.getId());
								System.out.println("???????????? ??????");
							} else {
								System.out.println("???????????? ??????");
							}
						} else {
							System.out.println("????????? ???????????? ????????????.");
						}
					}
					image
					.setOriginFileName(imageFile.getOriginFileName())
					.setStoredFileName(imageFile.getStoredFileName())
					.setStoredThumbnail(imageFile.getStoredThumbnail())
					.setDelegateThumbnail(imageFile.getDelegateThumbnail())
					.setFileSize(imageFile.getFileSize())
					.setDeleteCheck(imageFile.getDeleteCheck())
					.setItem(imageFile.getItem());
				return image;
				})
				.map(image -> baseRepo.save(image))
				.map(image -> response(image))
				.map(image -> Header.OK(image))
				.orElseGet(() -> Header.ERROR("??????????????? ???????????? ????????????."));
	}
	
	@Override
	public Header<ImageFileResponse> update(Header<ImageFileRequest> request) {
		ImageFileRequest imageFileRequest = request.getData();
		Optional<ImageFile> optional = baseRepo.findById(imageFileRequest.getId());
		return optional
				.map(image -> {
					image
//					.setOriginFileName(imageFileRequest.getOriginFileName())
//					.setStoredFileName(imageFileRequest.getStoredFileName())
//					.setStoredThumbnail(imageFileRequest.getStoredThumbnail())
					.setDelegateThumbnail(imageFileRequest.getDelegateThumbnail());
//					.setFileSize(imageFileRequest.getFileSize())
//					.setDeleteCheck(imageFileRequest.getDeleteCheck())
//					.setItem(itemRepo.getOne(imageFileRequest.getItemId()));
					return image;
				})
				.map(image -> baseRepo.save(image))
				.map(image -> response(image))
				.map(image -> Header.OK(image))
				.orElseGet(() -> Header.ERROR("??????????????? ???????????? ????????????."));
	}
	
	@Override
	public Header delete(Long id) {
		Optional<ImageFile> optional = baseRepo.findById(id);
		
		return optional
				.map(image -> {
					baseRepo.delete(image);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("????????? ???????????? ????????????."));
	}

	@Override
	public Header<ImageFileResponse> selectRead(Long id) {
		Optional<ImageFile> optional = baseRepo.findById(id);
		return optional
				.map(image -> response(image))
				.map(image -> Header.OK(image))
				.orElseGet(() -> Header.ERROR("???????????? ???????????? ????????????."));
	}

	@Override
	public Header<List<ImageFileResponse>> allRead() {
		List<ImageFile> imageFileList = baseRepo.findAll();
		
		List<ImageFileResponse> imageResList = imageFileList.stream()
				.map(image -> response(image))
				.collect(Collectors.toList());
		
		return Header.OK(imageResList);
	}

	@Override
	public Header<List<ImageFileResponse>> pagingRead(Pageable pageable) {
		Page<ImageFile> page = baseRepo.findAll(pageable);
		
		List<ImageFileResponse> imageResList = page.stream()
				.map(image -> response(image))
				.collect(Collectors.toList());
		
		Pagination pagination = Pagination.builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.currentPage(page.getNumber())
				.currentElements(page.getNumberOfElements())
				.build();
		
		return Header.OK(imageResList, pagination);
	}
	
	//???????????????
	public ImageFileResponse response(ImageFile image) {
		ImageFileResponse res = ImageFileResponse.builder()
				.id(image.getId())
				.originFileName(image.getOriginFileName())
				.storedFileName(image.getStoredFileName())
				.storedThumbnail(image.getStoredThumbnail())
				.delegateThumbnail(image.getDelegateThumbnail())
				.fileSize(image.getFileSize())
				.deleteCheck(image.getDeleteCheck())
				.itemId(image.getItem().getId())
				.build();
		
		return res;
	}

	
}
