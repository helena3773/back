package com.ict.teamProject.commentline.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.ict.teamProject.commentline.service.CommentLineDto;
import com.ict.teamProject.commentline.service.CommentLineService;


@Service
public class CommentLineServiceImpl implements CommentLineService<CommentLineDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private CommentLineMapper mapper;	
	
	//게시물 등록
	@Override
	public int insert(Map map) {
		int affected=0;
		try {
			affected=mapper.save(map);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return affected;
	}

	//게시물 상세보기
	@Override
	public CommentLineDto selectOne(Map map) {		
		return mapper.findByCommentLine(map);
	}

	//게시물 수정
	@Override
	public int update(CommentLineDto record) {
		int affected=0;
		try {			
			affected= mapper.updatefindByCommentLine(record);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return affected;
	}

	//트랜잭션 처리 관련 빈 주입 받기
	@Autowired
	private TransactionTemplate template;

	
	@Override
	public int delete(CommentLineDto record) {			
		int affected=0;//특정 글번호에 따른 삭제된 총 댓글 수
		try {

		}
		catch(Exception e) {
			return -1;
		}
		return affected;
	}

	@Override
	public List<CommentLineDto> selectAll(Map map) {
		List records=mapper.findAll(map);
		return records;
	}
	
	@Override
	public List<CommentLineDto> findrecent_comment(Map map) {
		List records=mapper.findrecent_comment(map);
		return records;
	}
	
	@Override
	public List userprofiles(Map map) {
		List records = mapper.userprofiles(map);
		return records;
	}

}
