package com.ion.jewelry.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ion.jewelry.model.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Optional<Member> findByAccount(String account);
	
	//아이디존재여부
	Boolean existsByAccount(String account);
	
	//이메일존재여부
	Boolean existsByEmail(String email);
	
	//아이디찾기
	@Query("select m from Member m where m.name = :name and m.email = :email")
	Optional<Member> findByNameAndEmail(@Param("name")String name,@Param("email")String email);
	
	//비밀번호찾기
	@Query("select m from Member m where m.name = :name and m.account = :account and m.email = :email")
	Optional<Member> findByNameAndAccountAndEmail(@Param("name")String name,@Param("account") String account,@Param("email") String email);
	
	
}
