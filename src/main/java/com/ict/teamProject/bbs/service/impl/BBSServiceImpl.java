package com.ict.teamProject.bbs.service.impl;

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
import com.ict.teamProject.files.service.FilesDto;



@Service("service")
public class BBSServiceImpl implements BBSService<BBSDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private BBSMapper mapper;
	
	
	//게시물 등록
	@Override
	public Map insert(Map map) {
		try {
	        // 시퀀스 값을 먼저 가져오는 쿼리 실행
	        int generatedKey = mapper.getSeqNextVal();
	        map.put("getBno", generatedKey);
			mapper.save(map);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
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
	
	//게시물 전체 조회
	@Override
	public List<BBSDto> selectAll() {
		List records=mapper.findAll();
		return records;
	}
	
	//게시물에 해당하는 파일 가져오기
	@Override
	public List<BBSDto> selectFiles(int bno) {
		List records=mapper.findFile(bno);
		System.out.println(records);
		return records;
	}

	//게시물 상세보기
	@Override
	public BBSDto selectOne(Map map) {		
		return mapper.findByBBS(map);
	}

	//게시물 수정
	@Override
	public int update(BBSDto record, FilesDto files) {
		int affected=0;
		try {
			mapper.updateBBS(record);
			affected=mapper.updateFiles(files);
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
	public int delete(BBSDto record, FilesDto files) {			
		int affected=0;//특정 글번호에 따른 삭제된 총 댓글 수
		try {
			//TransactionCallback<T>: 타입 파라미터 T는 트랜잭션 처리 작업후 반환할 타입으로 지정
			affected=template.execute(new TransactionCallback<Integer>() {
				//※doInTransaction()의 반환값이 execute()메소드의 반환값이다 
				@Override
				public Integer doInTransaction(TransactionStatus status) {
					int deleteCommentsCount=mapper.deleteFiles(files);
					//해당 원본 글 삭제
					mapper.deleteBBS(record);
					return deleteCommentsCount;
				}
			});
		}
		catch(Exception e) {
			return -1;
		}
		return affected;
	}



}
