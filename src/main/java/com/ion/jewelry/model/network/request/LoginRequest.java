package com.ion.jewelry.model.network.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class LoginRequest {
	@NotBlank
	private String account;	//아이디

	@NotBlank
	private String password;//비밀번호
}
