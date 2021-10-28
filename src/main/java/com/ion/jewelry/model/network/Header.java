package com.ion.jewelry.model.network;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Header<T> {
	
	private LocalDateTime transactionTime; // api 통신시간
	
	private String resultCode; // api 통신 코드
	
	private String description; // api 부가 설명
	
	private T data; // data(body)
	
	private Pagination pagination; // 페이지 정보
	
	// OK
	public static <T> Header<T> OK(){
		return (Header<T>)Header.builder()
				.transactionTime(LocalDateTime.now())
				.resultCode("OK")
				.description("Connection OK")
				.build();
	}
	
	// OK(Data)
	public static <T> Header<T> OK(T data){
		return (Header<T>)Header.builder()
				.transactionTime(LocalDateTime.now())
				.resultCode("OK")
				.description("Connection OK")
				.data(data)
				.build();
	}
	
	// OK(Data, pagination)
	public static <T> Header<T> OK(T data, Pagination pagination){
		return (Header<T>)Header.builder()
				.transactionTime(LocalDateTime.now())
				.resultCode("OK")
				.description("Connection OK")
				.data(data)
				.pagination(pagination)
				.build();
	}
	
	// ERROR
	public static <T> Header<T> ERROR(String description){
		return (Header<T>)Header.builder()
				.transactionTime(LocalDateTime.now())
				.resultCode("ERROR")
				.description(description)
				.build();
	}
	
}
