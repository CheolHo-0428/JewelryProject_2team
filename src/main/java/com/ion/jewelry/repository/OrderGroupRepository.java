package com.ion.jewelry.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.model.entity.OrderGroup;
import com.ion.jewelry.model.enums.OrderProductState;

@Repository
public interface OrderGroupRepository extends JpaRepository<OrderGroup, Long>{
	Page<OrderGroup> findByMember(Member member, Pageable pageable);
	Page<OrderGroup> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
	Page<OrderGroup> findByOrderProductState(OrderProductState stateEnum, Pageable pageable);
	Page<OrderGroup> findByResipientContaining(String name, Pageable pageable);
}
