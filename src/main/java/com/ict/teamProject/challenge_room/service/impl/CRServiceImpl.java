package com.ict.teamProject.challenge_room.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.BBSService;
import com.ict.teamProject.bbs.service.LikesDto;
import com.ict.teamProject.challenge_room.service.CPDto;
import com.ict.teamProject.challenge_room.service.CRDto;
import com.ict.teamProject.challenge_room.service.CRService;
import com.ict.teamProject.files.service.FilesDto;
import com.ict.teamProject.member.service.MemberDto;


//24.02.18 생성
@Service
public class CRServiceImpl implements CRService<CRDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private CRMapper mapper;

	@Override
	public List<CRDto> selectAll() {
		return mapper.findAll();
	}

	@Override
	public CRDto viewMyRoom(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectMyRoom(String id) {
		Integer room = mapper.getMyRoom(id);
		return room;
	}

	@Override
	public int insert(CRDto map) {
		int affected = mapper.save(map);
		return affected;
	}

	@Override
	public int update(String id) {
		return mapper.update(id);
	}

	@Override
	public int delete(int room) {
		int affected = mapper.delete(room);
		return affected;
	}

	@Override
	public int insertP(CPDto dto) {
		int affected = mapper.insertP(dto);
		return affected;
	}

	@Override
	public int getSeqValue() {
		return mapper.getSeqValue();
	}

	@Override
	public void deletep(String id) {
		mapper.deletep(id);
	}

	@Override
	public Map findmyData(String id) {
		return mapper.findmyData(id);
	}

	@Override
	public List participantsdata() {
		return mapper.participantsdata();
	}

	@Override
	public int join(CPDto dto) {
		return mapper.join(dto);
	}

	@Override
	public CRDto findRoomData(int challNo) {
		return mapper.findRoomData(challNo);
	}

	@Override
	public String selectManager(int room) {
		return mapper.selectManager(room);
	}


}
