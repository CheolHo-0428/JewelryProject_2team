package com.ion.jewelry.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ion.jewelry.model.entity.Role;
import com.ion.jewelry.model.enums.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {  // JPA에서 사용할 Role Repository임
	Optional<Role> findByName(ERole name);  
	
	
}
