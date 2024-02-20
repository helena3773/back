package com.ict.teamProject.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ict.teamProject.member.service.impl.MemberMapper;

@Service
public class MemberService {
	private MemberMapper mapper;

	
	public MemberService(MemberMapper mapper) {
		this.mapper=mapper;
	}
	
	public int join(MemberDto dto) {
		return mapper.saveMember(dto);
	}
	
	
	public MemberDto selectdata(String id) {
		return mapper.findByMember(id); //MemberMapper 인터페이스의 findByMember메소드 호출
	}
	
	public int updatedata(String id, String colname, String newcolval) {	
		return mapper.updateMember(id, colname, newcolval);		
	}
	
	public int logincheck(String id, String pwd) {
		return mapper.logincheck(id, pwd);
	}

	public Map searchPoint(String id) {
		return mapper.searchPoint(id);
	}

	public Map getUserAddress(String id) {
		return mapper.getUserAddress(id);
	}

//	public int updateSocialUser(MemberDto dto) {
//		return mapper.updateSocialUser(dto);
//	
//	}

}
