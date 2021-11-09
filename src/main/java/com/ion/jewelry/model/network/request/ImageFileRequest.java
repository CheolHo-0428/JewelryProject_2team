package com.ion.jewelry.model.network.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ion.jewelry.model.enums.YesNo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ImageFileRequest {
	
	private Long id; //이미지번호
	
	private List<Long> idList; //이미지번호리스트
	
	private String originFileName; //원본파일이름
	
	private String storedFileName; //서버에저장될파일이름
	
	private String storedThumbnail; //썸네일이미지
	
	private YesNo delegateThumbnail; //대표썸네일여부
	
	private List<YesNo> delegateThumbnailList; //대표썸네일여부 리스트
	
	private Long fileSize; //파일크기
	
	private YesNo deleteCheck; //파일삭제여부
	
	private List<YesNo> deleteCheckList; //파일삭제여부
	
	private Long itemId; //해당상품 번호
	
	private List<MultipartFile> files;
}
