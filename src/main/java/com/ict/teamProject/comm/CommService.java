package com.ict.teamProject.comm;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ict.teamProject.comm.dto.FriendDto;
import com.ict.teamProject.comm.dto.MateDto;
import com.ict.teamProject.comm.dto.MySubscriberDto;
import com.ict.teamProject.comm.dto.SubscribeToDto;

@Service
public class CommService {
	
	//mapper 생성자 주입
	private CommMapper mapper;
	public CommService(CommMapper mapper) {
		this.mapper = mapper;
	}
	
	//친구, 메이트, 구독자 조회
	public List<MateDto> findAllMatesById(String id) {//메이트
		return mapper.findAllMatesById(id);
	}
	
	public List<FriendDto> findAllFriendsById(String id) {//친구
		return mapper.findAllFriendsById(id);
	}
	
	public List<SubscribeToDto> findAllSubToById(String id){//구독한 목록
		return mapper.findAllSubToById(id);
	}
	
	public List<MySubscriberDto> findAllMySubById(String id){//구독자 목록
		return mapper.findAllMySubById(id);
	}
	
	//이름 조회
	public String findNameById(String id) {
		return mapper.findNameById(id);
	}
	
	//친구,메이트, 구독자 수 조회
	public int findFMSnumById(String id, String cate) {
		if(cate.equals("f")) return mapper.findFnumById(id);
		else if(cate.equals("m")) return mapper.findMnumById(id);
		else return mapper.findSnumById(id);
	}
	
	//프로필 사진 조회
	public String findProPathById(String id) {
		return mapper.findProPathById(id);
	}
	
	//메이트의 호감도 수정
	public void putFavorableRating(MateDto dto) {
		mapper.putFavorableRating(dto);
	}
}
