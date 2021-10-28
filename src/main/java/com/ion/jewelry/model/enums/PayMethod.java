package com.ion.jewelry.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayMethod {
	//enum(id, title, description)
	BANK_TRANSFER(0,"계좌이체", "계좌이체");
	
	private Integer id;
	
	private String title;
	
	private String description;
}
