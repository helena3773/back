package com.ict.teamProject.notic;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ict.teamProject.notic.dto.NoticDto;

@Service
public class NoticService {
	
	//mapper 생성자 주입
	private NoticMapper mapper;
	public NoticService(NoticMapper mapper) {
		this.mapper = mapper;
	}
	
	//유저 PayList 목록
	public List<NoticDto> findAllNotic(String id) {
		return mapper.findAllNotic(id);
	}
}
