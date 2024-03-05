package com.ict.teamProject.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.ict.teamProject.schedule.service.SCHDto;
import com.ict.teamProject.schedule.service.SCHService;



@Service
public class SCHServiceImpl implements SCHService<SCHDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private SCHMapper mapper;
	
	

}
