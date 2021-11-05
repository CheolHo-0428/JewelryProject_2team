package com.ion.jewelry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ion.jewelry.model.entity.Item;
import com.ion.jewelry.model.enums.ObjectStatus;

public interface ItemRepository extends JpaRepository<Item, Long> {

	Page<Item> findByNameContaining(String keyword, Pageable pageable);
	Page<Item> findByNameContainsIgnoreCase(String keyword, Pageable pageable);
	
	Page<Item> findById(Long keyword, Pageable pageable);
	Page<Item> findByCategoryId(Long keyword, Pageable pageable);
	
	Page<Item> findByStatus(ObjectStatus keyword, Pageable pageable);
	Page<Item> findByStatusIgnoreCase(ObjectStatus keyword, Pageable pageable);
	
}
