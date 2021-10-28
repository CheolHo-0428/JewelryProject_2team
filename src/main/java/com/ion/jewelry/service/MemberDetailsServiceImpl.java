package com.ion.jewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ion.jewelry.jwt.MemberSecurity;
import com.ion.jewelry.model.entity.Member;
import com.ion.jewelry.repository.MemberRepository;
//UserDetailsService - 내장 객체로부터 상속받은 UserDetailsServiceImpl 사용자 객체임
@Service 
public class MemberDetailsServiceImpl implements UserDetailsService {
	@Autowired
	MemberRepository memberRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		Member member = memberRepository.findByAccount(account)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with account: " + account));

		return MemberSecurity.build(member);
	}

}
