package com.ict.teamProject.member.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.member.service.MemberDto;

@Mapper
public interface MemberMapper {
	
	int saveMember(MemberDto dto);
	
	 boolean checkPhoneNumber(String tel);
	 
	 //레코드 하나
	 MemberDto findByMember(String id);
	 
	 int updateMember(String id, String colname, String newcolval);
	 
	 int logincheck(String id, String pwd);



	String findbyUserPassword(Map map);
	Map<String, Object> getInfo(String id);
	int isPreviousUser(String id);
	
	MemberDto findByUsername(String id);
	void joinSocialMember(MemberDto dto);
	MemberDto findByMemberInfo(String id);
	
	//회원가입
	void profileImageTable(MemberDto dto);

	Map searchPoint(String id);

	 void updateUser(MemberDto userEntity);
	//int updateSocialUser(MemberDto dto);




}
