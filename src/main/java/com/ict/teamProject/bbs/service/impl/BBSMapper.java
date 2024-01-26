package com.ict.teamProject.bbs.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.files.service.FilesDto;

@Mapper
public interface BBSMapper {

	//전체 조회
	List findAll();
	List findFile(int bno);
	
	//레코드 하나
	BBSDto findByBBS(Map map);
	
	//입력
	int save(Map map);
	int saveFiles(Map map);
	
	//씨큐리티 용
	int saveUser(Map<String, String> map);
	

	//레코드 하나 수정
	int updateBBS(BBSDto record);
	int updateFiles(FilesDto files);
	
	//레코드 하나 삭제
	int deleteFiles(FilesDto files);
	int deleteBBS(BBSDto record);
	
	// 시퀀스 값을 먼저 가져오기
	int getSeqNextVal();

}
