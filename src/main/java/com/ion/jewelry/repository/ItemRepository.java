package com.ion.jewelry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ion.jewelry.model.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	Page<Item> findByNameContaining(String keyword, Pageable pageable);
}
