package com.ion.jewelry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ion.jewelry.model.entity.ResignMember;

@Repository
public interface ResignMemberRepository extends JpaRepository<ResignMember, Long> {

	Page<ResignMember> findByAccountContaining(String keyword, Pageable pageable);

	Page<ResignMember> findByNameContaining(String keyword, Pageable pageable);

}
