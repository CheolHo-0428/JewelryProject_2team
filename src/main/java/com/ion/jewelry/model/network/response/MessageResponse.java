package com.ion.jewelry.model.network.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageResponse {  // Request에 대한 Response를 Message 형태로 나타낸 것임
	private String message;
}
