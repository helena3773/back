package com.ict.teamProject.chat.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.BBSUsersProfileDto;
import com.ict.teamProject.bbs.service.LikesDto;
import com.ict.teamProject.chat.service.ChatDto;
import com.ict.teamProject.files.service.FilesDto;

@Mapper
public interface ChatMapper {

	
	//레코드 하나
	//BBSDto findByBBS(int bno);
	
	//입력
	int save(Map map);
	//int saveFiles(Map map);

	//int update(ChatDto dto);

	
	
	
}
