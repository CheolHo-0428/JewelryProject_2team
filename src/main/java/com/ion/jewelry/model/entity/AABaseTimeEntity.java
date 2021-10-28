package com.ion.jewelry.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AABaseTimeEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt; // 데이터생성날짜
	
	@CreatedBy
	@Column(updatable = false)
	private String createdBy; // 데이터를 생성한 계정
	
	@LastModifiedDate
	private LocalDateTime updatedAt; // 데이터업데이트날짜
	
	@LastModifiedBy
	private String updatedBy; // 데이터를 수정한 계정
}
