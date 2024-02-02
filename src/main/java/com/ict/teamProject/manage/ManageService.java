package com.ict.teamProject.manage;

import org.springframework.stereotype.Service;

import com.ict.teamProject.manage.dto.DiaryDto;

@Service
public class ManageService {
	
	private ManageMapper mapper;
	public ManageService(ManageMapper mapper) {
		this.mapper = mapper;
	}
	
	public DiaryDto findDiaryById(String UserNDate) {
		return mapper.findDiaryById(UserNDate);
	}
}
