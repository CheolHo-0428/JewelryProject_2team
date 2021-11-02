package com.ion.jewelry.component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ion.jewelry.model.entity.QnaBoard;
import com.ion.jewelry.model.entity.ReviewBoard;
import com.ion.jewelry.model.enums.YesNo;
import com.ion.jewelry.model.network.request.QnaBoardRequest;

@Component
public class QnaFileHandler {

	private QnaBoard board;
	
	public QnaBoard parseFileInfo(
			QnaBoardRequest requestDto,
			List<MultipartFile> multipartFiles
	) throws Exception {
		
		if(!CollectionUtils.isEmpty(multipartFiles)) {
			LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);
		
            String absolutePath = new File("").getAbsolutePath() + File.separator + "front\\vue-frontend\\" + File.separator;
            System.out.println(absolutePath);
            String path = "static\\qna\\images" + File.separator + current_date;
            File file = new File(absolutePath + File.separator + path);
            
         // 디렉터리가 존재하지 않을 경우
            if(!file.exists()) {
                boolean wasSuccessful = file.mkdirs();
  
            // 디렉터리 생성에 실패했을 경우
            if(!wasSuccessful)
                System.out.println("file: was not successful");
            }
            
            for(MultipartFile multipartFile : multipartFiles) {
            	String originalFileExtension;
                String contentType = multipartFile.getContentType();
                
             // 확장자명이 존재하지 않을 경우 처리 x
                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
                    if(contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else  // 다른 확장자일 경우 처리 x
                        break;
                }
                
             // 파일명 중복 피하고자 나노초까지 얻어와 지정
                String new_file_name = System.nanoTime() + originalFileExtension;
                board = QnaBoard.builder()
        				.title(requestDto.getTitle())
        				.content(requestDto.getContent())
        				.writer(requestDto.getWriter())
        				.originFileName(multipartFile.getOriginalFilename())
        				.storedFileName(path + File.separator + new_file_name)
        				.fileSize(multipartFile.getSize())
        				.deleteCheck(YesNo.NO)
        				.build();
                
                file = new File(absolutePath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);
                
            }
            
		}
		
		return board;
	}

}
