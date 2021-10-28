package com.ion.jewelry.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum YesNo {
	
	NO(0, "No", "No"),
	YES(1, "Yes", "Yes");
	
	private Integer id;
	
	private String title;
	
	private String description;
	
}
