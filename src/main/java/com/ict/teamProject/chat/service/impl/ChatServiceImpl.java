package com.ict.teamProject.chat.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.ict.teamProject.chat.service.ChatDto;
import com.ict.teamProject.chat.service.ChatService;



@Service
public class ChatServiceImpl implements ChatService<ChatDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private ChatMapper mapper;
	
	
	//메세지 등록
	@Override
	public int insert(Map map) {
		int affected = 0;
		try {
			affected = mapper.save(map);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return affected;
	}
	/*
	//게시물 파일등록
	@Override
	public int insertFile(Map map) {
		int affected=0;
		try {
			affected=mapper.saveFiles(map);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return affected;		
	}
	
	
	//게시물 수정
	@Override
	public int update(ChatDto dto) {
		int affected=0;
		try {
			affected=mapper.update(dto);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return affected;

}

	@Override
	public int deleteBBS(int bno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFiles(int bno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ChatDto selectOne(int bno) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
