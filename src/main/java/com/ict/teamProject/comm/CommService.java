package com.ict.teamProject.comm;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ict.teamProject.comm.dto.FriendDto;
import com.ict.teamProject.comm.dto.MateDto;
import com.ict.teamProject.comm.dto.MySubscriberDto;
import com.ict.teamProject.comm.dto.SubscribeToDto;
import com.ict.teamProject.comm.dto.UserProfileDto;

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
	
	//가입날짜 조회
	public Date findJoinDateById(String id) {
		return mapper.findJoinDateById(id);
	}
	
	//메이트의 호감도 수정
	public void putFavorableRating(MateDto dto) {
		mapper.putFavorableRating(dto);
	}
	
	//유저의 한줄 소개
	public String findIntroductionById(String id) {
		return mapper.findIntroductionById(id);
	}
	
	//친구 차단
	public void putFriendBlocking(String id) {
		mapper.putFriendBlocking(id);
	}
	
	//친구 삭제
	public void deleteFriend(String id) {
		mapper.deleteFriend(id);
	}
	
	//구독 끊기
	public void deleteSubTo(String id) {
		mapper.deleteSubTo(id);
	}
	
	//메이트 끊기
	public void deleteMate(String id) {
		mapper.deleteMate(id);
	}
	
	//구독자 삭제
	public void deleteSubscriber(Map<String, String> ids) {
		mapper.deleteSubscriber(ids);
	}
	
	//변경된 유저프로필 사진정보 DB 저장
	public int putProfileImage(UserProfileDto dto) {
		return mapper.putProfileImage(dto);
	}
}
