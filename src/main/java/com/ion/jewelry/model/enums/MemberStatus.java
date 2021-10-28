package com.ion.jewelry.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberStatus {
	//enum(id, title, description)
	REGISTERED(0, "등록", "회원등록상태"),
	UNREGISTERED(1, "등록해지", "회원등록해지");
	
	private Integer id;
	
	private String title;
	
	private String description;
}
