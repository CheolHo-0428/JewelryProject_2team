package com.ion.jewelry.model.network.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupRequest {  // 회원가입 시 웹페이지에서 입력한 Request 내용임
    @NotBlank
    @Size(min = 3, max = 20)
    private String account; 	//아이디
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;	//비밀번호
    
    @Size(max = 50)
    @Email
    private String email;		//이메일
    
    private String name;		//이름
    
    private String phone;		//전화번호
    
    private String postCode; 	//우편번호
    
    private String address; 	//주소1
    
    private String detailAddress; //주소2(상세주소)
    
    private Set<String> role;	//권한
}
