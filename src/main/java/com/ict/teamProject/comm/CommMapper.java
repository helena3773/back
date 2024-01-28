package com.ict.teamProject.comm;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.comm.dto.FriendDto;
import com.ict.teamProject.comm.dto.MateDto;
import com.ict.teamProject.comm.dto.MySubscriberDto;
import com.ict.teamProject.comm.dto.SubscribeToDto;
import com.ict.teamProject.comm.dto.UserProfileDto;

@Mapper
public interface CommMapper {
	public List<MateDto> findAllMatesById(String id);
	public List<FriendDto> findAllFriendsById(String id);
	public List<SubscribeToDto> findAllSubToById(String id); //내가 구독한 목록
	public List<MySubscriberDto> findAllMySubById(String id); //나의 구독자 목록
	public String findNameById(String id);
	public int findFnumById(String id); //친구 수
	public int findMnumById(String id); //메이트 수
	public int findSnumById(String id); //구독자 수
	public String findProPathById(String id); //프로필 사진
	public void putFavorableRating(MateDto dto); //호감도 수정
	public String findIntroductionById(String id);//한줄 소개
	public Date findJoinDateById(String id); //가입 날짜 받기
	public void putFriendBlocking(String id); //친구 끊기 수정
}
