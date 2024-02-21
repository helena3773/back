package com.ict.teamProject.challenge_room.service;

import java.util.List;
import java.util.Map;

import com.ict.teamProject.member.service.MemberDto;


//24.02.18 생성
public interface CRService<T> {

	//전체 챌린지 보기
	List<CRDto> selectAll();
	
	//자기 챌린지 보기
	CRDto viewMyRoom(String id);
	
	//자기 챌린지 방 번호 찾기
	Integer selectMyRoom(String id);
		
	//입력/수정/삭제용
	int insert(CRDto dto);
	int update(String id);
	int delete(int room);

	int insertP(CPDto dto);

	int getSeqValue();

	void deletep(String id);

	Map findmyData(String id);

	List participantsdata();

	int join(CPDto dto);

	CRDto findRoomData(int challNo);

	String selectManager(int room);

	
}
