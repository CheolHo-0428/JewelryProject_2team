package com.ion.jewelry.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ion.jewelry.model.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	//장바구니조회
	Page<Cart> findByMemberId(Long member_id, Pageable pageable);
}
