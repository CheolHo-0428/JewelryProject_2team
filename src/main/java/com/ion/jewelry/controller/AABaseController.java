package com.ion.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ion.jewelry.ifs.CrudInterface;
import com.ion.jewelry.model.network.Header;
import com.ion.jewelry.service.AABaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public abstract class AABaseController<Req, Res, Entity> implements CrudInterface<Req, Res> {
	
	@Autowired
	protected AABaseService<Req, Res, Entity> baseService;

	@Override
	@PostMapping("")
	public Header<Res> create(@RequestBody Header<Req> request) {
		log.info("{}", request);
		return baseService.create(request);
	}

	@Override
	@PutMapping("")
	public Header<Res> update(@RequestBody Header<Req> request) {
		log.info("{}", request);
		return baseService.update(request);
	}

	@Override
	@DeleteMapping("{id}")
	public Header delete(@PathVariable Long id) {
		log.info("read id: {}", id);
		return baseService.delete(id);
	}

	@Override
	@GetMapping("{id}")
	public Header<Res> selectRead(@PathVariable Long id) {
		log.info("read id: {}", id);
		return baseService.selectRead(id);
	}

	@Override
	@GetMapping("")
	public Header<List<Res>> allRead() {
		return baseService.allRead();
	}

	@Override
	@GetMapping("/paging")
	public Header<List<Res>> pagingRead(Pageable pageable) {
			log.info("{}", pageable);
		return baseService.pagingRead(pageable);
	}
	
}
