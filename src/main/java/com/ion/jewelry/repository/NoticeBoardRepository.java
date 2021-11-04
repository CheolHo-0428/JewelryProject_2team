package com.ion.jewelry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ion.jewelry.model.entity.NoticeBoard;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

	Page<NoticeBoard> findByTitleContaining(String keyword, Pageable pageable);
	Page<NoticeBoard> findByWriterContaining(String keyword, Pageable pageable);
}
