package com.ion.jewelry.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryType {
	//enum(id, title, description)
	RING(0, "반지", "반지"),
	EARRINGS(1, "귀걸이", "귀걸이"),
	BRACELET(2, "팔찌", "팔찌"),
	NECKLACE(3, "목걸이", "목걸이");
	
	private Integer id;
	
	private String title;
	
	private String description;
}
