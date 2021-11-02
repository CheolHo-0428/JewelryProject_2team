package com.ion.jewelry.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ObjectStatus {
	//enum(id, title, description)
	REGISTERED(0, "REGISTERED", "등록 및 활성화"),
	UNREGISTERED(1, "UNREGISTERED", "등록해지 및 비활성화");
	
	private Integer id;
	
	private String title;
	
	private String description;
}
