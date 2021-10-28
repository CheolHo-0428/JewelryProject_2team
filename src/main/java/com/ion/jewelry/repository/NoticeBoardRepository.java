package com.ion.jewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ion.jewelry.model.entity.NoticeBoard;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

}
