package com.ion.jewelry.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ion.jewelry.model.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	//장바구니조회
	List<Cart> findByMemberId(Long member_id);
	
	//멤버아이디와 아이템아이디 존재할시
	Boolean existsByMemberIdAndItemId(Long member_id,Long item_id);

	Optional<Cart> findByMemberIdAndItemId(Long memberId, Long itemId);
}
