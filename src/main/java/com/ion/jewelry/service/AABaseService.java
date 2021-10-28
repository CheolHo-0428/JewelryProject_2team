package com.ion.jewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ion.jewelry.ifs.CrudInterface;

@Component
public abstract class AABaseService<Req, Res, Entity> implements CrudInterface<Req, Res> {

	@Autowired(required = false)
	protected JpaRepository<Entity, Long> baseRepo;
	
}
