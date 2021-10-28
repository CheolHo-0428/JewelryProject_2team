package com.ion.jewelry.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayAccount {
	//enum(id, title, description)
	IBK(0, "기업은행계좌", "기업은행:000-111222-01-123"),
	WOORI(1, "우리은행계좌", "우리은행:000-222333-01-567");
	
	private Integer id;
	
	private String title;
	
	private String description;
}
