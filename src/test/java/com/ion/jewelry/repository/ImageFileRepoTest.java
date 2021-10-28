package com.ion.jewelry.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ion.jewelry.JewelryProjectApplicationTests;
import com.ion.jewelry.model.entity.ImageFile;
import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.enums.YesNo;

public class ImageFileRepoTest extends JewelryProjectApplicationTests {
	
	@Autowired
	private ImageFileRepository imageFileRepo;
	
	@Autowired
	private ItemRepository itemRepo;
	
	//@Test
	public void create() {
		ImageFile imageFile = new ImageFile();
		
		imageFile.setOriginFileName("KakaoTalk_20211004_191841500.jpg");
		imageFile.setStoredFileName("1_image.jpg");
		imageFile.setStoredThumbnail(null);
		imageFile.setDelegateThumbnail(YesNo.YES);
		imageFile.setFileSize(3123413235L);
		imageFile.setDeleteCheck(YesNo.NO);
		
		Optional<Item> optional = itemRepo.findById(1L);
		optional.ifPresent(item -> {
			imageFile.setItem(item);
		});
		
		ImageFile newImageFile = imageFileRepo.save(imageFile);
		Assertions.assertNotNull(newImageFile);
	}
	
	@Test
	@Transactional
	public void allRead() {
		List<ImageFile> imageFileList = imageFileRepo.findAll();
		imageFileList.forEach(imageFile -> {
			System.out.println("read data => " + imageFile.toString());
		});
	}
}
