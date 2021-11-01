package com.ion.jewelry.model.network.request;

import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.response.ImageFileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageFileRequest {
	
	private Long id; //이미지번호
	
	private String originFileName; //원본파일이름
	
	private String storedFileName; //서버에저장될파일이름
	
	private String storedThumbnail; //썸네일이미지
	
	private YesNo delegateThumbnail; //대표썸네일여부
	
	private Long fileSize; //파일크기
	
	private YesNo deleteCheck; //파일삭제여부
	
	private Long itemId; //해당상품 번호
}
