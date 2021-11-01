package com.ion.jewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ion.jewelry.model.entity.ImageFile;
import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.model.network.Pagination;
import com.ion.jewelry.model.network.request.ImageFileRequest;
import com.ion.jewelry.model.network.response.ImageFileResponse;
import com.ion.jewelry.repository.ItemRepository;

@Service
public class ImageFileService extends AABaseService<ImageFileRequest, ImageFileResponse, ImageFile> {
	
	@Autowired
	private ItemRepository itemRepo;
	
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

	@Override
	public Header<ImageFileResponse> update(Header<ImageFileRequest> request) {
		ImageFileRequest imageFileRequest = request.getData();
		Optional<ImageFile> optional = baseRepo.findById(imageFileRequest.getId());
		return optional
				.map(image -> {
					image
					.setOriginFileName(imageFileRequest.getOriginFileName())
					.setStoredFileName(imageFileRequest.getStoredFileName())
					.setStoredThumbnail(imageFileRequest.getStoredThumbnail())
					.setDelegateThumbnail(imageFileRequest.getDelegateThumbnail())
					.setFileSize(imageFileRequest.getFileSize())
					.setDeleteCheck(imageFileRequest.getDeleteCheck())
					.setItem(itemRepo.getOne(imageFileRequest.getItemId()));
					return image;
				})
				.map(image -> baseRepo.save(image))
				.map(image -> response(image))
				.map(image -> Header.OK(image))
				.orElseGet(() -> Header.ERROR("업데이트할 데이터가 없습니다."));
	}

	@Override
	public Header delete(Long id) {
		Optional<ImageFile> optional = baseRepo.findById(id);
		
		return optional
				.map(image -> {
					baseRepo.delete(image);
					return Header.OK();
				})
				.orElseGet(() -> Header.ERROR("삭제될 데이터가 없습니다."));
	}

	@Override
	public Header<ImageFileResponse> selectRead(Long id) {
		Optional<ImageFile> optional = baseRepo.findById(id);
		return optional
				.map(image -> response(image))
				.map(image -> Header.OK(image))
				.orElseGet(() -> Header.ERROR("조회하신 데이터가 없습니다."));
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
	
	//응답메소드
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
