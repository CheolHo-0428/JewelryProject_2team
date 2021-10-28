package com.ion.jewelry.ifs;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ion.jewelry.model.network.Header;

public interface CrudInterface<Req, Res> {
	
	public Header<Res> create(Header<Req> request); //데이터 삽입
	
	public Header<Res> update(Header<Req> request); // 데이터 수정
	
	public Header delete(Long id); //데이터 삭제
	
	public Header<Res> selectRead(Long id); //데이터 선택 조회(id)
	
	public Header<List<Res>> allRead(); //데이터 조회
	
	public Header<List<Res>> pagingRead(Pageable pageable); //데이터 조회(페이징 처리)
	
}
