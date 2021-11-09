package com.ion.jewelry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ion.jewelry.model.entity.Cart;
import com.ion.jewelry.model.entity.ImageFile;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
	ImageFile findByItemId(Long item_id);
}
