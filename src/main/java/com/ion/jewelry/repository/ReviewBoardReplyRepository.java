package com.ion.jewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ion.jewelry.model.entity.ReviewBoardReply;

@Repository
public interface ReviewBoardReplyRepository extends JpaRepository<ReviewBoardReply, Long> {

}
