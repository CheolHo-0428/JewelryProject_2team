package com.ion.jewelry.model.network.request;

import com.ion.jewelry.model.network.response.ReviewBoardResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewBoardReplyInfoResponse {
	
	private ReviewBoardResponse reviewBoardResponse;
}
