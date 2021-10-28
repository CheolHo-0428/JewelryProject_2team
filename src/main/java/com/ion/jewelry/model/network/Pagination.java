package com.ion.jewelry.model.network;

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
public class Pagination {

	private Integer totalPages; // 총 페이지값
	
	private Long totalElements; // 총 레코드(행)값
	
	private Integer currentPage; // 현재 페이지값
	
	private Integer currentElements; // 현재 레코드(행)값
}
