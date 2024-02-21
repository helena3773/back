package com.ict.teamProject.challenge_room.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.challenge_room.service.CPDto;
import com.ict.teamProject.challenge_room.service.CRDto;
import com.ict.teamProject.member.service.MemberDto;

//24.02.18 생성
@Mapper
public interface CRMapper {

	//전체 조회
	List findAll();
	
	//자기 챌린지 보기
	CRDto findByBBS(int bno);
		
	//입력/수정/삭제
	int save(CRDto map);
	int update(String id);
	int delete(int room);
	
	// 자기 방 번호 가져오기
	Integer getMyRoom(String id);
	int insertP(CPDto dto);

	int getSeqValue();

	void deletep(String id);
	
	Map findmyData(String id);

	List participantsdata();

	int join(CPDto dto);

	CRDto findRoomData(int challNo);

	String selectManager(int room);

	

}
